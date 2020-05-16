package portfolio.restbootjpa.controller;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import portfolio.restbootjpa.Entity.MerBs;


@RestController
public class TestController {

	@PersistenceContext
	EntityManager em;
	


	
	@GetMapping("/")
	@Transactional
	public ResponseEntity haha() {
		
	
		
		MerBs a = new MerBs();
	
					
		em.persist(a);
		
			
		
		em.flush();
		em.clear();
		
		
		
		return null;
		
//		
//	
//		
//		MerBs b =em.find(MerBs.class, a.getMerNo());
//		
//		System.out.println(b);
//
//		
//		//자체 url 생성한다.
//		//헤더스에 Headers 에 Location을 등록한다
//         URI uri = WebMvcLinkBuilder.linkTo(TestController.class).slash(a.getMerNo()).toUri();
//  
//         MemberResource resource = new MemberResource(b);
//         
//         //linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
//              
//         
//         resource.add( linkTo(TestController.class).slash(a.getMerNo()).withSelfRel());
//         resource.add( linkTo(TestController.class).withRel("hoho"));
//                  
//         //링크는 확인했고
//         
//         
// 		 // eventResource.add(selfLinkBuilder.withRel("update-event"));
// 		
// 		//링크를 뺄 수 있는 방법 한가지만 파악하자
//        //
//         
//    //     resource.add( linkTo(TestController.class).slash(b.getId()).withSelfRel());
//                   		
// 		return new ResponseEntity(HttpStatus.OK).created(uri).body(resource); 
 		
 		
     
     //      return  new ResponseEntity(HttpStatus.OK).created(uri).build();
      
		
		
	//	return null;
		
		
		
		//
		//ResponseEntity					 		
	//	return "haha";		
	//	return new ResponseEntity(b,HttpStatus.OK); 기본적으로 객체에 대한 응답만 줄때 이거 사용
		
	
	}
	
	
	
	@GetMapping("/nell")
	public ResponseEntity hoho() {
		
		
		
		
	
		return null;
	}
	
}
