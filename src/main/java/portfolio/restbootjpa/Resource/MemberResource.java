package portfolio.restbootjpa.Resource;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import portfolio.restbootjpa.Entity.Member;


public class MemberResource  extends  RepresentationModel<MemberResource> {

	
	private Member member;
	
	public MemberResource(Member member) {
	
		this.member = member;
	}	
	
	public Member getContent() {
		return member;
	}
	
	
	


	
	
	//JSON하고 같이 아닌 경우하고 같이 넣으면 되겠는데

}
