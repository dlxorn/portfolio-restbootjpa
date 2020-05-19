package portfolio.restbootjpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import portfolio.restbootjpa.account.AccountService;
import portfolio.restbootjpa.common.AppProperties;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter{
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager  authenticationManager ;
	
	@Autowired
	AccountService accountService;
	
	
	@Autowired
	TokenStore tokenstore;
	
	
	@Autowired
	AppProperties appProperties;
	
	
		

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.passwordEncoder(passwordEncoder) ; 
	}

	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		
		clients.inMemory()	 //TODO 인메모리용에서 바꿀 것
		   .withClient(appProperties.getClientId())
		   .authorizedGrantTypes("password","refresh_token") 
		   .scopes("read", "write") 
		   .secret(this.passwordEncoder.encode(appProperties.getClientSecret()))
		   .accessTokenValiditySeconds(10 * 60) 
		   .refreshTokenValiditySeconds(6*10*60) 		  ;
	
	}


	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
		.userDetailsService(accountService)
		.tokenStore(tokenstore) ;	
	}


}
