package portfolio.restbootjpa.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("phone") 
@Getter
@Setter
@NoArgsConstructor
public class Phone  extends ContactItem{
	
	
	private String telCompany;
	
		

	@Builder
	private Phone(Long id, String contactCtnt,String telCompany) {
		super(id, contactCtnt);
		this.telCompany = telCompany;
	
	}
	

	
	
	
}
