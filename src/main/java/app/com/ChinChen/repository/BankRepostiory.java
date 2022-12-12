package app.com.ChinChen.repository;

import app.com.ChinChen.domain.Bank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BankRepostiory extends MongoRepository<Bank, String> {
}
