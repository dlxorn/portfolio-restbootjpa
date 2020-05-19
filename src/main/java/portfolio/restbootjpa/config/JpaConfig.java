package portfolio.restbootjpa.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


@Configuration
@EnableJpaAuditing  
public class JpaConfig {

	@Bean
	public AuditorAware<String> auditorProvider() {		
		
		return 		new AuditorAware<String>() {

					@Override
					public Optional<String> getCurrentAuditor() {
						
						SecurityContext context = SecurityContextHolder.getContext();
						Authentication authentication = context.getAuthentication();					
						
						String username = "무인증 로그인";
						
						if(authentication != null) {
							 username = authentication.getName();
							
						}
						return  Optional.of(username);
					}			
			
		    };
				
	}
	
}
