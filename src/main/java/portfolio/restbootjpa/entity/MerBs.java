package portfolio.restbootjpa.entity;

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
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MerBs extends BaseEntity{

	
	@GeneratedValue
	@Id
	private Long merNo;
		
	
	
	@Column(length =9)
	private String merNm;
		
	@Column(length =14)
	private String regDtm;
	@Column(length =14)
	private String cnlDtm;
			
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name ="client_bs")	
	private MerClientBs merClientBs;
	
	
	@ManyToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name = "rsvr_client_bs") 
	private MerClientBs rsvrClientBs;
	
	@OneToMany(mappedBy = "merBs")
	private List<MerReprRel> merRsvrRel = new ArrayList<MerReprRel>();
	
	@Column(length =6)
	private String bbrNo;
			
	
	
	@OneToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name = "contact_id_phone") 
	private Phone phone;

	@OneToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name = "contact_id_email") 
	private Email email;
	

	public static MerBsBuilder getMappingBuilder(MerBs merBs) {
		return MerBs.builder()
				.merNo(merBs.getMerNo()) 
				.merNm(merBs.getMerNm())
				.regDtm(merBs.getRegDtm())
				.cnlDtm(merBs.getCnlDtm())
				.merClientBs(merBs.getMerClientBs())
				.rsvrClientBs(merBs.getRsvrClientBs())
				.merRsvrRel(merBs.getMerRsvrRel())
				.phone(merBs.getPhone())
				.email(merBs.getEmail())				
				
				;		
	}





	
		
}
