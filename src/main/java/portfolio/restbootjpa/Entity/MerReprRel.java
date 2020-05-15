package portfolio.restbootjpa.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MerReprRel  extends BaseEntity{
	
	@Id
	private Long id;


	@ManyToOne
	@JoinColumn(name="mer_no")
	private MerBs merBs;
		
	
	@ManyToOne
	@JoinColumn(name="rsvr_jngc_no")
	private MerClientBs merJngcBs;
			
	@Column(length =14)
	private String regDtm;
	@Column(length =14)
	private String cnlDtm;
	
	

	
	
}
