package portfolio.restbootjpa.accounts;

import java.util.Map;

import org.springframework.security.core.userdetails.User;

import lombok.Builder;
import lombok.Getter;
import portfolio.restbootjpa.accounts.JoinUser;
import portfolio.restbootjpa.accounts.Role;

@Getter
public class OAuthAttributes {
	
	private Map<String, Object> attributes;
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;
	
	
	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
		this.attributes =attributes;
		this.nameAttributeKey =nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
			
	}
	
	public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {		

		if("naver".equals(registrationId)) {
			return ofNaver("id",attributes );
		}
		
				
		return ofGoogle(userNameAttributeName, attributes);
	}
		

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String,Object> attributes) {
		return OAuthAttributes.builder()
					.name((String) attributes.get("name"))
					.email((String) attributes.get("email"))
					.picture((String) attributes.get("picture"))
					.attributes(attributes)
					.nameAttributeKey(userNameAttributeName)				
					.build();	
	}
	
	
	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String,Object> attributes) {
		Map<String,Object> response = (Map<String,Object>)attributes.get("response");
				
		
		return OAuthAttributes.builder()
					.name((String) response.get("name"))
					.email((String) response.get("email"))
					.picture((String) response.get("picture"))
					.attributes(response)
					.nameAttributeKey(userNameAttributeName)				
					.build();	
	}
	
	
	
	public JoinUser toEntity() {
				
		return JoinUser.builder()
				.name(name)
				.email(email)
				.picture(picture)
				.role(Role.GUEST)
				.build();		
		
	}
	

}
