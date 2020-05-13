package portfolio.restbootjpa.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity

@NoArgsConstructor
@Getter
public class Member {

	
	
	@GeneratedValue
	@Id
	private Long id;
	
	private String Name;

	public Member(String name) {
		super();
		Name = name;
	}
	
	
	
	
	

	
	
	
	
}
