package portfolio.restbootjpa.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import portfolio.restbootjpa.constraint.MerClientType;
import portfolio.restbootjpa.dto.MerClientDto;
import portfolio.restbootjpa.util.ValidationUtil;

@Component
public class MerClientBsValidator {
	
	@Autowired
	ValidationUtil validationUtil;
	
	
	public void validateCommonDomain(MerClientDto dto  , Errors errors)  {
				
		if(dto.getType()  == null) {
			errors.rejectValue("merClientType", "merClientType is null");				
		}
		
		
		if(isCorrectMerClientType(dto.getType())  == false) {
			errors.rejectValue("merClientType", "wrong merClientType");				
		}
		
		
	}
	
	
	
    public void  validateUpdateDomian(MerClientDto dto  , Errors errors)  {
    	validateCommonDomain(dto, errors);	
    	
    	if(dto.getClientNo() == null) {
    		errors.rejectValue("merClientNo", "merClientNo is null");		
    		    		
    	}    	
	}
        
        
    public boolean isCorrectMerClientType(String type) {
    	
    	boolean isExist = false;
    	
    	for( MerClientType merClientType  :   MerClientType.values() ) {
    	   if(type.equals(merClientType.name())) {    		   
    		isExist = true;
    		break;    	}	
    	 }    	
    	 	
    	return isExist;	   	
    }
	
	
	
	
	

}
