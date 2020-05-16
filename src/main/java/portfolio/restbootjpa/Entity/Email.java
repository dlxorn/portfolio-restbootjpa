package portfolio.restbootjpa.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("e_mail") 
@Getter
@NoArgsConstructor
public class Email  extends ContactItem{
	
	
	private String domainName;

		

	@Builder
	private Email(Long id, String contactCtnt,String domainName) {
		super(id, contactCtnt);
		this.domainName = domainName;
	
	}
	

	
	
	
}
