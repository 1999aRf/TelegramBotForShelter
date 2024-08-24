package shelter.bot.botshelter;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition()
public class BotForTheShelterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BotForTheShelterApplication.class, args);
	}

}
