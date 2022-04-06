package ml.pfit.service;

import ml.pfit.model.Country;
import ml.pfit.model.RequestStats;
import ml.pfit.repository.RequestStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * A simple volatile persistent statistics store implementation
 */
@Component
public class MongoStatsImpl implements StatsInterface {

    @Autowired
    private RequestStatsRepository repository;

    /** Reference to the persistent requests statistics */
    protected RequestStats rs = null;

    @Override
    public void storeRequest(Country country) {
        getRS().setTotalRequests(getRS().getTotalRequests()+1);
        getRS().setTotalDistance(getRS().getTotalDistance()+country.getDistance());
        getRS().setMaxDistance(Math.max(getRS().getMaxDistance(), country.getDistance()));
        getRS().setMinDistance(Math.min(getRS().getMinDistance(), country.getDistance()));
        repository.save(getRS());
    }

    @Override
    public Integer maxDistance() {
        return getRS().getMaxDistance();
    }

    @Override
    public Integer minDistance() {
        return getRS().getMinDistance();
    }

    @Override
    public Integer avgDistance() {
        return getRS().getTotalRequests() > 0 ? Math.toIntExact(getRS().getTotalDistance() / getRS().getTotalRequests()) : 0;
    }

    /** @return the reference to the persistent request statistics */
    RequestStats getRS() {
        if (rs==null) {
            rs = (repository.count() > 0 ? repository.findAll().get(0) : new RequestStats("1"));
        }
        return rs;
    }


}
