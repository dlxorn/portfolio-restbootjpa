package portfolio.restbootjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import portfolio.restbootjpa.entity.MerClientBs;

public interface MerClientBsRepository extends JpaRepository<MerClientBs, Long> {

	@Query(value = "select m from MerClientBs m join m.merReprRelList c join c.merBs d where d.merNo = :merNo order by m.clientNo asc")
	    public List<MerClientBs> findMerClientBsByMerNo(Long merNo);	     
	 	

}
