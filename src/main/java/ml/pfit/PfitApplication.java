package ml.pfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class PfitApplication {

	public static void main(String[] args) {
		SpringApplication.run(PfitApplication.class, args);
	}

}
