package app.com.ChinChen.library;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;

import static java.util.stream.Collectors.toList;


public class PageUtil {
    public static <T> Page<T> getPage(List<T> list, Pageable pageable) {
        if (CollectionUtils.isEmpty(list)) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) list.get(0).getClass();

        Stream<T> stream = list.stream();
        if (!IterableUtils.isEmpty(pageable.getSort())) {
            stream = stream.sorted(getComparator(pageable.getSort(), clazz));
        }
        List<T> slice = stream.skip((long) pageable.getPageNumber() * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .collect(toList());
        return new PageImpl<>(slice, pageable, list.size());
    }

    @SuppressWarnings("unchecked")
    public static <T> Comparator<T> getComparator(Sort sort, Class<T> clazz) {
        return StreamSupport.stream(sort.spliterator(), false)
                .map(order -> getComparator(order, clazz))
                .reduce(Comparator::thenComparing)
                .orElse((Comparator<T>) Comparator.naturalOrder());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Comparator<T> getComparator(Order order, Class<T> clazz) {
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(clazz, order.getProperty());
        if (propertyDescriptor == null || propertyDescriptor.getReadMethod() == null) {
            throw new IllegalArgumentException(String.format("can't find read method for %s in %s", order.getProperty(), clazz.getName()));
        }
        Method readMethod = propertyDescriptor.getReadMethod();

        Comparator<T> comparator = nullsLast(comparing(f -> {
            try {
                return (Comparable) readMethod.invoke(f);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalArgumentException(e);
            }
        }, nullsLast(Comparator.naturalOrder())));
        return Direction.ASC.equals(order.getDirection()) ? comparator : comparator.reversed();
    }
}
