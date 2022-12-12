package app.com.ChinChen.repository;

import app.com.ChinChen.domain.AutoNumB;
import app.com.ChinChen.domain_temp.CodeSection;
import app.com.ChinChen.domain_temp.ItemCodeList;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface AutoNumBRepository extends MongoRepository<AutoNumB, String> {
    @Aggregation(pipeline = {
            "{$match:{$expr: {$and: [" +
                    "{$eq: [\"$ChapCode\",\"?0\"]}," +
                    "{$eq: [\"$CodeSection\",\"?1\"]}," +
                    "{$ne: [\"$Code\",\"\"]}" +
                    "]}}}",
            "{$project:{Code:1,Content:1,SelfRow:1}}"
    })
    List<CodeSection> getCodeSection(String ChapCode, String CodeSection);

    @Aggregation(pipeline = {
            "{$match:{$expr: {$and: [" +
                    "{$eq: [\"$ChapCode\",\"?0\"]}," +
                    "{$eq: [\"$CodeSection\",\"?1\"]}," +
                    "{$ne: [\"$Code\",\"\"]}," +
                    "{lte: [\"$MinRow\",?2] }," +
                    "{$gte:[\"$MaxRow\",?2]}" +
                    "]}}}",
            "{$project:{Code:1,Content:1,SelfRow:1}}"
    })
    List<CodeSection> getCodeSection(String ChapCode, String CodeSection, int SelfRow);
}
