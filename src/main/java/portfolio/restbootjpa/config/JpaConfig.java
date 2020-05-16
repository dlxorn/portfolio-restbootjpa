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
		
		//security해서 이런식으로 가져오라고 했는데
		//실제로 통하는지는 확인이 필요하다
		//일단은 구현해둠
		//자동 유저네임 업데이트용 (createby, 등등)		
		
		return 		new AuditorAware<String>() {
			
					

					@Override
					public Optional<String> getCurrentAuditor() {

						
						SecurityContext context = SecurityContextHolder.getContext();
						Authentication authentication = context.getAuthentication();
					
						
						String username = "테스트 대상";
						
						if(authentication != null) {
							 username = authentication.getName();
							
						}
						
						
			
						return  Optional.of(username);
					}
			
			
			
		    };
				
	}

	
}
