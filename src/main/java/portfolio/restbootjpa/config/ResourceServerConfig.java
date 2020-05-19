package portfolio.restbootjpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId("event");
		
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous()
				.and()
				.authorizeRequests()
				.mvcMatchers(HttpMethod.GET,"/api/**")	
				.permitAll() //이건 모든 상대들에게 허용
				.anyRequest()
				.authenticated()
				.and()
			.exceptionHandling()  //에러 핸들러인데 에러핸들런 중에 에러가 나며면
			  .accessDeniedHandler(new OAuth2AccessDeniedHandler()); //OAuth2AccessDeniedHandler를 사용하여 핸들링을 해준다.
			  
		
	}

	

	
}
