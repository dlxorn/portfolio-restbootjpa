package portfolio.restbootjpa.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import portfolio.restbootjpa.accounts.Account;
import portfolio.restbootjpa.accounts.CurrentUser;
import portfolio.restbootjpa.dto.MerClientDto;
import portfolio.restbootjpa.entity.MerBs;
import portfolio.restbootjpa.entity.MerClientBs;
import portfolio.restbootjpa.resource.MerBsResource;
import portfolio.restbootjpa.resource.MerClientBsResource;
import portfolio.restbootjpa.service.MerClientService;
import portfolio.restbootjpa.validator.MerClientBsValidator;

@RestController
@RequestMapping(value="/api/merclient",produces = MediaTypes.HAL_JSON_VALUE)
public class MerClientController {
	
	
	@Autowired
	MerClientService merClientService;
			
	@Autowired
	MerClientBsValidator merClientBsValidator;
		
	
	@Autowired
	ModelMapper modelMapper;
	
	
	
	@GetMapping
	public ResponseEntity getList(Pageable pageable,  PagedResourcesAssembler<MerClientBs> assembler ,  @CurrentUser Account currentUser ) {		
				
		Page<MerClientBs> page = merClientService.findAll(pageable);
		
	    boolean isLogin =	currentUser != null ;	
		PagedModel<RepresentationModel<?>> pagedResources = assembler.toModel(page, e ->  new MerClientBsResource(mappingToDto(e), isLogin));
		 	 			
		return ResponseEntity.ok(pagedResources);		
		
	}
	
	
	
	
	
	@GetMapping("/{clientNo}")
	public ResponseEntity getClientInfo(@PathVariable Long clientNo, @CurrentUser Account currentUser) {
		
		
		Optional<MerClientBs> optMerClientBs = merClientService.findMerClientBsById(clientNo);
		if(optMerClientBs.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		MerClientBs merClientBs = optMerClientBs.get();
		
		MerClientDto merClientDto= mappingToDto (merClientBs);
				
        boolean isLogin = currentUser != null;
		MerClientBsResource resource = new MerClientBsResource(merClientDto,isLogin);			
		
		return ResponseEntity.ok().body(resource);
	}		
	
	@PutMapping
	public ResponseEntity updateClientInfo(@RequestBody  MerClientDto  merClientDto ,  @Valid Errors errors) {	
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);		}
		
		merClientBsValidator.validateCommonDomain(merClientDto, errors);
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);
		}
		
		Optional<MerClientBs> optMerClientBs = merClientService.updateMerClientBs(merClientDto);
		
		if(optMerClientBs.isEmpty()) {
			return ResponseEntity.notFound().build();			
		}
		
		MerClientBsResource resource = new MerClientBsResource(merClientDto,true);						
		return ResponseEntity.ok().body(resource);
	}
	
	
	@PostMapping
	public ResponseEntity createClientInfo(@RequestBody  MerClientDto  merClientDto ,@Valid Errors errors ) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);
		}
		merClientBsValidator.validateCommonDomain(merClientDto, errors);
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);
		}	
			
		MerClientBs merClientBs = merClientService.saveMerClientFromDto(merClientDto);
		
		
		WebMvcLinkBuilder selfLinkBuilder = linkTo(MerClientController.class).slash(merClientBs.getClientNo());
		URI createdUri  = selfLinkBuilder.toUri() ;		
		merClientDto.setClientNo(merClientBs.getClientNo());
		
		MerClientBsResource merClientBsResource = new MerClientBsResource(merClientDto, true);
		
		
		return ResponseEntity.created(createdUri).body(merClientBsResource);
	}
	
	
	@DeleteMapping("{clientNo}")
	public ResponseEntity deleteClientInfo(@PathVariable Long clientNo) {
		
		Optional<MerClientBs> optMerClientBs = merClientService.findMerClientBsById(clientNo);
		
		if(optMerClientBs.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		MerClientBs merClientBs  = optMerClientBs.get();
			
		merClientService.deleteMerBs(merClientBs);		
		
		MerClientBsResource merClientBsResource = new MerClientBsResource();
				
		return ResponseEntity.ok().body(merClientBsResource);		
	}
	
	
   public MerClientDto mappingToDto(MerClientBs  merClientBs) {

	   MerClientDto merBsDto = modelMapper.map(merClientBs, MerClientDto.class);	   
	   
	   merBsDto.setType(merClientBs.getType().name());
	   
	   if(merClientBs.getPhone() != null) {
		  merBsDto.setPhoneNumber(merClientBs.getPhone().getContactCtnt());	   
	   }
	   
	   if(merClientBs.getEmail() != null) {
		  merBsDto.setEmail(merClientBs.getEmail().getContactCtnt()); 		  
	   }
	    	   
	   
       return merBsDto;
   	}
   	

}
