package portfolio.restbootjpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import portfolio.restbootjpa.constraint.MerClientType;
import portfolio.restbootjpa.dto.MerClientDto;
import portfolio.restbootjpa.entity.MerClientBs;
import portfolio.restbootjpa.service.MerClientService;
import portfolio.restbootjpa.service.MerService;

@SpringBootTest
@Transactional
public class ServiceTest {

@Autowired
MerClientService merClientService;
@Autowired
MerService merService;

  @Test
  public void MerClientBsCRUDTest() {	  
	  
 
    MerClientDto merClientDto = MerClientDto.builder().clientNm("호랑씨").email("ori@naver.com").PhoneNumber("01034334433").Type(MerClientType.BUISNESS.name()).build();
	MerClientBs merClientBs = merClientService.saveMerClientFromDto(merClientDto);
	
	
	Optional<MerClientBs> optmerClientBs2 = merClientService.findMerClientBsById(merClientBs.getClientNo());
	
    assertThat(optmerClientBs2.isEmpty() == false).isTrue();    
    MerClientBs merClientBs2 = optmerClientBs2.get();
     
    assertThat(merClientBs2.getClientNo()).isEqualTo(merClientBs.getClientNo()) ;
    assertThat(merClientBs2.getClientNm()).isEqualTo(merClientDto.getClientNm()) ;
    assertThat(merClientBs2.getPhone().getContactCtnt()).isEqualTo(merClientDto.getPhoneNumber()) ;    
    assertThat(merClientBs2.getEmail().getContactCtnt()).isEqualTo(merClientDto.getEmail()) ;  
    assertThat(merClientBs2.getType().name()).isEqualTo(merClientDto.getType()) ;  
    

    
    merClientDto.setClientNo(merClientBs.getClientNo());
    merClientDto.setClientNm("토끼씨");
    merClientDto.setType(MerClientType.INVIDUAL.name());
    merClientDto.setEmail("nell@naver.com");
    merClientDto.setPhoneNumber("01033993399");
               
    
    merClientService.updateMerClientBs(merClientDto);
       
	Optional<MerClientBs> optmerClientBs3= merClientService.findMerClientBsById(merClientBs.getClientNo());
	
    assertThat(optmerClientBs3.isEmpty() == false).isTrue();    
    MerClientBs merClientBs3 = optmerClientBs3.get();
    
    assertThat(merClientBs3.getClientNo()).isEqualTo(merClientBs.getClientNo()) ;
    assertThat(merClientBs3.getClientNm()).isEqualTo(merClientDto.getClientNm()) ;
    assertThat(merClientBs3.getPhone().getContactCtnt()).isEqualTo(merClientDto.getPhoneNumber()) ;    
    assertThat(merClientBs3.getEmail().getContactCtnt()).isEqualTo(merClientDto.getEmail()) ;  
    assertThat(merClientBs3.getType().name()).isEqualTo(merClientDto.getType()) ;  
    
    merClientService.deleteMerBs(merClientBs3);
	Optional<MerClientBs> optmerClientBs4= merClientService.findMerClientBsById(merClientBs.getClientNo());
	assertThat(optmerClientBs4.isEmpty()).isTrue();    
      
    

  }
	
	

}
