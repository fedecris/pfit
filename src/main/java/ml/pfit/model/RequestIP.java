package ml.pfit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestIP {

    /** The IP of a request */
    private String ip;

    public RequestIP(String ip) {
        this.ip = ip;
    }
}
