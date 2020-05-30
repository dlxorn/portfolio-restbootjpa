package portfolio.restbootjpa.resource;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import portfolio.restbootjpa.controller.MerClientRestController;
import portfolio.restbootjpa.dto.MerClientDto;

@Relation(collectionRelation = "clientinfolist")          
public class MerClientBsResource extends RepresentationModel<MerClientBsResource> {
	
	
	@JsonUnwrapped
	private MerClientDto merClientDto;
	
	
	public MerClientBsResource (MerClientDto merClientDto, boolean isLogin) {	
		this();
		this.merClientDto = merClientDto;	
		this.add(linkTo(MerClientRestController.class).withRel("merclient-list").withType(RequestMethod.GET.name()));
		this.add(linkTo(MerClientRestController.class).slash(merClientDto.getClientNo()).withRel("read-merclient").withType(RequestMethod.GET.name()));		
		
		if(isLogin) {
		this.add(linkTo(MerClientRestController.class).withRel("create-merclient").withType(RequestMethod.POST.name()));
		this.add(linkTo(MerClientRestController.class).withRel("update-merclient").withType(RequestMethod.PUT.name()));
		this.add(linkTo(MerClientRestController.class).slash(merClientDto.getClientNo()).withRel("delete-merclient").withType(RequestMethod.DELETE.name()));
		}	
		
	}
	
	
	public MerClientBsResource () {	
		
		this.add(linkTo(MerClientRestController.class).withRel("merclient-list").withType(RequestMethod.GET.name()));		
		
	}
	
}
