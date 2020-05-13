package portfolio.restbootjpa.account;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service                     //이 빈이 등록되어있어야 스프링부트가 자동 생성해주는 빈이 더이상 생성이 안된다
                             //그리고 로그인 관련 처리가 된다
public class AccountService implements UserDetailsService{

	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public Account createAccount(String username, String password) {
		
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(passwordEncoder.encode(password));
		return accountRepository.save(account);
		
		
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<Account> byUsername = accountRepository.findByUsername(username);
		Account account  =  byUsername.orElseThrow(() -> new UsernameNotFoundException(username));
				
		       //스프링 시큐리티에서 제공하는 구현체
		return new User(account.getUsername() , account.getPassword(), authorities());
	}


	private Collection<? extends GrantedAuthority> authorities() {
		
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
	}
	
	
}


