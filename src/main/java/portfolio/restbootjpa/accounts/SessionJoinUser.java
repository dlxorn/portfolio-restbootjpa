package portfolio.restbootjpa.accounts;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class SessionJoinUser implements Serializable{
	
	private String name;
	private String email;
	private String picture;
	

	public SessionJoinUser(JoinUser joinUser) {
		this.name = joinUser.getName();
		this.email = joinUser.getEmail();
		this.picture = joinUser.getPicture();		
	}
	
}
