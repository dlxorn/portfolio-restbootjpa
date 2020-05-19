package portfolio.restbootjpa.common;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;


//properties에 설정값을 만들어 뒤기 위하여 이거 쓰는 것 같다.
@Component
@ConfigurationProperties(prefix = "my-app")
@Getter
@Setter
public class AppProperties {

	@NotEmpty
	private String adminUsername;

	@NotEmpty
	private String adminPassword;

	@NotEmpty
	private String userUsername;

	@NotEmpty
	private String userPassword;

	@NotEmpty
	private String clientId;

	@NotEmpty
	private String clientSecret;

}
