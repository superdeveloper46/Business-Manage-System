package app.com.ChinChen.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import app.com.ChinChen.library.AES;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/update" })
public class UpdateController {
    @Autowired
    MongoTemplate mongoTemplate;

    // update data api
    /*
     * {
     * "collectionName": "project",
     * "where":[
     * {
     * "key": "_id",
     * "value": "1",
     * "subdocName": ""
     * },
     * //if you don't have subdocument, you can skip this this
     * // {
     * // "key": "_id",
     * // "value": "1",
     * // "subdocName": "employeeDetail"
     * // }
     * ],
     * "updates": {
     * "key": "_id",
     * "value": "1"
     * "data_type": "String" // String, int, double, Date, Boolean
     * }
     * 
     * }
     */
    @PostMapping("/data")
    public ResponseEntity<?> updateSort(@RequestBody Map<String, Object> data) {
        Query query;
        final Update update = new Update();
        Criteria andCriteria = new Criteria();
        query = new Query();

        String collectionName = data.get("collectionName").toString();
        List<Map<String, String>> where = (List<Map<String, String>>) data.get("where");
        Map<String, String> updates = (Map<String, String>) data.get("updates");

        List<Criteria> andExpression = new ArrayList<>();

        String setKey = "";
        for (int i = 0; i < where.size(); i++) {
            Map<String, String> t = where.get(i);
            if (t.get("subdocName").equals("")) {
                Criteria c = Criteria.where(t.get("key")).is(t.get("value"));
                andExpression.add(c);
                setKey = setKey + updates.get("key");
            } else {
                Criteria c = Criteria.where(t.get("subdocName"))
                        .elemMatch(Criteria.where(t.get("key")).is(t.get("value")));
                andExpression.add(c);
                setKey = t.get("subdocName") + ".$." + setKey;
            }
        }

        query.addCriteria(andCriteria.andOperator(andExpression.toArray(new Criteria[andExpression.size()])));
        String data_type = updates.get("data_type");

        if (data_type.equals("String")) {
            update.set(setKey, updates.get("value"));
        } else if (data_type.equals("int")) {
            int value = Integer.parseInt(updates.get("value"));
            update.set(setKey, value);
        } else if (data_type.equals("double")) {
            double value = Double.parseDouble(updates.get("value"));
            update.set(setKey, value);
        } else if (data_type.equals("Date")) {
            Date value;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT") );
                value = sdf.parse(updates.get("value"));
                //value = new SimpleDateFormat("yyyy-MM-dd").parse(updates.get("value"));
                update.set(setKey, value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (data_type.equals("Boolean")) {
            Boolean value = Boolean.parseBoolean(updates.get("value"));
            update.set(setKey, value);
        }

        mongoTemplate.updateFirst(query, update, collectionName);
        HashMap<String, String> res = new HashMap<String, String>();

        res.put("status", "ok");
        return ResponseEntity
                .ok()
                .body(res);

    }

    /*
     * {
     * "collectionName": "employee", //Collection
     * "id": "20221125114606241", //Collection id
     * "subdocName": "",
     * "subdocNameId": ""
     * }
     * or
     * {
     * "collectionName": "employee", //Collection
     * "id": "20221125114606241", //Collection id
     * "subdocName": "contactList", //刪除Detail 才填
     * "subdocNameId": "20221125114606241" //新增Detail id
     * }
     */
    @PostMapping("/delData")
    public ResponseEntity<?> delData(@RequestBody Map<String, String> data) {
        String collectionName = data.get("collectionName").toString();
        String id = data.get("id");
        String subdocName = data.get("subdocName");
        String subdocNameId = data.get("subdocNameId");

        HashMap<String, String> res = new HashMap<String, String>();

        Criteria criteria = Criteria.where("_id").is(id);
        // 刪除主檔
        Query query = Query.query(criteria);
        if (subdocName == null || subdocName.isEmpty()) {
            mongoTemplate.remove(query, collectionName);
            res.put("status", "ok");
            return ResponseEntity.ok().body(res);
        }
        // 刪除Detail
        Criteria DetailCriteria = Criteria.where("_id").is(subdocNameId);
        Query DetailQuery = Query.query(DetailCriteria);
        Update update = new Update().pull(subdocName, DetailQuery);
        mongoTemplate.updateMulti(query, update, collectionName);

        res.put("status", "ok");
        return ResponseEntity.ok().body(res);
    }

    @PostMapping("/encrypt") // 資料加密
    public ResponseEntity<?> Encrypt(@RequestBody Map<String, String> data) {
        String dataValue = data.get("data");
        HashMap<String, String> res = new HashMap<String, String>();
        res.put("data", new AES().encode(dataValue));
        return ResponseEntity.ok().body(res);
    }

    /*
     * {
     * "collectionName": "supplier", //新增一筆資料
     * "subdocName": "",
     * "id": ""
     * }
     * or
     * {
     * "collectionName": "supplier", //Collection
     * "subdocName": "licenseList", //新增Detail 才填
     * "id": "20221012040532590" //Collection id
     * }
     */
    @PostMapping("/addData")
    public ResponseEntity<?> addData(@RequestBody Map<String, String> data) {
        HashMap<String, String> res = new HashMap<String, String>();
        try {
            String collectionName = data.get("collectionName");
            String id = data.get("id");
            String subdocName = data.get("subdocName");
            // 建立Collection 物件
            String bean = collectionName.toUpperCase().charAt(0) + collectionName.substring(1);// 第一字母轉大寫
            Class newClass = Class.forName("app.com.ChinChen.domain." + bean);
            Object newObj = newClass.newInstance();
            if (subdocName == null || subdocName.length() == 0) {
                // 新增一筆資料
                mongoTemplate.save(newObj, collectionName);
                res.put("_id", getId(newObj));
                return ResponseEntity.ok().body(res);
            }
            // 新增範例
            // Employee Employee = (Employee) mongoTemplate.findById(id, obj.getClass());
            // List<EmergencyContact> contactList = Employee.getContactList();
            // EmergencyContact emergencyContact = new EmergencyContact();
            // contactList.add(emergencyContact);

            Object qryObj = mongoTemplate.findById(id, newObj.getClass());// 查詢
            List<Object> values = (List) getValue(qryObj, new String[] { subdocName });// 取得Detail Value
            String subdocNameFunction = subdocName.toUpperCase().charAt(0) + subdocName.substring(1);// 第一字母轉大寫
            Method method = qryObj.getClass().getMethod("get" + subdocNameFunction);// 取得Method
            String addOjbName = getOjbName(method);// 取得Detail 物件名稱
            // 建立新增物件
            Class addClazz = Class.forName("app.com.ChinChen.domain." + addOjbName);
            Object addObj = addClazz.newInstance();
            values.add(addObj);// 將物件新增進List
            mongoTemplate.save(qryObj, collectionName);// 新增
            res.put("_id", getId(addObj));
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            res.put("status", "error");
            return ResponseEntity.ok().body(res);
        }
        // Class c = mObj.getClass();
        // Field field = getField(c, subdocName);
        // Object metj = field.get(mObj);
        // Type type = field.getType();
    }

    private static String getId(Object addObj) {
        String addString = addObj.toString();
        int index = addString.indexOf("_id=");
        String _id = addString.substring(index + 4, index + 4 + 17);
        return _id;
    }

    private static String getOjbName(Method method) throws NoSuchFieldException, IllegalAccessException {
        Field f = Method.class.getDeclaredField("signature");
        f.setAccessible(true);
        String sigature = (String) f.get(method);
        // ()Ljava/util/List<Lapp/com/ChinChen/domain/EmergencyContact;>;
        int listIndex = sigature.lastIndexOf("/");
        String addOjb = sigature.substring(listIndex + 1, sigature.length() - 3);
        return addOjb;
    }

    public static Object getValue(Object obj, String[] fieldNames) {
        // String[] fieldNames = fieldPath.split("[\\.\\[\\]]");
        String success = "";
        Object res = obj;
        for (String fieldName : fieldNames) {
            if (fieldName.isEmpty())
                continue;
            int index = toIndex(fieldName);
            if (index >= 0) {
                try {
                    res = ((Object[]) res)[index];
                } catch (ClassCastException cce) {
                    throw new RuntimeException(
                            "cannot cast " + res.getClass() + " object " + res + " to array, path:" + success, cce);
                } catch (IndexOutOfBoundsException iobe) {
                    throw new RuntimeException("bad index " + index + ", array size " + ((Object[]) res).length
                            + " object " + res + ", path:" + success, iobe);
                }
            } else {
                Field field = getField(res.getClass(), fieldName);
                field.setAccessible(true);
                try {
                    res = field.get(res);
                } catch (Exception ee) {
                    throw new RuntimeException("cannot get value of [" + fieldName + "] from " + res.getClass()
                            + " object " + res + ", path:" + success, ee);
                }
            }
            success += fieldName + ".";
        }
        return res;
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        Class<?> tmpClass = clazz;
        do {
            try {
                Field f = tmpClass.getDeclaredField(fieldName);
                return f;
            } catch (NoSuchFieldException e) {
                tmpClass = tmpClass.getSuperclass();
            }
        } while (tmpClass != null);

        throw new RuntimeException("Field '" + fieldName + "' not found in class " + clazz);
    }

    private static int toIndex(String s) {
        int res = -1;
        if (s != null && s.length() > 0 && Character.isDigit(s.charAt(0))) {
            try {
                res = Integer.parseInt(s);
                if (res < 0) {
                    res = -1;
                }
            } catch (Throwable t) {
                res = -1;
            }
        }
        return res;
    }
    // public static<T> T get(Object obj, String fieldPath) {
    // return (T) getValue(obj, fieldPath);
    // }

    // public static String getSignature(Method m) {
    // String sig;
    // try {
    // Field gSig = Method.class.getDeclaredField("signature");
    // gSig.setAccessible(true);
    // sig = (String) gSig.get(m);
    // if (sig != null) return sig;
    // } catch (IllegalAccessException | NoSuchFieldException e) {
    // e.printStackTrace();
    // }
    //
    // StringBuilder sb = new StringBuilder("(");
    // for (Class<?> c : m.getParameterTypes())
    // sb.append((sig = Array.newInstance(c, 0).toString())
    // .substring(1, sig.indexOf('@')));
    // return sb.append(')')
    // .append(
    // m.getReturnType() == void.class ? "V" :
    // (sig = Array.newInstance(m.getReturnType(), 0).toString()).substring(1,
    // sig.indexOf('@'))
    // )
    // .toString();
    // }
}
