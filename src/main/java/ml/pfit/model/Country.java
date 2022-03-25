package ml.pfit.model;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Getter
@Setter
public class Country {


    /** Country CODE (ARG, ITA, BRA, etc.) */
    private String code;

    /** Country Name (Argentina, Italia, Brazil, etc.) */
    private String name;

    /** Official languages (Spanish, Italiano, Portuguese, etc.) */
    private ArrayList<String> languages = new ArrayList<>();

    /** Timezones ("UTC-03:00", "UTC+01:00", ["UTC-05:00", "UTC-04:00", "UTC-03:00", "UTC-02:00" ]  */
    private ArrayList<String> timeZones = new ArrayList<>();

    /** Distance from Buenos Aires */
    private Integer distance;

    /** Local currency (ARS, EUR, BRL) */
    private String currency;

    /** Local currency based on USD */
    private Double currencyRateUSD;

    /** @return a conversion of this object to a JSONObject entity */
    public JSONObject toJSON() {
        JSONObject res = new JSONObject();

        // Non-variable information
        res.put("code", code);
        res.put("languages", getJSONLanguages());
        res.put("timeZones", getJSONTimeZones());
        res.put("distance", distance);
        res.put("currency", currency);

        // Variable information
        res.put("localTimes", getJSONLocalTimes());
        res.put("usdRate", currencyRateUSD);
        return res;
    }

    /** @return a JSON representation of this country's language */
    protected JSONArray getJSONLanguages() {
        JSONArray ret = new JSONArray();
        ret.addAll(languages);
        return ret;
    }

    /** @return a JSON representation of this country's time zones */
    protected JSONArray getJSONTimeZones() {
        JSONArray ret = new JSONArray();
        ret.addAll(timeZones);
        return ret;
    }

    /** @return a JSON representation of current local times based on the timeZone */
    protected JSONArray getJSONLocalTimes() {
        JSONArray ret = new JSONArray();
        timeZones.forEach(tz -> ret.add( ZonedDateTime.now().withZoneSameInstant(ZoneId.of(tz)).toString() ));
        return ret;
    }

    /** @return current local times based on the timeZone */
    public ArrayList<String> getLocalTimes() {
        ArrayList<String> ret = new ArrayList<>();
        timeZones.forEach(tz -> ret.add( ZonedDateTime.now().withZoneSameInstant(ZoneId.of(tz)).toString() ));
        return ret;
    }

    /** Load variables based on other instance
     * @param other country which variables should be taken from */
    public void load(Country other) {
        this.code = other.code;
        this.name = other.name;
        this.languages = other.languages;
        this.timeZones = other.timeZones;
        this.distance = other.distance;
        this.currency = other.currency;
        this.currencyRateUSD = other.currencyRateUSD;
    }

}
