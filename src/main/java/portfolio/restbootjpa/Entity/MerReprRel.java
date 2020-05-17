package portfolio.restbootjpa.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MerReprRel  extends BaseEntity{
	
	@Id
	@GeneratedValue
	private Long id;


	@ManyToOne
	@JoinColumn(name="mer_no")
	private MerBs merBs;
		
	
	@ManyToOne
	@JoinColumn(name="rsvr_no")
	private MerClientBs merClientBs;
			
	@Column(length =14)
	private String regDtm;
	@Column(length =14)
	private String cnlDtm;
	
	

	
	
}
