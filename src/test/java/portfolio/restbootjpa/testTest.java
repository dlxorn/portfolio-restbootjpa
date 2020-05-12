package portfolio.restbootjpa;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;



@SpringBootTest
@AutoConfigureMockMvc 
public class testTest {
	
	@Autowired
	protected MockMvc mockMvc;  
		
	

	@Test
	public void hahaha() throws Exception {
		MvcResult result = mockMvc.perform(
				get("/").contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaTypes.HAL_JSON))						
		.andDo(print())
		.andExpect(status().isOk())
		.andReturn();
		
		
	 assertTrue(result.getResponse().getContentAsString().equals("haha"));
		
	//     .andExpect(content().string(containsString("This content is only shown to users.")));
		
		
	//	.andExpect()
	//	.andExpect(content().string("haha"));
	//	.andExpect(status().isCreated());
		

		
				
	}
	
}
