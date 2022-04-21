package ml.pfit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class IPAddr {

    private final String ip;

    private final String code;

    private final String name;
}
