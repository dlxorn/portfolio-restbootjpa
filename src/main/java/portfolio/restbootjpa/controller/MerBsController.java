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

import portfolio.restbootjpa.Resource.MerBsResource;
import portfolio.restbootjpa.accounts.Account;
import portfolio.restbootjpa.accounts.CurrentUser;
import portfolio.restbootjpa.dto.MerBsDto;
import portfolio.restbootjpa.entity.MerBs;
import portfolio.restbootjpa.service.MerService;
import portfolio.restbootjpa.validator.MerBsValidator;

@RestController
@RequestMapping(value = "/api/merbs", produces = MediaTypes.HAL_JSON_VALUE)
public class MerBsController {

	@Autowired
	MerService merService;	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	MerBsValidator merBsValidator;
	
	
	
	@GetMapping   
	public ResponseEntity getMerBsInfoList(Pageable pageable,   PagedResourcesAssembler<MerBs> assembler ,  @CurrentUser Account currentUser  ) {	    
		
		Page<MerBs> page = merService.findAll(pageable);
				
	    boolean isLogin =	currentUser != null ;	
		PagedModel<RepresentationModel<?>> pagedResources = assembler.toModel(page, e ->  new MerBsResource(mappingToDto(e), isLogin));
		 	 			
		return ResponseEntity.ok(pagedResources);
	}	
	
		
	@GetMapping("/{merNo}")   
	public ResponseEntity getMerBsInfo(@PathVariable Long merNo ,  @CurrentUser Account currentUser  ) throws Exception {		
		
		Optional<MerBs> optMerBs = merService.findMerBsById(merNo);
		if(optMerBs.isEmpty()) {			
			return ResponseEntity.notFound().build();			
		}
		MerBs merBs = 	optMerBs.get();	
				
		MerBsDto merBsDto = mappingToDto(merBs);		
		
	    boolean islogin =	currentUser != null ;	
		MerBsResource merBsResource = new MerBsResource(merBsDto,islogin);	
				
				
		return ResponseEntity.ok(merBsResource);

	}
			
	
	
	@PostMapping
	public ResponseEntity createMerInfo(@RequestBody @Valid MerBsDto merBsDto, Errors errors, @CurrentUser Account currentUser  ) {		
			
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);
		}				
		merBsValidator.validateCommonDomain(merBsDto,errors );		
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);
		}	
		
								
		MerBs merBs = merService.saveFromMerBsDto(merBsDto);	
			
		
		MerBsResource merBsResource = new MerBsResource(merBsDto, true);
		WebMvcLinkBuilder selfLinkBuilder = linkTo(MerBsController.class).slash(merBs.getMerNo());
		URI createdUri  = selfLinkBuilder.toUri() ;			
													
		return  ResponseEntity.created(createdUri).body(merBsResource);		
	}
	
	
	@PutMapping
	public ResponseEntity updateMerInfo(@RequestBody @Valid MerBsDto merBsDto, Errors errors,  @CurrentUser Account currentUser  ) {
		
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);
		}
		
		merBsValidator.validateUpdateDomain(merBsDto,errors );		
		if (errors.hasErrors()) {
			return ResponseEntity.badRequest().body(errors);
		}	
			
		MerBs merBs =merService.updateMerBsDto(merBsDto);
		
		if(merBs == null) {
			return ResponseEntity.notFound().build();
		}	
		MerBsResource merBsResource = new MerBsResource(merBsDto,true);
				
		return ResponseEntity.ok().body(merBsResource);				
	}
			

	@DeleteMapping("/{merNo}")
	public ResponseEntity delMerInfo(@PathVariable Long merNo,  @CurrentUser Account currentUser  ) {
		
		Optional<MerBs> optMerBs  =merService.findMerBsById(merNo);
		
		if(optMerBs.isEmpty()) {
			return ResponseEntity.notFound().build();
		}		
		
		merService.deleteMerBs(optMerBs.get());	
		
		MerBsResource merBsResource = new MerBsResource();		
		
		return ResponseEntity.ok().body(merBsResource);				
	}
				
	public MerBsDto mappingToDto(MerBs merBs) {
			
		MerBsDto merBsDto = modelMapper.map(merBs, MerBsDto.class);
		
		if(merBs.getEmail() != null) {
		merBsDto.setEmail(merBs.getEmail().getContactCtnt());
		}
		
		if(merBs.getPhone() != null) {
		merBsDto.setPhoneNumber(merBs.getPhone().getContactCtnt());
		}
		
		
		return merBsDto;
		
	}
	
	

}
