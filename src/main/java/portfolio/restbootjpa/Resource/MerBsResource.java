package portfolio.restbootjpa.Resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import portfolio.restbootjpa.controller.MerBsController;
import portfolio.restbootjpa.dto.MerBsDto;
@Relation(collectionRelation = "merbsinfolist")                                   
public class MerBsResource  extends  RepresentationModel<MerBsResource>{

	@JsonUnwrapped
	private MerBsDto merBsDto;
	
	
	public MerBsResource( MerBsDto merBsDto) {
		super();
		this.merBsDto = merBsDto;
		
		WebMvcLinkBuilder selfLinkBuilder = linkTo(MerBsController.class).slash(merBsDto.getMerNo());	
		this.add(selfLinkBuilder.withSelfRel());					
		this.add(linkTo(MerBsController.class).withRel("create-merbs").withType("POST"));
		this.add(linkTo(MerBsController.class).slash(merBsDto.getMerNo()).withRel("read-merbs").withType("GET"));	
		this.add(linkTo(MerBsController.class).withRel("update-merbs").withType("PUT"));
		this.add(linkTo(MerBsController.class).slash(merBsDto.getMerNo()).withRel("delete-merbs").withType("DELETE"));
		this.add(linkTo(MerBsController.class).withRel("merbs-list").withType("GET"));
		
	}
			

}
