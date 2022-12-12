package app.com.ChinChen.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import app.com.ChinChen.domain.DailyReport;

@RepositoryRestResource
public interface DailyReportRepostiory extends MongoRepository<DailyReport, String> {

    @Query(value = "{'projectId':?0}", fields = "{'dailyDate': 1, 'morningWeather': 1, 'afternoonWeather': 1, 'expectComplete': 1, 'actualComplete': 1, 'todayItem': 1, 'bossSuggest': 1, 'waitToSlove': 1}")
    List<DailyReport> findAllByProjectId(String projectId);

//    @Aggregation(pipeline = {
//            "{$lookup:{from: \"project\",localField: \"projectId\",foreignField: \"_id\",as: \"project\"}}",
//            "{$unwind:$project}",
//            "{$match:{_id: ?0}}",
//    })
//    DailyReport findAllById(String _id);

    @Query(value = "{'_id':?0}", fields = "{}")
    DailyReport findAllById(String _id);
}
