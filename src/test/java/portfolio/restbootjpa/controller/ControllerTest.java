package portfolio.restbootjpa.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import portfolio.restbootjpa.Entity.Email;
import portfolio.restbootjpa.Entity.MerBs;
import portfolio.restbootjpa.Entity.Phone;
import portfolio.restbootjpa.accounts.Account;
import portfolio.restbootjpa.accounts.AccountRole;
import portfolio.restbootjpa.accounts.AccountService;
import portfolio.restbootjpa.common.AppProperties;
import portfolio.restbootjpa.dto.MerBsDto;
import portfolio.restbootjpa.repository.ContactRepository;
import portfolio.restbootjpa.repository.MerBsRepository;
import portfolio.restbootjpa.repository.MerClientBsRepository;
import portfolio.restbootjpa.repository.MerClientRelRepository;
import portfolio.restbootjpa.repository.PhoneRepository;
import portfolio.restbootjpa.repository.ReRegBaseRepository;
import portfolio.restbootjpa.repository.ReRegDtRepository;

@SpringBootTest
@AutoConfigureMockMvc  
@Transactional
public class ControllerTest {
		
	@Autowired
	protected MockMvc mockMvc;  
	
    ObjectMapper objectMapper = new ObjectMapper();	
	
	@PersistenceContext  
	private EntityManager em;
	
	@Autowired 
	MerBsRepository merBsRepository;	
	@Autowired
	PhoneRepository phoneRepository; //TODO 테스트용으로 만들었으나, contactRepository 쓴 후,  캐스팅해서 사용하면 됨. 삭제할 것
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	MerClientBsRepository merClientBsRepository;
	@Autowired
	ReRegBaseRepository reRegBaseRepository;	
	@Autowired
	ReRegDtRepository reRegDtRepository;		
	@Autowired
	MerClientRelRepository merClientRelRepository;
	@Autowired
	AccountService accountService;
	
	@Autowired
	AppProperties appProperties;
	
	
	
	
	
	
	@BeforeEach
	public void setUp() {
		this.merBsRepository.deleteAll();
		this.phoneRepository.deleteAll();
		this.contactRepository.deleteAll();
		this.merClientBsRepository.deleteAll();
		this.reRegBaseRepository.deleteAll();
		this.reRegDtRepository.deleteAll();
		this.merClientRelRepository.deleteAll();
	}
	
	
	
	@Test
	public void getMerBsInfoTestLogin() throws Exception {
		
		Phone phone = Phone.builder().contactCtnt("01082821111").build();
		Email email = Email.builder().contactCtnt("like@naver.com").build();
		contactRepository.save(phone);
		contactRepository.save(email);
				
		//MERBS
		MerBs merBs = MerBs.builder().merNm("hehe").build();	
		merBs.setPhone(phone);
		merBs.setEmail(email);
					
		merBsRepository.save(merBs);	
		
		em.flush();	
		em.clear();
			
	    mockMvc.perform(
				get("/api/merbs/"+merBs.getMerNo())
				.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
				.contentType(MediaType.APPLICATION_JSON)		
				.accept(MediaTypes.HAL_JSON))						
		.andDo(print())
		.andExpect(status().isOk())			
		.andExpect(jsonPath("merNo").exists())  
		.andExpect(jsonPath("merNo").value(merBs.getMerNo()))
		.andExpect(jsonPath("phoneNumber").value(phone.getContactCtnt()))
		.andExpect(jsonPath("email").value(email.getContactCtnt()))
		.andExpect(jsonPath("_links.self").exists())	
		.andExpect(jsonPath("_links.create-merbs").exists())
		.andExpect(jsonPath("_links.read-merbs").exists())	 
		.andExpect(jsonPath("_links.update-merbs").exists())	 
		.andExpect(jsonPath("_links.delete-merbs").exists())	 	
		.andExpect(jsonPath("_links.merbs-list").exists())	 	
		;	    	
	}
	
	
	
	
	
	@Test
	public void getMerBsInfoTestNoLogin() throws Exception {
		
		Phone phone = Phone.builder().contactCtnt("01082821111").build();
		Email email = Email.builder().contactCtnt("like@naver.com").build();
		contactRepository.save(phone);
		contactRepository.save(email);
				
		//MERBS
		MerBs merBs = MerBs.builder().merNm("hehe").build();	
		merBs.setPhone(phone);
		merBs.setEmail(email);
					
		merBsRepository.save(merBs);	
		
		em.flush();	
		em.clear();
			
	    mockMvc.perform(
				get("/api/merbs/"+merBs.getMerNo())		
				.contentType(MediaType.APPLICATION_JSON)		
				.accept(MediaTypes.HAL_JSON))						
		.andDo(print())
		.andExpect(status().isOk())			
		.andExpect(jsonPath("merNo").exists())  
		.andExpect(jsonPath("merNo").value(merBs.getMerNo()))
		.andExpect(jsonPath("phoneNumber").value(phone.getContactCtnt()))
		.andExpect(jsonPath("email").value(email.getContactCtnt()))
		.andExpect(jsonPath("_links.self").exists())		
		.andExpect(jsonPath("_links.read-merbs").exists())	 	 	
		.andExpect(jsonPath("_links.merbs-list").exists())	 	
		;
	    	
	}
	
		
	@Test
	public void saveMerBsInfoTest() throws Exception {
		
		MerBsDto merBsDto = MerBsDto.builder()
				            .merNm("하하하하!!!")
				            .regDtm("20200518102233")
				            .bbrNo("288383")
				            .phoneNumber("01033445958")
				            .email("like@naver.com")
							.build() ;						
			
	    mockMvc.perform(
				post("/api/merbs")
				.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
				.contentType(MediaType.APPLICATION_JSON)			
				.accept(MediaTypes.HAL_JSON)
			   .content(objectMapper.writeValueAsString(merBsDto))	)						
		.andDo(print())
		.andExpect(status().isCreated())		;	    		
			
	   MerBs merBs =merBsRepository.findByMerNm("하하하하!!!").get(0);
	   	   	   
	   assertThat(merBs.getBbrNo()).isEqualTo(merBsDto.getBbrNo());
	   assertThat(merBs.getRegDtm()).isEqualTo(merBsDto.getRegDtm());
	   assertThat(merBs.getPhone().getContactCtnt()).isEqualTo(merBsDto.getPhoneNumber());
	   assertThat(merBs.getEmail().getContactCtnt()).isEqualTo(merBsDto.getEmail());
	        
		
	}
	
	
	
@Test
public void saveMerBsInfoTestIncorrectData() throws Exception {
	
	MerBsDto merBsDto = MerBsDto.builder()
			            .merNm("하하하하!!!")
			            .regDtm("202005181022332")
			            .bbrNo("2883833")
			            .phoneNumber("01033445958")
			            .email("like@naver.com")
						.build() ;						
		
    mockMvc.perform(
			post("/api/merbs")
			.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
			.contentType(MediaType.APPLICATION_JSON)			
			.accept(MediaTypes.HAL_JSON)
		   .content(objectMapper.writeValueAsString(merBsDto))	)						
	.andDo(print())
	.andExpect(status().isBadRequest())		;	  		
	
        
	
}
	
	
	@Test
	public void saveMerBsInfoTestNoLogin() throws Exception {
		
		MerBsDto merBsDto = MerBsDto.builder()
				            .merNm("하하하하!!!")
				            .regDtm("202005182222")
				            .bbrNo("288383")
				            .phoneNumber("01033445958")
				            .email("like@naver.com")
							.build() ;						
			
	    mockMvc.perform(
				post("/api/merbs")			
				.contentType(MediaType.APPLICATION_JSON)			
				.accept(MediaTypes.HAL_JSON)
			   .content(objectMapper.writeValueAsString(merBsDto))	)						
		.andDo(print())
		.andExpect(status().isUnauthorized()) ;  //무허가 접근인지 확인   		     
		
	}
		
	
	
@Test
public void updateMerBsInfoTest() throws Exception {
	
	MerBsDto merBsDto = MerBsDto.builder()
            .merNm("하하하하!!!")
            .regDtm("20200518222210")
            .bbrNo("288383")
            .phoneNumber("01033445958")
            .email("like@naver.com")
			.build() ;			
	
    mockMvc.perform(
				post("/api/merbs")
				.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
				.contentType(MediaType.APPLICATION_JSON)			
				.accept(MediaTypes.HAL_JSON)
			   .content(objectMapper.writeValueAsString(merBsDto))	)						
		.andDo(print())
		.andExpect(status().isCreated());
    
	MerBs merBs =merBsRepository.findByMerNm("하하하하!!!").get(0);
	   	   	      
    
    
	MerBsDto merBsDto2 = MerBsDto.builder()
			.merNo(merBs.getMerNo())
            .merNm("호호호호호!!!")
            .regDtm("20200519222222")
            .bbrNo("999999")
            .phoneNumber("01081815923")
            .email("hate@naver.com")
			.build() ;	
    
	mockMvc.perform(
			put("/api/merbs")
			.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
			.contentType(MediaType.APPLICATION_JSON)			
			.accept(MediaTypes.HAL_JSON)
		   .content(objectMapper.writeValueAsString(merBsDto2))	)						
	.andDo(print()) ;
	
	Optional<MerBs> optMerBs =merBsRepository.findById(merBs.getMerNo());
    assertThat(optMerBs.isEmpty()).isEqualTo(false);
		
	MerBs merBs2 = optMerBs.get();
	assertThat(merBs2.getMerNo()).isEqualTo(merBsDto2.getMerNo());
	assertThat(merBs2.getMerNm()).isEqualTo(merBsDto2.getMerNm());
	assertThat(merBs2.getRegDtm()).isEqualTo(merBsDto2.getRegDtm());
	assertThat(merBs2.getBbrNo()).isEqualTo(merBsDto2.getBbrNo());
	assertThat(merBs2.getPhone().getContactCtnt()).isEqualTo(merBsDto2.getPhoneNumber());
	assertThat(merBs2.getEmail().getContactCtnt()).isEqualTo(merBsDto2.getEmail());
               	
}




@Test  
public void updateMerBsInfoTestNoLogin() throws Exception {
	
	MerBsDto merBsDto = MerBsDto.builder()
            .merNm("하하하하!!!")
            .regDtm("202005182222")
            .bbrNo("288383")
            .phoneNumber("01033445958")
            .email("like@naver.com")
			.build() ;			
	
    mockMvc.perform(
				post("/api/merbs")
				.contentType(MediaType.APPLICATION_JSON)			
				.accept(MediaTypes.HAL_JSON)
			   .content(objectMapper.writeValueAsString(merBsDto))	)						
		.andDo(print())
		.andExpect(status().isUnauthorized());
               	
}






	
	
	@Test
	public void deleteMerBsInfoTest() throws Exception {
		
		//MERBS
		MerBs merBs = MerBs.builder().merNm("hehe").build();	
					
		merBsRepository.save(merBs);	
		
	    mockMvc.perform(
					delete("/api/merbs/"+ merBs.getMerNo())
					.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
					.contentType(MediaType.APPLICATION_JSON)			
					.accept(MediaTypes.HAL_JSON)					)	
			.andDo(print())
			.andExpect(status().isOk())		;
	    
	    
	    Optional<MerBs> optMerbs = merBsRepository.findById(merBs.getMerNo());
	    
	    assertThat(optMerbs.isEmpty()).isEqualTo(true);
	    
	}
	
	
	@Test
	public void deleteMerBsInfoTestNoLogin() throws Exception {
		
		//MERBS
		MerBs merBs = MerBs.builder().merNm("hehe").build();	
					
		merBsRepository.save(merBs);	
		
	    mockMvc.perform(
					delete("/api/merbs/"+ merBs.getMerNo())
					.contentType(MediaType.APPLICATION_JSON)			
					.accept(MediaTypes.HAL_JSON)					)	
			.andDo(print())
			.andExpect(status().isUnauthorized())		;
	    	    
	}
	
		
	
	@Test
	public void getMerBsInfoListTest() throws Exception {
		
		makeMerBsData();
		
	    mockMvc.perform(
					get("/api/merbs/")
					.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
					.param("page","0")
					.param("size", "5")
					.param("sort","merNo,DESC")
					.contentType(MediaType.APPLICATION_JSON)			
					.accept(MediaTypes.HAL_JSON)					)	
			.andDo(print())
			.andExpect(status().isOk())		
			.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.self").exists())
	    	.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.merbs-list").exists())
	    	.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.read-merbs").exists())
	    	.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.create-merbs").exists())
	    	.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.update-merbs").exists())
	    	.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.delete-merbs").exists())	    	
	    	.andExpect(jsonPath("_links.first").exists())
	    	.andExpect(jsonPath("_links.self").exists())
	    	.andExpect(jsonPath("_links.next").exists())
	    	.andExpect(jsonPath("_links.last").exists())
	    
			;	    
	}
		
	
	
	@Test
	public void getMerBsInfoListTestNoLogin() throws Exception {
		
		makeMerBsData();
		
	    mockMvc.perform(
					get("/api/merbs/")				
					.param("page","0")
					.param("size", "5")
					.param("sort","merNo,DESC")
					.contentType(MediaType.APPLICATION_JSON)			
					.accept(MediaTypes.HAL_JSON)					)	
			.andDo(print())
			.andExpect(status().isOk())			   
		.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.self").exists())
    	.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.merbs-list").exists())
    	.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.read-merbs").exists())
    	.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.create-merbs").doesNotExist())
    	.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.update-merbs").doesNotExist())
    	.andExpect(jsonPath("_embedded.merbsinfolist[0]._links.delete-merbs").doesNotExist())	    	
    	.andExpect(jsonPath("_links.first").exists())
    	.andExpect(jsonPath("_links.self").exists())
    	.andExpect(jsonPath("_links.next").exists())
    	.andExpect(jsonPath("_links.last").exists())
    	
    	;
	}
	
	
	
	
	

	public void makeMerBsData() {
		
		for(int i=0 ; i<10; i++) {	
			
			MerBs merBs = MerBs.builder().merNm("하하"+ i)								
                    .build();		
			merBsRepository.save(merBs);						
		}
		
		
		em.flush();	
		em.clear();
	}
	
	
		
//  시작 히 아이디 하나 생성해도록 설정해둠 그래서  현재 필요 없음
	private Account createAccount() {
		 
		 //Given
		 Account account = Account.builder()
		 		.email(appProperties.getUserUsername())
		 		.password(appProperties.getUserPassword())	
		 		.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
		 		.build();
		 
		return  this.accountService.saveAccount(account) ;	 
				
	}
	
	private String getBearerToken(boolean needToCreateAccount) throws Exception {
		return "Bearer " + getAccessToken(needToCreateAccount) ;
	}
	
	

	private String getAccessToken(boolean needToCreateAccount) throws Exception {
	    	
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
