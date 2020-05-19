package portfolio.restbootjpa.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder 
@NoArgsConstructor
@AllArgsConstructor
public class MerBsDto {
	
    private Long merNo;	  
        
    @NotEmpty
	@Length(max=100)
	private String merNm;
    @NotEmpty  
	@Length(min=14 , max=14)
	private String regDtm;	
	@Length(min=14 , max=14)
	private String cnlDtm;
	@Length(min=6 ,max=6)
	private String bbrNo;	
	private String phoneNumber;
	@Email
	private String email;			
	

}
