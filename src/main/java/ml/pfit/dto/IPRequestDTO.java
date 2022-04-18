package ml.pfit.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class IPRequestDTO {

    private String ip;

    private String code;

    private String name;
}
