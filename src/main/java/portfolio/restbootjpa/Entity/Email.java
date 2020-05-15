package portfolio.restbootjpa.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("e_mail") 
public class Email  extends ContactItem{

	
}
