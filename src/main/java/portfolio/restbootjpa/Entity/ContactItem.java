package portfolio.restbootjpa.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CONTACT_DT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)  
@DiscriminatorColumn(name = "CONTACT_TYPE")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public  class ContactItem extends BaseEntity{
		
	@Id
	@GeneratedValue
	private Long id;		
	
	private String contactCtnt;
				

}
