package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;

@SpringBootApplication
@ComponentScan({ "controllers", "sim.rise.ext.services" })
@OpenAPIDefinition(
	info = @Info(
		title = "arslab-services OpenAPI Definition", 
		version = "1.0.0", 
		description = "OpenAPI definition of the arslab-services REST API.", 
		contact = @Contact(name = "Paul Roode", email = "paul.roode@carleton.ca")
	),
	servers = {
		@Server(url = "{protocol}://arslab-services.herokuapp.com", description = "Staging server",
			variables = { @ServerVariable(name = "protocol", allowableValues = { "http", "https" }, defaultValue = "https") }),
		@Server(url = "http://localhost:{port}", description = "Development server",
			variables = { @ServerVariable(name = "port", allowableValues = { "8080" }, defaultValue = "8080") })
	}
)
public class Application extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		
	}

}