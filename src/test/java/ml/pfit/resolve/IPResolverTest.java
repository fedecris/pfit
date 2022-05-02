package ml.pfit.resolve;

import ml.pfit.model.IPAddr;
import ml.pfit.utils.RemoteAPI;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IPResolverTest {

    @Mock
    private RemoteAPI remoteAPI; // Mockito.mock(RemoteAPI.class);

    @InjectMocks
    private IPResolver ipResolver;

    @Test
    void ipResolveShouldReturnCountryInfo() throws Exception {
        // given
        String ip ="190.247.191.212";
        JSONObject obj = new JSONObject();
        obj.put("country_code", "ARG");
        obj.put("country_name", "Argentina");

        // when
        when(remoteAPI.call(any(String.class))).thenReturn(obj);
        IPAddr val = ipResolver.resolve(ip);

        // then
        assertThat(val).isNotNull();
        assertThat(val.getCode()).isEqualTo("ARG");
        assertThat(val.getName()).isEqualTo("Argentina");
    }

}
