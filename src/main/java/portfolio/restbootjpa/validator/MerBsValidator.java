package portfolio.restbootjpa.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import portfolio.restbootjpa.dto.MerBsDto;
import portfolio.restbootjpa.util.ValidationUtil;

@Component
public class MerBsValidator {
	
	@Autowired
	ValidationUtil validationUtil;
	
	
	
	

	public void validateCommonDomain(MerBsDto dto, Errors errors) {
		
		if(validationUtil.isNumeric(dto.getRegDtm()) == false) {			
			errors.rejectValue("regDtm", "wrongValue", "regDtm is wrong");		
		}
			
		if(validationUtil.isNumeric(dto.getBbrNo()) == false) {			
			errors.rejectValue("bbrNo", "wrongValue", "bbrNo is wrong");		
		}		
		
	
		if(dto.getCnlDtm() != null ) {
			
			if(validationUtil.isNumeric(dto.getCnlDtm()) == false) {			
				errors.rejectValue("cnlDtm", "wrongValue", "cnlDtm is wrong");		
			}	
						
			int regDtm = Integer.parseInt(dto.getRegDtm() );
			int cnlDtm = Integer.parseInt(dto.getCnlDtm() );
			
			if(regDtm >  cnlDtm) {
				errors.rejectValue("cnlDtm", "wrongValue", "cnlDtm is wrong");				
			}			
		}
				
	}
	
	
	
	public void validateUpdateDomain(MerBsDto dto, Errors errors) {
		validateCommonDomain(dto, errors) ;		
		if(dto.getMerNo() == null ) {		
			errors.rejectValue("merNo", "isnull", "merNo is null");	
		}				
	}
	

	
	
}
