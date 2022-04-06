package ml.pfit.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "requests")
@Getter
@Setter
public class RequestStats {

    @Id
    private String id;

    @NotNull
    private Long totalDistance;

    @NotNull
    private Long totalRequests;

    @NotNull
    private Integer minDistance;

    @NotNull
    private Integer maxDistance;

    /** Default constructor, with id as parameter
     * @param id the identifier of this entity */
    public RequestStats(String id) {
        this.id = id;
        totalDistance = 0L;
        totalRequests = 0L;
        minDistance = Integer.MAX_VALUE;
        maxDistance = Integer.MIN_VALUE;
    }

}
