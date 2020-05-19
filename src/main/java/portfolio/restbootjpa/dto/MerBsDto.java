package portfolio.restbootjpa.dto;

import javax.validation.constraints.Email;

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
		
	
//	@Length(max=9)
	private Long merNo;	
//	@Length(max=100)
	private String merNm;
//	@Length(max=14)
	private String regDtm;
//	@Length(max=14)
	private String cnlDtm;
//	@Length(max=6)
	private String bbrNo;	
	private String phoneNumber;
//	@Email
	private String email;	
		
	

}
