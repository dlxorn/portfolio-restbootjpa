package portfolio.restbootjpa.Entity;

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
public class ReRegDt  extends BaseEntity{

	@Id
	@GeneratedValue
	private Long id;
	

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name= "report_base_id")
	private ReRegBase reportBase;
	
	private String beforeCtnt;
	private String afterCtnt;
		
	
	@Enumerated(EnumType.STRING)
	private ReRegType reRegType;
	
	
		
	
	
	
}
