package portfolio.restbootjpa.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

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
@Setter
@Builder
public class MerClientBs extends BaseEntity {
	
	@GeneratedValue
	@Id
	private Long clientNo;	
	
	@Enumerated(EnumType.STRING)
	private MerClientType type;
	
	private String clientNm;
		
	@OneToMany(mappedBy = "merClientBs")
	private List<MerReprRel> merReprRelList = new ArrayList<MerReprRel>();
		
	
	@OneToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name = "contact_id_phone") 
	private Phone phone;

	@OneToOne(fetch=FetchType.LAZY)	
	@JoinColumn(name = "contact_id_email") 
	private Email email;
		
	
}
