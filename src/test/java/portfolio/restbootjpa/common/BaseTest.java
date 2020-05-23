package portfolio.restbootjpa.common;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import portfolio.restbootjpa.accounts.Account;
import portfolio.restbootjpa.accounts.AccountRole;
import portfolio.restbootjpa.accounts.AccountService;

@SpringBootTest
@AutoConfigureMockMvc  
@AutoConfigureRestDocs
@Transactional
@Import(RestDocsConfiguration.class)
public class BaseTest {
	@Autowired
	AccountService accountService;
	@Autowired
	AppProperties appProperties;
	
	
	@Autowired
	protected MockMvc mockMvc;  
	
	
//  시작 히 아이디 하나 생성해도록 설정해둠 그래서  현재 필요 없음
	protected Account createAccount() {
		 
		 //Given
		 Account account = Account.builder()
		 		.email(appProperties.getUserUsername())
		 		.password(appProperties.getUserPassword())	
		 		.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
		 		.build();
		 
		return  this.accountService.saveAccount(account) ;	 
				
	}
	
	protected String getBearerToken(boolean needToCreateAccount) throws Exception {
		return "Bearer " + getAccessToken(needToCreateAccount) ;
	}
	
	

	protected String getAccessToken(boolean needToCreateAccount) throws Exception {
	    	
		if(needToCreateAccount) {
	//	createAccount();
		}		 
		 
		ResultActions perform = this.mockMvc.perform(post("/oauth/token") //인증서버가 등록이 되면 auth/token을 처리할 수 있는 핸들러가 적용이 된다.
				     .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret())) //클라이언트 아이디와 시크릿을 가지고 basic -auth라는 헤더라는 만들었다.
				     .param("username", appProperties.getUserUsername())
				     .param("password", appProperties.getUserPassword())
				     .param("grant_type", "password")
				     
				 );
		 String responseBody = perform.andReturn().getResponse().getContentAsString();
		Jackson2JsonParser  parser = new Jackson2JsonParser();
			
			
		return parser.parseMap(responseBody).get("access_token").toString(); //이렇게 하면 access_token을 꺼낼 수 있다.
	}


		
	
}
