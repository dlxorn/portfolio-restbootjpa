package portfolio.restbootjpa.accounts;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class JoinUser {

	@Id
	@GeneratedValue
	private Long id;
	
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column
	private String picture;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
	
	@Builder
	public JoinUser(String name, String email, String picture, Role role) {
		
		this.name =name;
		this.email = email;
		this.picture = picture;
		this.role = role;			
		
	}
	
	
	public JoinUser update(String name,String picture) {
		this.name =  name;
		this.picture = picture;
		
		return this;
	}	
	
	public String getRoleKey() {
		return this.role.getKey();
	}
		
	
	
	
	
	
	

}