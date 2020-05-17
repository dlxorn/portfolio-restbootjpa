package portfolio.restbootjpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import portfolio.restbootjpa.Entity.MerBs;


public interface MerBsRepository extends JpaRepository<MerBs, Long>  {
	
	public  List<MerBs> findByMerNm(String merNm);
	
	public  List<MerBs> findYoyoByMerNmAndMerNmStartingWith(String merNm,String merNmlike);
	
    @Query("select count(m) from MerBs m where merNm = :merNm")  
    public long countByMerNm(@Param("merNm") String merNm  );
        
   
    @Query("select c.clientNm from MerBs m left join m.merClientBs c where m.merNm = :merNm")  
    public String findClientNmByMerNm(@Param("merNm") String merNm);
       		
    @Modifying(clearAutomatically = true)
    @Query("update MerBs m set m.merNm = :merNm")
    int bulkChangeMerNm(@Param("merNm") String merNm);

    
    @Query("select m from MerBs m join fetch m.merClientBs c")
    public List<MerBs> findMerBsFetchJoin();
        
    @Query(value = "select m from MerBs m left join fetch m.merClientBs c", countQuery ="select count(m) from MerBs m" )
    public Page<MerBs> findMerBsFetchJoinPage(Pageable pageable);
  
    
    
    
    
    
    
	
	
}
