package ml.pfit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.http.HttpMethod.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PfitApplicationTests {

	@LocalServerPort
	private int port;

	/** Connection exchange with API*/
	TestRestTemplate restTemplate = new TestRestTemplate();

	/** Trace IP path */
	String traceUrl 	= "/traceip/";

	/** Stats path */
	String statsUrl 	= "/stats";

	/** An IP from Brazil */
	String IP_BRA = "177.47.27.205";

	/** An IP from Argentina */
	String IP_ARG = "190.247.191.203";


	@Test
	void correctCallToStatsShouldReturnStatus200() throws Exception {
		ResponseEntity<String> response = callStats();
		Assertions.assertEquals(200, response.getStatusCode().value());
	}

	@Test
	void incorrectCallToTraceShouldReturnStatusDifferentThan200() throws Exception {
		ResponseEntity<String> response = callTraceForIP("999");
		Assertions.assertNotEquals(200, response.getStatusCode().value());
	}

	@Test
	void severalCallsToTraceShouldReturnCorrectMinMaxAvg() throws Exception {
		Random r = new Random();
		// Argentina invocation count
		int argCalls = r.nextInt(9) + 1;
		// Brazil invocation count
		int braCalls = r.nextInt(9) + 1;
		// Pre-calculated Argentina (center) distance from BA
		int argDistance = 520;
		// Pre-calculated Brazil distance from BA
		int braDistance = 2757;
		// Pre-calculated average distance
		int avgDistance = Math.toIntExact((argDistance * argCalls + braDistance * braCalls) / (argCalls + braCalls));

		// API calls for Argentina IP
		for (int i=0; i<argCalls; i++) {
			callTraceForIP(IP_ARG);
		}
		// API calls for Brazil IP
		for (int i=0; i<braCalls; i++) {
			callTraceForIP(IP_BRA);
		}

		// Are min, max and avg as expected?
		ResponseEntity<String> response = callStats();
		JSONParser jsonParser = new JSONParser();
		JSONObject json = (JSONObject)jsonParser.parse(response.getBody());

		// Min test
		assertThat((Long)json.get("min")).isEqualTo(argDistance);
		// Max test
		assertThat((Long)json.get("max")).isEqualTo(braDistance);
		// Avg test
		assertThat((Long)json.get("avg")).isEqualTo(avgDistance);
	}


	/** Calls the API tracing a specific IP
	 * @param ip the IP to trace */
	ResponseEntity<String> callTraceForIP(String ip) {
		return restTemplate.exchange(String.format("%s%s%s", getBaseURL(), traceUrl, ip), GET, null, String.class);
	}

	/** Calls the API for stats info */
	ResponseEntity<String> callStats() {
		return restTemplate.exchange(String.format("%s%s", getBaseURL(), statsUrl), GET, null, String.class);
	}

	/** @return base URL for API tests */
	String getBaseURL() {
		return "http://localhost:" + port + "/api";
	}

}
