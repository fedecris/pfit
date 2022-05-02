package ml.pfit.resolve;

import ml.pfit.model.Country;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryResolverTest {

    @Mock
    private RemoteAPI remoteAPI; // Mockito.mock(RemoteAPI.class);

    @InjectMocks
    private CountryResolver countryResolver;

    @Test
    void resolve() throws Exception {
        // given
        JSONObject obj = new JSONObject();
        obj.put("country_code", "ARG");
        obj.put("country_name", "Argentina");

        JSONObject lang = new JSONObject();
        lang.put("name", "spanish");
        JSONArray langs = new JSONArray();
        langs.add(lang);
        obj.put("languages", langs);

        JSONArray timezones = new JSONArray();
        timezones.add("+3");
        obj.put("timezones", timezones);

        JSONArray latlng = new JSONArray();
        latlng.add(30d);
        latlng.add(40d);
        obj.put("latlng", latlng);

        JSONObject currency = new JSONObject();
        currency.put("code", "ARS");
        JSONArray currencies = new JSONArray();
        currencies.add(lang);
        obj.put("currencies", currencies);

        // when
        when(remoteAPI.call(any(String.class))).thenReturn(obj);
        Country res = countryResolver.resolve("123.123.123.123");

        // then
        verify(remoteAPI).call("123.123.123.123");
        assertThat(res.getLanguages().get(0)).isEqualTo("spanish");
    }
}