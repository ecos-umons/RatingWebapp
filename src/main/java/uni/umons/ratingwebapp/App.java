package uni.umons.ratingwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Calendar;

/**
 * Spring Boot Servlet Initializer
 */
@SpringBootApplication
public class App extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(App.class);
	}

    public static void main( String[] args ) {
		SpringApplication.run(App.class, args);
    }
}
