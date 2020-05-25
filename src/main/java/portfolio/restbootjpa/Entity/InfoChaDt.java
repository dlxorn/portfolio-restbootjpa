package portfolio.restbootjpa.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import portfolio.restbootjpa.constraint.ReRegType;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class InfoChaDt  extends BaseEntity{

	@Id
	@GeneratedValue
	private Long id;
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name= "info_chng_base_id")
	private InfoChaBase reportBase;
	
	private String beforeCtnt;
	private String afterCtnt;
		
	
	@Enumerated(EnumType.STRING)
	private ReRegType reRegType;
	
	
		
	
	
	
}
