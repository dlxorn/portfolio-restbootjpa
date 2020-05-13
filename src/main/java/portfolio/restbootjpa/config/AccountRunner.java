package portfolio.restbootjpa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import portfolio.restbootjpa.account.AccountService;

@Component
public class AccountRunner implements ApplicationRunner{

	@Autowired
	AccountService accountService;
	
	
	
	//임시 테스트용 어카운트
	@Override
	public void run(ApplicationArguments args) throws Exception {
		accountService.createAccount("keesun", "1234");
		
	}
	
	
}
