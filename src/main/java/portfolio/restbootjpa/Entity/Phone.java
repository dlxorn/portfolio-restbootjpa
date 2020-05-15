package portfolio.restbootjpa.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("phone") 
public class Phone  extends ContactItem{
	
	
	
}
