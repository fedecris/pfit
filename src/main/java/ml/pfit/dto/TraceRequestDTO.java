package ml.pfit.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Component
@Getter
@Setter
public class TraceRequestDTO {

    private String ip;

    private String code;

    private String name;

    private ArrayList<String> languages;

    private ArrayList<String> timeZones;

    private Integer distance;

    private String currency;

    private Double currencyRateUSD;

    private ArrayList<String> localTimes;

    private Long responseTimeMS;

    public TraceRequestDTO(String ip) {
        this.ip = ip;
    }

    public TraceRequestDTO() {
    }

    /** calculates local times based on the timeZone */
    public void calculateLocalTimes() {
        localTimes = new ArrayList<>();
        timeZones.forEach(tz -> localTimes.add( ZonedDateTime.now().withZoneSameInstant(ZoneId.of(tz)).toString() ));
    }
}
