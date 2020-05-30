package portfolio.restbootjpa.index;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import portfolio.restbootjpa.controller.MerBsRestController;

@RestController
@RequestMapping(value = "/", produces = MediaTypes.HAL_JSON_VALUE)
class IndexController {
	
	@GetMapping   
	public RepresentationModel getMerBsInfoList() {	    
		
		RepresentationModel index = new RepresentationModel();
		index.add(linkTo(MerBsRestController.class).withRel("merbs"));
				
		
		return index;
	}	
	
	

	
	
}
