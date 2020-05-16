package portfolio.restbootjpa.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import portfolio.restbootjpa.constraint.MerClientType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class MerClientBs extends BaseEntity {

		
	@GeneratedValue
	@Id
	private Long jngcNo;	
	
	@Enumerated(EnumType.STRING)
	private MerClientType type;
	
	private String clientNm;
		
	@OneToMany(mappedBy = "merJngcBs")
	private List<MerReprRel> merReprRelList = new ArrayList<MerReprRel>();

//oneTomany로 만들어야할듯
//	@OneToOne(fetch=FetchType.LAZY)	
//	@JoinColumn(name = "contact_id") 
//	private ContactItem contactBase;
	
		
	
	
	
}
