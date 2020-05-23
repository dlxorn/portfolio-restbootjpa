package portfolio.restbootjpa.Resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import portfolio.restbootjpa.controller.MerBsController;
import portfolio.restbootjpa.dto.MerBsDto;
@Relation(collectionRelation = "merbsinfolist")                                   
public class MerBsResource  extends  RepresentationModel<MerBsResource>{

	@JsonUnwrapped
	private MerBsDto merBsDto;
	
	
	public MerBsResource( MerBsDto merBsDto, boolean isLogin) {
		this.merBsDto = merBsDto;
				
		//TODO 잘못 넣은 것 여기서 삭제하고 바깥에서 넣을 것
		WebMvcLinkBuilder selfLinkBuilder = linkTo(MerBsController.class).slash(merBsDto.getMerNo());	
		this.add(selfLinkBuilder.withSelfRel());
		//TODO 끝
		
		this.add(linkTo(MerBsController.class).withRel("merbs-list").withType(RequestMethod.GET.name()));
		this.add(linkTo(MerBsController.class).slash(merBsDto.getMerNo()).withRel("read-merbs").withType(RequestMethod.GET.name()));	
		
				
		if(isLogin) {
		this.add(linkTo(MerBsController.class).withRel("create-merbs").withType(RequestMethod.POST.name()));		
		this.add(linkTo(MerBsController.class).withRel("update-merbs").withType(RequestMethod.PUT.name()));
		this.add(linkTo(MerBsController.class).slash(merBsDto.getMerNo()).withRel("delete-merbs").withType(RequestMethod.DELETE.name()));
		}
		
	}
			

}
