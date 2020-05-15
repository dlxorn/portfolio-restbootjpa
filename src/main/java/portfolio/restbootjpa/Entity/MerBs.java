package portfolio.restbootjpa.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MerBs extends BaseEntity{

	
	@GeneratedValue
	@Id
	private Long merNo;
		
	private String merNm;
		
	@Column(length =14)
	private String regDtm;
	@Column(length =14)
	private String cnlDtm;
			
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name ="client_bs")	
	private MerClientBs jngcNo;
	
	
	@ManyToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name = "rsvr_client_bs") 
	private MerClientBs rsvrClientBs;
	
	@OneToMany(mappedBy = "merBs")
	private List<MerReprRel> merRsvrRel = new ArrayList<MerReprRel>();
	
	
//oneTomany로 만들어야할듯	
//	@OneToOne(fetch=FetchType.LAZY)	
//	@JoinColumn(name = "contact_id") 
//	private ContactItem contactItem;
	
		
		
	
}
