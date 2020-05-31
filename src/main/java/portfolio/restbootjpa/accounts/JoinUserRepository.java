package portfolio.restbootjpa.accounts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinUserRepository  extends JpaRepository<JoinUser, Long>{

	Optional<JoinUser> findByEmail(String email);
	
}
