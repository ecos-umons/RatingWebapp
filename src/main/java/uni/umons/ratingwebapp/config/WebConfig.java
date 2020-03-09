
package uni.umons.ratingwebapp.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.*;
import uni.umons.ratingwebapp.security.CustomErrorPageRegisterar;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "uni.umons.ratingwebapp.*")
@PropertySource({"classpath:application.properties", "classpath:database.properties"})
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/css/**").addResourceLocations("/css/");
		registry.addResourceHandler("/js/**").addResourceLocations("/js/");
		registry.addResourceHandler("/images/**").addResourceLocations("/images/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/signin").setViewName("signin");
		registry.addViewController("/signin").setViewName("signin");
		registry.addViewController("/error/404").setViewName("404");
		registry.addViewController("/error/505").setViewName("505");
		registry.addViewController("/error/401").setViewName("401");
	}

	@Bean
	public ErrorPageRegistrar errorPageRegistrar(){
		return new CustomErrorPageRegisterar();
	}
}

