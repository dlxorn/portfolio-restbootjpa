package portfolio.restbootjpa.Resource;

import org.springframework.hateoas.RepresentationModel;

import portfolio.restbootjpa.Entity.MerBs;


public class MemberResource  extends  RepresentationModel<MemberResource> {

	
	private MerBs member;
	
	public MemberResource(MerBs member) {
	
		this.member = member;
	}	
	
	public MerBs getContent() {
		return member;
	}
	
	
	


	
	
	//JSON하고 같이 아닌 경우하고 같이 넣으면 되겠는데

}
