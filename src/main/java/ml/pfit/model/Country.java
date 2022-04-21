package ml.pfit.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class Country {

    private final String code;

    private final ArrayList<String> languages;

    private final ArrayList<String> timeZones;

    private final Integer distance;

    private final String currency;

}
