package portfolio.restbootjpa.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedRequestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import portfolio.restbootjpa.accounts.AccountService;
import portfolio.restbootjpa.common.AppProperties;
import portfolio.restbootjpa.common.BaseTest;
import portfolio.restbootjpa.constraint.MerClientType;
import portfolio.restbootjpa.dto.MerBsDto;
import portfolio.restbootjpa.dto.MerClientDto;
import portfolio.restbootjpa.entity.Email;
import portfolio.restbootjpa.entity.MerBs;
import portfolio.restbootjpa.entity.MerClientBs;
import portfolio.restbootjpa.entity.Phone;
import portfolio.restbootjpa.repository.ContactRepository;
import portfolio.restbootjpa.repository.MerBsRepository;
import portfolio.restbootjpa.repository.MerClientBsRepository;
import portfolio.restbootjpa.repository.MerClientRelRepository;
import portfolio.restbootjpa.repository.PhoneRepository;
import portfolio.restbootjpa.repository.ReRegBaseRepository;
import portfolio.restbootjpa.repository.ReRegDtRepository;
import portfolio.restbootjpa.service.MerClientService;


public class MerClientBsControllerTest extends BaseTest{
	
	
@Autowired
protected MockMvc mockMvc;  

ObjectMapper objectMapper = new ObjectMapper();	

@PersistenceContext  
private EntityManager em;

@Autowired 
MerBsRepository merBsRepository;	
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
MerClientService merClientService;
	
@BeforeEach
public void setUp() {
	this.merBsRepository.deleteAll();
	this.contactRepository.deleteAll();
	this.merClientBsRepository.deleteAll();
	this.reRegBaseRepository.deleteAll();
	this.reRegDtRepository.deleteAll();
	this.merClientRelRepository.deleteAll();
}


@Test
public void getMerClientInfoTestLogin() throws Exception {
	
	MerClientDto merClientDto = MerClientDto.builder().clientNm("호랑씨").email("ori@naver.com").phoneNumber("01034334433").type(MerClientType.BUISNESS.name()).build();
	MerClientBs merClientBs = merClientService.saveMerClientFromDto(merClientDto);
		
	Optional<MerClientBs> optmerClientBs2 = merClientService.findMerClientBsById(merClientBs.getClientNo());		
	assertThat(optmerClientBs2.isEmpty() == false).isTrue();    	
	
    mockMvc.perform(
				get("/api/merclient/"+merClientBs.getClientNo())
				.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
				.contentType(MediaType.APPLICATION_JSON)		
				.accept(MediaTypes.HAL_JSON))						
		.andDo(print())
		.andExpect(status().isOk())			
		.andExpect(jsonPath("clientNo").exists())  
		.andExpect(jsonPath("clientNo").value(merClientBs.getClientNo()))
		.andExpect(jsonPath("phoneNumber").value(merClientBs.getPhone().getContactCtnt()))
		.andExpect(jsonPath("email").value(merClientBs.getEmail().getContactCtnt())) 
		.andExpect(jsonPath("_links.create-merclient").exists())
		.andExpect(jsonPath("_links.read-merclient").exists())	 
		.andExpect(jsonPath("_links.update-merclient").exists())	 
		.andExpect(jsonPath("_links.delete-merclient").exists())	 	
		.andExpect(jsonPath("_links.merclient-list").exists())	 
		.andDo(document("get-merclient",
				links(   
						  linkWithRel("merclient-list").description("고객 리스트 조회"),
						  linkWithRel("read-merclient").description("고객 정보 조회"),
						  linkWithRel("create-merclient").description("고객 신규 등록(로그인 이후 링크 제공,사용 가능)"),						
						  linkWithRel("update-merclient").description("고객 정보 수정(로그인 이후 링크 제공,사용 가능)"),							
						  linkWithRel("delete-merclient").description("고객 정보 삭제(로그인 이후 링크 제공,사용 가능)")
					),				
			requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description("authorization header(비로그인시 제외)"),
					headerWithName(HttpHeaders.ACCEPT).description("accept header"), 
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
			),		
			relaxedResponseFields(
					fieldWithPath("clientNo").description("고객 번호"),
					fieldWithPath("clientNm").description("고객명"),
					fieldWithPath("type").description("고객 형태(사업자, 개인)"),				
					fieldWithPath("phoneNumber").description("폰 넘버"),
					fieldWithPath("email").description("이메일주소")		
			)));
		
		
		;
    
}


@Test
public void getMerClientInfoTestNoLogin() throws Exception {
	
	MerClientDto merClientDto = MerClientDto.builder().clientNm("호랑씨").email("ori@naver.com").phoneNumber("01034334433").type(MerClientType.BUISNESS.name()).build();
	MerClientBs merClientBs = merClientService.saveMerClientFromDto(merClientDto);
		
	Optional<MerClientBs> optmerClientBs2 = merClientService.findMerClientBsById(merClientBs.getClientNo());		
	assertThat(optmerClientBs2.isEmpty() == false).isTrue();    
					
    mockMvc.perform(
				get("/api/merclient/"+merClientBs.getClientNo())			
				.contentType(MediaType.APPLICATION_JSON)		
				.accept(MediaTypes.HAL_JSON))						
		.andDo(print())
		.andExpect(status().isOk())			
		.andExpect(jsonPath("clientNo").exists())  
		.andExpect(jsonPath("clientNo").value(merClientBs.getClientNo()))
		.andExpect(jsonPath("phoneNumber").value(merClientBs.getPhone().getContactCtnt()))
		.andExpect(jsonPath("email").value(merClientBs.getEmail().getContactCtnt())) 
		.andExpect(jsonPath("_links.create-merclient").doesNotExist())
		.andExpect(jsonPath("_links.update-merclient").doesNotExist())	 
		.andExpect(jsonPath("_links.delete-merclient").doesNotExist())	 	
		.andExpect(jsonPath("_links.merclient-list").exists())	 
		.andExpect(jsonPath("_links.read-merclient").exists() )	 ;	
    	
}


@Test
public void saveMerClientInfoTest() throws Exception {
	
	MerClientDto merClientDto = MerClientDto.builder().clientNm("호랑씨").email("ori@naver.com").phoneNumber("01034334433").type(MerClientType.BUISNESS.name()).build();
						
		
    mockMvc.perform(
			post("/api/merclient")
			.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
			.contentType(MediaType.APPLICATION_JSON)			
			.accept(MediaTypes.HAL_JSON)
		   .content(objectMapper.writeValueAsString(merClientDto))	)						
	.andDo(print())
	.andExpect(status().isCreated())		
	.andDo(document("create-merclient",
			links(    linkWithRel("merclient-list").description("고객 리스트 조회"),
					  linkWithRel("read-merclient").description("고객 정보 조회"),
					  linkWithRel("create-merclient").description("고객 신규 등록(로그인 이후 링크 제공,사용 가능)"),						
					  linkWithRel("update-merclient").description("고객 정보 수정(로그인 이후 링크 제공,사용 가능)"),							
					  linkWithRel("delete-merclient").description("고객 정보 삭제(로그인 이후 링크 제공,사용 가능)")
				),				
		requestHeaders(
				headerWithName(HttpHeaders.AUTHORIZATION).description("authorization header"),
				headerWithName(HttpHeaders.ACCEPT).description("accept header"), 
				headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
		),
		relaxedRequestFields(			
				fieldWithPath("clientNo").description("고객 번호"),
				fieldWithPath("clientNm").description("고객명"),
				fieldWithPath("type").description("고객 형태(사업자, 개인)"),	
				fieldWithPath("phoneNumber").description("폰 넘버"),
				fieldWithPath("email").description("이메일주소")															
				
		),
		relaxedResponseFields(
				fieldWithPath("clientNo").description("고객 번호"),
				fieldWithPath("clientNm").description("고객명"),
				fieldWithPath("type").description("고객 형태(사업자, 개인)"),				
				fieldWithPath("phoneNumber").description("폰 넘버"),
				fieldWithPath("email").description("이메일주소")			
		)));
	
	
	;	    		
		
   List<MerClientBs> merClientBsList = merClientBsRepository.findAll();
   MerClientBs merClientBs = merClientBsList.get(0);   
   	   	   
   assertThat(merClientDto.getClientNm()).isEqualTo(merClientBs.getClientNm());
   
   assertThat(merClientDto.getEmail()).isEqualTo(merClientBs.getEmail().getContactCtnt());
   assertThat(merClientDto.getPhoneNumber()).isEqualTo(merClientBs.getPhone().getContactCtnt());
   assertThat(merClientDto.getType()).isEqualTo(merClientBs.getType().name());
      
	
}



@Test
public void saveMerClientInfoTestNoLogin() throws Exception {
	
	MerClientDto merClientDto = MerClientDto.builder().clientNm("호랑씨").email("ori@naver.com").phoneNumber("01034334433").type(MerClientType.BUISNESS.name()).build();
						
		
    mockMvc.perform(
			post("/api/merclient")	
			.contentType(MediaType.APPLICATION_JSON)			
			.accept(MediaTypes.HAL_JSON)
		   .content(objectMapper.writeValueAsString(merClientDto))	)						
	.andDo(print())
	.andExpect(status().isUnauthorized())	;
    
}



@Test
public void updateMerClientInfoTest() throws Exception {	
	
	MerClientDto merClientDto = MerClientDto.builder().clientNm("호랑씨").email("ori@naver.com").phoneNumber("01034334433").type(MerClientType.BUISNESS.name()).build();
	
	
    mockMvc.perform(
			post("/api/merclient")
			.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
			.contentType(MediaType.APPLICATION_JSON)			
			.accept(MediaTypes.HAL_JSON)
		   .content(objectMapper.writeValueAsString(merClientDto))	)						
	.andDo(print())
	.andExpect(status().isCreated())		
	
	
	;	    		
		
   List<MerClientBs> merClientBsList = merClientBsRepository.findAll();
   MerClientBs merClientBs = merClientBsList.get(0);   
   
   
   MerClientDto merClientDto2 = MerClientDto.builder().clientNo(merClientBs.getClientNo()).clientNm("여우씨").email("vat@naver.com").phoneNumber("01034334499").type(MerClientType.INVIDUAL.name()).build();
	
	
   mockMvc.perform(
			put("/api/merclient")
			.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
			.contentType(MediaType.APPLICATION_JSON)			
			.accept(MediaTypes.HAL_JSON)
		   .content(objectMapper.writeValueAsString(merClientDto2))	)						
	.andDo(print())
	.andExpect(status().isOk())	
	.andDo(document("update-merclient",
			links(    linkWithRel("merclient-list").description("고객 리스트 조회"),
					  linkWithRel("read-merclient").description("고객 정보 조회"),
					  linkWithRel("create-merclient").description("고객 신규 등록(로그인 이후 링크 제공,사용 가능)"),						
					  linkWithRel("update-merclient").description("고객 정보 수정(로그인 이후 링크 제공,사용 가능)"),							
					  linkWithRel("delete-merclient").description("고객 정보 삭제(로그인 이후 링크 제공,사용 가능)")
				),				
		requestHeaders(
				headerWithName(HttpHeaders.AUTHORIZATION).description("authorization header"),
				headerWithName(HttpHeaders.ACCEPT).description("accept header"), 
				headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
		),		
		relaxedRequestFields(			
				fieldWithPath("clientNo").description("고객 번호"),
				fieldWithPath("clientNm").description("고객명"),
				fieldWithPath("type").description("고객 형태(사업자, 개인)"),
				fieldWithPath("phoneNumber").description("폰 넘버"),
				fieldWithPath("email").description("이메일주소")															
				
		),
		relaxedResponseFields(
				fieldWithPath("clientNo").description("고객 번호"),
				fieldWithPath("clientNm").description("고객명"),
				fieldWithPath("type").description("고객 형태(사업자, 개인)"),
				fieldWithPath("phoneNumber").description("폰 넘버"),
				fieldWithPath("email").description("이메일주소")			
		)));
	
         
   List<MerClientBs> merClientBsList2 = merClientBsRepository.findAll();
   MerClientBs merClientBs2 = merClientBsList2.get(0);  
   
   assertThat(merClientDto2.getClientNo()).isEqualTo(merClientBs2.getClientNo());
   assertThat(merClientDto2.getClientNm()).isEqualTo(merClientBs2.getClientNm());
   assertThat(merClientDto2.getEmail()).isEqualTo(merClientBs2.getEmail().getContactCtnt());
   assertThat(merClientDto2.getPhoneNumber()).isEqualTo(merClientBs2.getPhone().getContactCtnt());
   assertThat(merClientDto2.getType()).isEqualTo(merClientBs2.getType().name());	
}

@Test
public void deleteMerClientBsInfoTest() throws Exception {
	
	MerClientDto merClientDto = MerClientDto.builder().clientNm("호랑씨").email("ori@naver.com").phoneNumber("01034334433").type(MerClientType.BUISNESS.name()).build();
	MerClientBs merClientBs = merClientService.saveMerClientFromDto(merClientDto);
	
    mockMvc.perform(
				delete("/api/merclient/"+ merClientBs.getClientNo())
				.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
				.contentType(MediaType.APPLICATION_JSON)			
				.accept(MediaTypes.HAL_JSON)					)	
		.andDo(print())
		.andExpect(status().isOk())		
		.andDo(document("delete-merclient",
				links(    linkWithRel("merclient-list").description("고객 리스트 조회")				
					),				
			requestHeaders(
					headerWithName(HttpHeaders.AUTHORIZATION).description("authorization header"),
					headerWithName(HttpHeaders.ACCEPT).description("accept header"), 
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
			)			
			));
				
    Optional<MerClientBs> merClientBs2 = merClientBsRepository.findById(merClientBs.getClientNo());
    
    assertThat(merClientBs2.isEmpty()).isEqualTo(true);  
    
}


@Test
public void getMerClientBsInfoListTestLogin() throws Exception {
	
	makeMerClientData();
	
    mockMvc.perform(
				get("/api/merclient/")				
				.param("page","1")
				.param("size", "2")
				.param("sort","clientNo,DESC")
				.header(HttpHeaders.AUTHORIZATION,  getBearerToken(true))
				.contentType(MediaType.APPLICATION_JSON)			
				.accept(MediaTypes.HAL_JSON)					)	
		.andDo(print())
		.andExpect(status().isOk())			   
	.andExpect(jsonPath("_embedded.clientinfolist[0]._links.merclient-list").exists())
	.andExpect(jsonPath("_embedded.clientinfolist[0]._links.read-merclient").exists())
	.andExpect(jsonPath("_embedded.clientinfolist[0]._links.create-merclient").exists())
	.andExpect(jsonPath("_embedded.clientinfolist[0]._links.update-merclient").exists())
	.andExpect(jsonPath("_embedded.clientinfolist[0]._links.delete-merclient").exists())	    	
	.andExpect(jsonPath("_links.self").exists())
	.andExpect(jsonPath("_links.first").exists())
	.andExpect(jsonPath("_links.last").exists())
	.andExpect(jsonPath("_links.prev").exists())
	.andExpect(jsonPath("_links.next").exists())
  	.andDo(document("get-merclientlist",
			links(    linkWithRel("self").description("현 페이지 링크"),
					  linkWithRel("first").description("첫 페이지"),
					  linkWithRel("last").description("마지막 페이지"),	
					  linkWithRel("prev").description("이전 페이지"),	
					  linkWithRel("next").description("다음 페이지")	    					  
				 ),				
		requestHeaders(
				headerWithName(HttpHeaders.AUTHORIZATION).description("authorization header(비로그인시 제외)"),
				headerWithName(HttpHeaders.ACCEPT).description("accept header"), 
				headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
		)

		));

	
}





@Test
public void getMerClientBsInfoListTestNoLogin() throws Exception {
	
	makeMerClientData();
	
    mockMvc.perform(
				get("/api/merclient/")				
				.param("page","0")
				.param("size", "5")
				.param("sort","clientNo,DESC")
				.contentType(MediaType.APPLICATION_JSON)			
				.accept(MediaTypes.HAL_JSON)					)	
		.andDo(print())
		.andExpect(status().isOk())			   
	.andExpect(jsonPath("_embedded.clientinfolist[0]._links.merclient-list").exists())
	.andExpect(jsonPath("_embedded.clientinfolist[0]._links.read-merclient").exists())
	.andExpect(jsonPath("_embedded.clientinfolist[0]._links.create-merclient").doesNotExist())
	.andExpect(jsonPath("_embedded.clientinfolist[0]._links.update-merclient").doesNotExist())
	.andExpect(jsonPath("_embedded.clientinfolist[0]._links.delete-merclient").doesNotExist())	    	
	.andExpect(jsonPath("_links.first").exists())
	.andExpect(jsonPath("_links.self").exists())
	.andExpect(jsonPath("_links.next").exists())
	.andExpect(jsonPath("_links.last").exists())
	
	;
}




public void makeMerClientData() {
	
	for(int i=0 ; i<10; i++) {	
		
		MerClientDto merClientDto = MerClientDto.builder().clientNm("호랑씨"+i).email("ori@naver.com").phoneNumber("01034334433").type(MerClientType.BUISNESS.name()).build();
		 merClientService.saveMerClientFromDto(merClientDto);					
	}
}


	
}
