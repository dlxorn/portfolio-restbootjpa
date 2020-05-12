package portfolio.restbootjpa.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import portfolio.restbootjpa.Entity.Member;

@RestController
public class TestController {

	@PersistenceContext
	EntityManager em;
	
	
	@GetMapping("/")
	@Transactional
	public String haha() {
		
		Member a = new Member("zzzzzzzz");
			
		em.persist(a);
		
		em.flush();
		em.clear();
		
		Member b =em.find(Member.class, a.getId());
		
		System.out.println(b);
				
		
		
	
		
		return "haha";
	}
}
