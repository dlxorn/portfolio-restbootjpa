package portfolio.restbootjpa.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MerClientDto {	
	
	private Long clientNo;
	@NotEmpty
	private String Type;	
	@NotEmpty
	@Length(max= 100)
	private String clientNm;
	private String PhoneNumber;
	@Email
	private String email;	
	

}
