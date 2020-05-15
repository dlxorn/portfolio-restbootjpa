package portfolio.restbootjpa.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class ReRegBase  extends BaseEntity{

	@Id
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
