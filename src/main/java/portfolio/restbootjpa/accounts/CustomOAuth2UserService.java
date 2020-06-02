package portfolio.restbootjpa.accounts;

import java.util.Collections;

import javax.servlet.http.HttpSession;import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import portfolio.restbootjpa.accounts.JoinUserRepository;


@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	
	private final JoinUserRepository joinUserRepository;
	private final HttpSession httpSession ;
	
	
				
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		
		OAuth2User oAuth2User = delegate.loadUser(userRequest);		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();		
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		
		OAuthAttributes attributes = OAuthAttributes.of(registrationId,userNameAttributeName ,oAuth2User.getAttributes() );
	
		
		
		JoinUser user = saveOrUpdate(attributes);
		
		httpSession.setAttribute("user", new SessionJoinUser(user));			
		
	
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())), attributes.getAttributes(), attributes.getNameAttributeKey());
	}



	private JoinUser saveOrUpdate(OAuthAttributes attributes) {
		JoinUser user = joinUserRepository.findByEmail(attributes.getEmail())
						.map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
						.orElse(attributes.toEntity());											
		return joinUserRepository.save(user);
	}
	
	
	
	

}
