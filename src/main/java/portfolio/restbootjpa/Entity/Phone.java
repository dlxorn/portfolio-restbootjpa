package portfolio.restbootjpa.Entity;

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
@NoArgsConstructor
public class Phone  extends ContactItem{
	
	
	private String telCompany;
	
		

	@Builder
	private Phone(Long id, String contactCtnt,String telCompany) {
		super(id, contactCtnt);
		this.telCompany = telCompany;
	
	}
	

	
	
	
}
