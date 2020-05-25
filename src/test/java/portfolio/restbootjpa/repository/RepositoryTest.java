package portfolio.restbootjpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import portfolio.restbootjpa.entity.ContactItem;
import portfolio.restbootjpa.entity.Email;
import portfolio.restbootjpa.entity.MerBs;
import portfolio.restbootjpa.entity.MerClientBs;
import portfolio.restbootjpa.entity.MerReprRel;
import portfolio.restbootjpa.entity.Phone;
import portfolio.restbootjpa.entity.InfoChaBase;
import portfolio.restbootjpa.entity.InfoChaDt;


@SpringBootTest
@Transactional
public class RepositoryTest {	
	
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
								
		
		InfoChaBase reRegBase = InfoChaBase.builder().build();
	
				
		reRegBaseRepository.save(reRegBase);		
		
		em.flush();				
		em.clear();	
		
		Optional<InfoChaBase> optreRegBase2 = reRegBaseRepository.findById(reRegBase.getId());	
				
		assertThat(optreRegBase2.isEmpty() == false).isEqualTo(true);
		
		InfoChaBase reRegBase2 = optreRegBase2.get();
		
		assertThat(reRegBase2.getId()).isEqualTo(reRegBase.getId());
	
				
		reRegBaseRepository.delete(reRegBase);		
		
		em.flush();		
				
		Optional<InfoChaBase> optreRegBase3 = reRegBaseRepository.findById(reRegBase.getId());			 
		assertThat(optreRegBase3.isEmpty()).isEqualTo(true);
		
		
		
	}
	
	
	
	@Test
	public void ReRegGetDtListTest() {
								
		
		InfoChaBase reRegBase = InfoChaBase.builder().build();
	
				
		reRegBaseRepository.save(reRegBase);		
		
		em.flush();				
		em.clear();	
		
		Optional<InfoChaBase> optreRegBase2 = reRegBaseRepository.findById(reRegBase.getId());	
				
		assertThat(optreRegBase2.isEmpty() == false).isEqualTo(true);		
		InfoChaBase reRegBase2 = optreRegBase2.get();		
		assertThat(reRegBase2.getId()).isEqualTo(reRegBase.getId());	

		
		InfoChaDt reRegDt = InfoChaDt.builder().beforeCtnt("이전").afterCtnt("이후").reportBase(reRegBase).build();		
		reRegDtRepository.save(reRegDt);
		
		
		InfoChaDt reRegDt2 = InfoChaDt.builder().beforeCtnt("이전1").afterCtnt("이후1").reportBase(reRegBase).build();		
		reRegDtRepository.save(reRegDt2);
		

		em.flush();				
		em.clear();	
		
		
		Optional<InfoChaBase> optreRegBase3 = reRegBaseRepository.findById(reRegBase.getId());	
		assertThat(optreRegBase3.isEmpty() == false).isEqualTo(true);	
		InfoChaBase ReRegBase3 = optreRegBase3.get();		
		System.out.println("====================== lazy 여부 확인 선 ");
		
		
		assertThat(ReRegBase3.getReportDtList().get(0).getBeforeCtnt()).isEqualTo(reRegDt.getBeforeCtnt());
		assertThat(ReRegBase3.getReportDtList().get(1).getBeforeCtnt()).isEqualTo(reRegDt2.getBeforeCtnt());
			
		
	}
		
	
	@Test 
	public void MerReprRelTest() {
				
		
		
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
		
		MerReprRel merReprRel = MerReprRel.builder().merBs(merBs).merClientBs(merClientBs).build();
				
		merClientRelRepository.save(merReprRel);
				
		em.flush();				
		em.clear();	
		
		
		MerBs merBs3 =  merBsRepository.findById(merBs.getMerNo()).get();	
			
		assertThat(merClientBs.getClientNm()).isEqualTo(merBs3.getMerRsvrRel().get(0).getMerClientBs().getClientNm());		
		
				
		
	}
		
	
	
	@Test 
	public void merBsQueryCreationTest() {
		
		//MERBS
		MerBs merBs = MerBs.builder().merNm("요요!!!!")								
				                     .build();		
				
		merBsRepository.save(merBs);			
		
		em.flush();				
		em.clear();	
		
		
	//	MerBs merbs2 = merBsRepository.findByMerNm("요요!!!!");		
	//	assertThat(merBs.getMerNm()).isEqualTo(merbs2.getMerNm());		
		
	List<MerBs> merBsList =merBsRepository.findByMerNm("요요!!!!");						
	assertThat(merBsList.get(0).getMerNm()).isEqualTo(merBs.getMerNm());		
			
	em.clear();	
	
	List<MerBs> merBsList1 =merBsRepository.findYoyoByMerNmAndMerNmStartingWith("요요!!!!", "요요!");					
	assertThat(merBsList1.get(0).getMerNm()).isEqualTo(merBs.getMerNm());		
		
		
		
	}
	
	
	
	@Test 
	public void merBsJsqlTest() {
		
		MerClientBs merClientBs = MerClientBs.builder().clientNm("룩룩!!").build();
		
		merClientBsRepository.save(merClientBs);
		
		//MERBS
		MerBs merBs = MerBs.builder().merNm("하하!!!!")								
				                     .build();		
		merBs.setMerClientBs(merClientBs);
				
		merBsRepository.save(merBs);			
		
		em.flush();				
		em.clear();	
		
		
		long count = merBsRepository.countByMerNm("하하!!!!") ;
				
		assertThat(count).isEqualTo(1);
			
		String clientNm =  merBsRepository.findClientNmByMerNm("하하!!!!");
						
		assertThat(clientNm).isEqualTo(merClientBs.getClientNm());		
		
		
		int result = merBsRepository.bulkChangeMerNm("요요!!!!!");
		
		assertThat(result).isEqualTo(1);		
								
		
		Optional<MerBs> optMerBs = merBsRepository.findById(merBs.getMerNo());
		
		MerBs merBs2 = optMerBs.get();
				
		assertThat(merBs2.getMerNm()).isEqualTo("요요!!!!!");	
	
				
		merBsRepository.findMerBsFetchJoin();
				
		
	}
	
	
	
	@Test 
	public void merBsPageTest() {
	
	
		makeMerBsData();
				
		
		Pageable pageable = PageRequest.of(1, 2, Sort.by(Sort.Direction.DESC, "merNo"));
		Page<MerBs> page = merBsRepository.findAll(pageable);
		
		
		assertThat(page.getNumber()).isEqualTo(1);
		assertThat(page.getSize()).isEqualTo(2);
		assertThat(page.getTotalPages()).isEqualTo(5);
		assertThat(page.hasNext()).isTrue();
		
			
		Page<MerBs> page2 = merBsRepository.findMerBsFetchJoinPage(pageable);
		
		assertThat(page2.getNumber()).isEqualTo(1);
		assertThat(page2.getSize()).isEqualTo(2);
		assertThat(page2.getTotalPages()).isEqualTo(5);
		assertThat(page2.hasNext()).isTrue();
					
		
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
	
		
	
	@Test
	public void MerClientListByMerNoTest() {
		
    MerBs merBs = 	MerBs.builder().merNm("하하네 가맹점").build();    
	merBsRepository.save(merBs);	   
	em.flush();	
	em.clear();			
		
	for(int i=0 ; i<10; i++) {			
			
			MerClientBs merClientBs = MerClientBs.builder().clientNm("하하"+ i)								
                    .build();		
			merClientBsRepository.save(merClientBs);	
			MerReprRel merReprRel = MerReprRel.builder().merBs(merBs).merClientBs(merClientBs).build();			
			merClientRelRepository.save(merReprRel);						
    }
	
	em.flush();	
	em.clear();	
	
	List<MerClientBs> merClientBsList = merClientBsRepository.findMerClientBsByMerNo(merBs.getMerNo());
	
		
	for(int i = 0 ; i < merClientBsList.size(); i++) {
		MerClientBs merClientBs = merClientBsList.get(i);
        assertThat(merClientBs.getClientNm()).isEqualTo("하하"+ i) ;    
	}	
				
	}
	
	
}
