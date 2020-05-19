package portfolio.restbootjpa.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	
	

	
	
}
