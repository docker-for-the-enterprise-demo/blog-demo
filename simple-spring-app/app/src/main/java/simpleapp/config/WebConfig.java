package simpleapp.config;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class WebConfig extends WebMvcConfigurerAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${staticResourcesDir}")
	protected String staticDir;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		logger.info("Adding resource handler /ds/** pointing to " + staticDir);
		registry.addResourceHandler("/ds/**").addResourceLocations("file:" + staticDir + File.separator);

		super.addResourceHandlers(registry);
	}

	// @Bean
	// public static PropertySourcesPlaceholderConfigurer
	// propertySourcesPlaceholderConfigurer() {
	// return new PropertySourcesPlaceholderConfigurer();
	// }
}
