package portfolio.restbootjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import portfolio.restbootjpa.entity.ContactItem;

public interface ContactRepository  extends JpaRepository<ContactItem, Long>{
	
	
	

}
