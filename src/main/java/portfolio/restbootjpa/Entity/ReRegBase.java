package portfolio.restbootjpa.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReRegBase  extends BaseEntity{

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="mer_no")
	private MerBs merbs;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="mer_client_bs")
	private MerClientBs merClientBs;	
		
	
	@OneToMany(mappedBy = "reportBase")
	private List<ReRegDt> reportDtList = new ArrayList<ReRegDt>();
				
	
}
