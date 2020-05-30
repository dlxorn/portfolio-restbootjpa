package portfolio.restbootjpa.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import portfolio.restbootjpa.controller.MerBsRestController;
import portfolio.restbootjpa.dto.MerBsDto;
@Relation(collectionRelation = "merbsinfolist")                                   
public class MerBsResource  extends  RepresentationModel<MerBsResource>{

	@JsonUnwrapped
	private MerBsDto merBsDto;
	
	
	public MerBsResource( MerBsDto merBsDto, boolean isLogin) {
	    this();
		this.merBsDto = merBsDto;		
		this.add(linkTo(MerBsRestController.class).slash(merBsDto.getMerNo()).withRel("read-merbs").withType(RequestMethod.GET.name()));			
				
		if(isLogin) {
		this.add(linkTo(MerBsRestController.class).withRel("create-merbs").withType(RequestMethod.POST.name()));		
		this.add(linkTo(MerBsRestController.class).withRel("update-merbs").withType(RequestMethod.PUT.name()));
		this.add(linkTo(MerBsRestController.class).slash(merBsDto.getMerNo()).withRel("delete-merbs").withType(RequestMethod.DELETE.name()));
		}
		
	}
	
	
	public MerBsResource() {	
		
		this.add(linkTo(MerBsRestController.class).withRel("merbs-list").withType(RequestMethod.GET.name()));
		
		
	}
		
			

}
