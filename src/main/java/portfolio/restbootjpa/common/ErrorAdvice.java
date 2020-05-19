package portfolio.restbootjpa.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorAdvice {
		
	@ExceptionHandler(Exception.class)
	public ResponseEntity custom(Exception e) {	
		e.printStackTrace();
			
		return ResponseEntity.badRequest().body("error");				
   }

}
