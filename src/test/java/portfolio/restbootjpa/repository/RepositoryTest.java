package portfolio.restbootjpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import portfolio.restbootjpa.Entity.ContactItem;
import portfolio.restbootjpa.Entity.Email;
import portfolio.restbootjpa.Entity.MerBs;
import portfolio.restbootjpa.Entity.MerClientBs;
import portfolio.restbootjpa.Entity.Phone;
import portfolio.restbootjpa.Entity.ReRegBase;
import portfolio.restbootjpa.Entity.ReRegDt;


@SpringBootTest
@Transactional
public class RepositoryTest {	
	
	@PersistenceContext  
	private EntityManager em;
				
	@Autowired 
	MerBsRepository merBsRepository;	
	@Autowired
	PhoneRepository phoneRepository; //TODO 테스트용으로 만들었으나, contactRepository를 캐스팅해서 사용하면 됨. 삭제할 것
	@Autowired
	ContactRepository contactRepository;
	@Autowired
	MerClientBsRepository merClientBsRepository;
	@Autowired
	ReRegBaseRepository reRegBaseRepository;	
	@Autowired
	ReRegDtRepository reRegDtRepository;
				
		
	@Test
	public void merBsCRUDTest() {
		
		//MERBS
		MerBs merBs = MerBs.builder().merNm("하하").build();
				
		merBsRepository.save(merBs);		
		
		em.flush();				
		em.clear();	
		
		Optional<MerBs> optionalMerBs2 = merBsRepository.findById(merBs.getMerNo());		
		assertThat(optionalMerBs2.isEmpty() == false).isEqualTo(true);
				
		MerBs merBs2 = optionalMerBs2.get();
		
		assertThat(merBs.getMerNo()).isEqualTo(merBs2.getMerNo());
				
		merBsRepository.delete(merBs);	
		
		em.flush();		
		
		Optional<MerBs> merBs3 = merBsRepository.findById(merBs.getMerNo());			 
		assertThat(merBs3.isEmpty()).isEqualTo(true);
		
		
	}
	
	
	
	@Test
	public void merBsJoinTest() {
		
		MerClientBs merClientBs = MerClientBs.builder().clientNm("룩룩!!").build();
		
		merClientBsRepository.save(merClientBs);
		
		
		//MERBS
		MerBs merBs = MerBs.builder().merNm("요요!!!!")
									 .merClientBs(merClientBs) 
				                     .build();		
				
		merBsRepository.save(merBs);	
		
		
		em.flush();				
		em.clear();	
		
		Optional<MerBs> optionalMerBs2 = merBsRepository.findById(merBs.getMerNo());		
		assertThat(optionalMerBs2.isEmpty() == false).isEqualTo(true);
				
		MerBs merBs2 = optionalMerBs2.get();
		
		
		assertThat(merBs.getMerNo()).isEqualTo(merBs2.getMerNo());				
		System.out.println("======================lazy 여부 확인");
		
		assertThat(merClientBs.getClientNm()).isEqualTo(merBs2.getMerClientBs().getClientNm());		
		
		
	}
	
	
	@Test
	public void phoneCRUDTest() {
				
		//phone
				
		Phone phone = Phone.builder().contactCtnt("01022882828").build();
				
		phoneRepository.save(phone);		
		
		em.flush();				
		em.clear();	
		
		Optional<Phone> optionalPhone2 = phoneRepository.findById(phone.getId());				
		assertThat(optionalPhone2.isEmpty() == false).isEqualTo(true);		
		Phone phone2 = optionalPhone2.get();
		assertThat(phone2.getId()).isEqualTo(phone.getId());
				
		contactRepository.delete(phone2);		
		
		em.flush();		
				
		Optional<Phone> phone3 = phoneRepository.findById(phone2.getId());			 
		assertThat(phone3.isEmpty()).isEqualTo(true);
		
		
	}	
	
	
	@Test
	public void contactCRUDTest() {
		
						
		//contact
		
		Phone phone = Phone.builder().contactCtnt("0102288282822").telCompany("KT").build();
	
				
		contactRepository.save(phone);		
		
		em.flush();				
		em.clear();	
		
		Optional<ContactItem> optContactItem = contactRepository.findById(phone.getId());	
				
		assertThat(optContactItem.isEmpty() == false).isEqualTo(true);
		
		Phone phoneCasting =(Phone) optContactItem.get();
		
		assertThat(phoneCasting.getId()).isEqualTo(phone.getId());
		assertThat(phoneCasting.getTelCompany()).isEqualTo(phone.getTelCompany());
		
	//	System.out.println("phone casting number :" + phoneCasting.getTelCompany());
				
		contactRepository.delete(phoneCasting);		
		
		em.flush();		
				
		Optional<ContactItem> testPhone3 = contactRepository.findById(phoneCasting.getId());			 
		assertThat(testPhone3.isEmpty()).isEqualTo(true);
		
		
		
	}
	
	
	
	@Test
	public void merContactTest() {
								
		//phone
		
		Phone phone = Phone.builder().contactCtnt("01022882828").build();
				
		phoneRepository.save(phone);		
		
		em.flush();				
		em.clear();	
		
		Optional<Phone> optionalPhone2 = phoneRepository.findById(phone.getId());				
		assertThat(optionalPhone2.isEmpty() == false).isEqualTo(true);		
		Phone phone2 = optionalPhone2.get();
		assertThat(phone2.getId()).isEqualTo(phone.getId());
		
		
		
		Email email = Email.builder().contactCtnt("likeuap@naver.com").build();
		
		contactRepository.save(email);		
		
		em.flush();				
		em.clear();	
		
		Optional<ContactItem> optEmail = contactRepository.findById(email.getId());				
		assertThat(optEmail.isEmpty() == false).isEqualTo(true);		
		Email castingEmail = (Email)optEmail.get();
		assertThat(email.getId()).isEqualTo(castingEmail.getId());
		
		
		
		//MERBS
		MerBs merBs = MerBs.builder().merNm("냐냐").phone(phone).build();
				
		merBsRepository.save(merBs);		
		
		em.flush();				
		em.clear();	
		
		Optional<MerBs> optionalMerBs2 = merBsRepository.findById(merBs.getMerNo());		
		assertThat(optionalMerBs2.isEmpty() == false).isEqualTo(true);
				
		MerBs merBs2 = optionalMerBs2.get();
		
		assertThat(merBs.getMerNo()).isEqualTo(merBs2.getMerNo());
	
			
		merBs2.setPhone(phone);
		merBs2.setEmail(email);
		
		em.flush();				
		em.clear();	
		
		
		
		Optional<MerBs> optMerBs3 = merBsRepository.findById(merBs2.getMerNo());
		assertThat(optMerBs3.isEmpty() == false).isEqualTo(true);		
		MerBs castingMerBs3 = optMerBs3.get();
		assertThat(castingMerBs3.getPhone().getContactCtnt()).isEqualTo(phone.getContactCtnt());	
		
		
		
		
		
		
		
		
		
		
	
	}
	
	
	
	
	
	@Test
	public void ReRegCRUDTest() {
								
		
		ReRegBase reRegBase = ReRegBase.builder().build();
	
				
		reRegBaseRepository.save(reRegBase);		
		
		em.flush();				
		em.clear();	
		
		Optional<ReRegBase> optreRegBase2 = reRegBaseRepository.findById(reRegBase.getId());	
				
		assertThat(optreRegBase2.isEmpty() == false).isEqualTo(true);
		
		ReRegBase reRegBase2 = optreRegBase2.get();
		
		assertThat(reRegBase2.getId()).isEqualTo(reRegBase.getId());
	
				
		reRegBaseRepository.delete(reRegBase);		
		
		em.flush();		
				
		Optional<ReRegBase> optreRegBase3 = reRegBaseRepository.findById(reRegBase.getId());			 
		assertThat(optreRegBase3.isEmpty()).isEqualTo(true);
		
		
		
	}
	
	
	
	@Test
	public void ReRegGetDtListTest() {
								
		
		ReRegBase reRegBase = ReRegBase.builder().build();
	
				
		reRegBaseRepository.save(reRegBase);		
		
		em.flush();				
		em.clear();	
		
		Optional<ReRegBase> optreRegBase2 = reRegBaseRepository.findById(reRegBase.getId());	
				
		assertThat(optreRegBase2.isEmpty() == false).isEqualTo(true);		
		ReRegBase reRegBase2 = optreRegBase2.get();		
		assertThat(reRegBase2.getId()).isEqualTo(reRegBase.getId());	

		
		ReRegDt reRegDt = ReRegDt.builder().beforeCtnt("이전").afterCtnt("이후").reportBase(reRegBase).build();		
		reRegDtRepository.save(reRegDt);
		
		
		ReRegDt reRegDt2 = ReRegDt.builder().beforeCtnt("이전1").afterCtnt("이후1").reportBase(reRegBase).build();		
		reRegDtRepository.save(reRegDt2);
		

		em.flush();				
		em.clear();	
		
		
		Optional<ReRegBase> optreRegBase3 = reRegBaseRepository.findById(reRegBase.getId());	
		assertThat(optreRegBase3.isEmpty() == false).isEqualTo(true);	
		ReRegBase ReRegBase3 = optreRegBase3.get();		
		System.out.println("====================== lazy 여부 확인 선 ");
		
		
		assertThat(ReRegBase3.getReportDtList().get(0).getBeforeCtnt()).isEqualTo(reRegDt.getBeforeCtnt());
		assertThat(ReRegBase3.getReportDtList().get(1).getBeforeCtnt()).isEqualTo(reRegDt2.getBeforeCtnt());
			
		
	}
	
	
	
	
	
	
	

}
