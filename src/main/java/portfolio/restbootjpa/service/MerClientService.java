package portfolio.restbootjpa.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import portfolio.restbootjpa.constraint.MerClientType;
import portfolio.restbootjpa.dto.MerClientDto;
import portfolio.restbootjpa.entity.Email;
import portfolio.restbootjpa.entity.MerClientBs;
import portfolio.restbootjpa.entity.Phone;
import portfolio.restbootjpa.repository.ContactRepository;
import portfolio.restbootjpa.repository.MerBsRepository;
import portfolio.restbootjpa.repository.MerClientBsRepository;
import portfolio.restbootjpa.repository.MerClientRelRepository;
import portfolio.restbootjpa.repository.PhoneRepository;
import portfolio.restbootjpa.repository.ReRegBaseRepository;
import portfolio.restbootjpa.repository.ReRegDtRepository;

@Service
@Transactional
public class MerClientService {
	
	@Autowired 
	MerBsRepository merBsRepository;	
	@Autowired
	PhoneRepository phoneRepository;
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
	ModelMapper modelMapper;

	
   public MerClientBs saveMerClientBs(MerClientBs merClientBs) {
	   
	   merClientBsRepository.save(merClientBs);
	   
	   return merClientBs;
   }
   
   public MerClientBs saveMerClientFromDto(MerClientDto merClientDto) {
	
	   MerClientBs merClientBs = modelMapper.map(merClientDto, MerClientBs.class);
	   merClientBs.setType(MerClientType.valueOf(merClientDto.getType()));
	   
	  Phone phone = Phone.builder().contactCtnt(merClientDto.getPhoneNumber()).build();
	  contactRepository.save(phone);
	  merClientBs.setPhone(phone);
	 
	  Email email = Email.builder().contactCtnt(merClientDto.getEmail()).build();
	  contactRepository.save(email);
	  merClientBs.setEmail(email); 
	         
	   
	   merClientBsRepository.save(merClientBs);
	   
	   return merClientBs;
   }
   
   public Page<MerClientBs> findAll(Pageable pageable){	   	
	   return merClientBsRepository.findAll(pageable);      
   }   
   
   
	
   public Optional<MerClientBs> findMerClientBsById(Long id) {
	   	      	   	   
	   return  merClientBsRepository.findById(id);
   }
   
   public  Optional<MerClientBs> updateMerClientBs(MerClientDto dto) {
	   
	   Optional<MerClientBs> optMerClientBs = merClientBsRepository.findById(dto.getClientNo());
	      if(optMerClientBs.isEmpty()) {
	    	 return optMerClientBs;
	      }	      
	      
	   MerClientBs clientBs = optMerClientBs.get();   	   
	   
	   clientBs.setClientNm(dto.getClientNm());
	   clientBs.setType(MerClientType.valueOf(dto.getType()));
	   
	   
	 
	if(clientBs.getPhone() != null) {
		   clientBs.getPhone().setContactCtnt(dto.getPhoneNumber());	   
	   }else {			   
		   Phone phone = Phone.builder().contactCtnt(dto.getPhoneNumber()).build();   		   
		   contactRepository.save(phone);		   	   
		   clientBs.setPhone(phone);
		   
	   }	   
	   
	   if(clientBs.getEmail() != null) {
		   clientBs.getEmail().setContactCtnt(dto.getEmail());	   
	   }else {			   
		   Email email = Email.builder().contactCtnt(dto.getEmail()).build();     			   
		   contactRepository.save(email);	   	   
		   clientBs.setEmail(email);
	   }   	   
 	   	   
	   return  optMerClientBs;
   }
	
   public void deleteMerBs(MerClientBs merClientBs) {   
		
	  merClientBsRepository.delete(merClientBs);
			   
   }
     
   
   public List<MerClientDto> getMerClientListByMerNo(Long merNo) {   
		
	   
	   return null;
			   
   }
   
   
	
}
