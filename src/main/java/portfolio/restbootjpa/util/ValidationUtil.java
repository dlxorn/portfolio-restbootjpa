package portfolio.restbootjpa.util;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtil {
	
	public boolean isNumeric(String s) {
		  try {
		      Double.parseDouble(s);
		      return true;
		  } catch(NumberFormatException e) {
		      return false;
		  }
 }
	

}
