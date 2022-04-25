package ml.pfit.resolve;

import ml.pfit.model.IPAddr;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IPResolverTest {

    @Mock
    private RemoteAPI remoteAPI; // Mockito.mock(RemoteAPI.class);

    private IPResolver ipResolver;

    @BeforeEach
    void init() {
        ipResolver = new IPResolver(remoteAPI);
    }

    @Test
    void ipResolveShouldReturnCountryInfo() throws Exception {
        String ip ="190.247.191.212";
        JSONObject obj = new JSONObject();
        obj.put("country_code", "ARG");
        obj.put("country_name", "Argentina");
        when(remoteAPI.call(any(String.class))).thenReturn(obj);
        IPAddr val = ipResolver.resolve(ip);
        assertThat(val).isNotNull();
        assertThat(val.getCode()).isEqualTo("ARG");
        assertThat(val.getName()).isEqualTo("Argentina");
    }

}
