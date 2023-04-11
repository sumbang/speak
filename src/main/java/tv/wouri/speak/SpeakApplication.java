package tv.wouri.speak;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import tv.wouri.speak.service.FilesStorageService;

import javax.annotation.Resource;

@SpringBootApplication
@EnableConfigurationProperties
@EntityScan(basePackages = {"tv.wouri.speak.models"})
@EnableJpaRepositories(basePackages = {"tv.wouri.speak.repositories"})
@ComponentScan({"tv.wouri.speak.controllers","tv.wouri.speak.service","tv.wouri.speak.security","tv.wouri.speak.apiV1"})
public class SpeakApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(SpeakApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpeakApplication.class);
	}

	@Override
	public void run(String... arg) throws Exception {
        //storageService.deleteAll();
		storageService.init();
	}
}
