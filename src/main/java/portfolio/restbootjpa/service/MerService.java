package portfolio.restbootjpa.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import portfolio.restbootjpa.Entity.Email;
import portfolio.restbootjpa.Entity.MerBs;
import portfolio.restbootjpa.Entity.Phone;
import portfolio.restbootjpa.dto.MerBsDto;
import portfolio.restbootjpa.repository.ContactRepository;
import portfolio.restbootjpa.repository.MerBsRepository;
import portfolio.restbootjpa.repository.MerClientBsRepository;
import portfolio.restbootjpa.repository.MerClientRelRepository;
import portfolio.restbootjpa.repository.PhoneRepository;
import portfolio.restbootjpa.repository.ReRegBaseRepository;
import portfolio.restbootjpa.repository.ReRegDtRepository;

@Service
@Transactional
public class MerService {
		
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



public Page<MerBs> findAll(Pageable pageable) {	
	
	return merBsRepository.findAll(pageable);
}






public MerBs saveMerBs(MerBs merBs) {	
	 merBsRepository.save(merBs);
	
	 return merBs;
}

public MerBs saveFromMerBsDto(MerBsDto merBsDto) {
	
	Phone phone =Phone.builder().contactCtnt(merBsDto.getPhoneNumber()).build();
	contactRepository.save(phone);
	
	Email email = Email.builder().contactCtnt(merBsDto.getEmail()).build();
	contactRepository.save(email);
	
	
	MerBs merBs = modelMapper.map(merBsDto, MerBs.class);
	merBs.setEmail(email);
	merBs.setPhone(phone);
		
    merBsRepository.save(merBs);
    	
	
	return merBs;
}



public Optional<MerBs> findMerBsById(Long merNo ) {	
	
   return 	merBsRepository.findById(merNo);
	   
}


public MerBs updateMerBsDto(MerBsDto merBsDto) {	
	
      Optional<MerBs> optMerBs = merBsRepository.findById(merBsDto.getMerNo());
      
      if(optMerBs.isEmpty()) {
    	  return null;
      }
      
      MerBs merBs = optMerBs.get();
      
     merBs.setMerNm(merBsDto.getMerNm());
     merBs.setBbrNo(merBsDto.getBbrNo());
     merBs.setRegDtm(merBsDto.getRegDtm());
   
     
     merBs.getPhone().setContactCtnt(merBsDto.getPhoneNumber());
     merBs.getEmail().setContactCtnt(merBsDto.getEmail());
     
     
              
	
	 return merBs;
		   
}


public void deleteMerBs(MerBs merBs ) {	
	
    merBsRepository.delete(merBs);
		   
}








	
	
	
	

}
