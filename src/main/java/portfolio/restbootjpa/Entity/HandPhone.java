package portfolio.restbootjpa.Entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("hand_phone") 
public class HandPhone  extends Phone {

	
}
