package portfolio.restbootjpa.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import portfolio.restbootjpa.dto.MerBsDto;

@Component
public class MerBsValidator {

	public void commonValidate(MerBsDto dto, Errors errors) {
		
		if(isNumeric(dto.getRegDtm()) == false) {			
			errors.rejectValue("regDtm", "wrongValue", "regDtm is wrong");		
		}
			
		if(isNumeric(dto.getBbrNo()) == false) {			
			errors.rejectValue("bbrNo", "wrongValue", "bbrNo is wrong");		
		}
		
		
	
		if(dto.getCnlDtm() != null ) {
			
			if(isNumeric(dto.getCnlDtm()) == false) {			
				errors.rejectValue("cnlDtm", "wrongValue", "cnlDtm is wrong");		
			}	
						
			int regDtm = Integer.parseInt(dto.getRegDtm() );
			int cnlDtm = Integer.parseInt(dto.getCnlDtm() );
			
			if(regDtm >  cnlDtm) {
				errors.rejectValue("cnlDtm", "wrongValue", "cnlDtm is wrong");				
			}			
		}
				
	}
	
	
	
	public void updateValidate(MerBsDto dto, Errors errors) {
		commonValidate(dto, errors) ;		
		if(dto.getMerNo() == null ) {		
			errors.rejectValue("merNo", "isnull", "merNo is null");	
		}				
	}
	
	
	public boolean isNumeric(String s) {
		  try {
		      Double.parseDouble(s);
		      return true;
		  } catch(NumberFormatException e) {
		      return false;
		  }
   }
	
	
}
