package portfolio.restbootjpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration  //이 타입을 만들어지는 순간 더 이상 타입이 등록되지 않는다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
http.authorizeRequests()
.antMatchers("/") //이런요청들은 인증을
.permitAll()               //허용하고
.anyRequest()             //나머지 요청들은
.authenticated()            //인증이 필요하다
.and()
.formLogin()               //form로그인과
.and()
.httpBasic() ;            //httpbasic인증이 필요하다

		
	}
	
	
	//패스워드 인콯더 설정 이걸 쓰면..?
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder() ;
		
	}
	
	
	
}
