package ml.pfit.repository;

import ml.pfit.model.RequestStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestStatsRepository extends MongoRepository<RequestStats, String> {

}
