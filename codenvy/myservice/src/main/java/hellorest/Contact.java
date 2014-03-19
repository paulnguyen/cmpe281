package hellorest;


import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;


@Entity
@Table(name = "contact")
public class Contact implements Serializable {

	private Long id;
	private int version;
	private String firstName;
	private String lastName;
	private DateTime birthDate;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Version
	@Column(name = "VERSION")
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	@NotNull
	@Size(min=3, max=60)
	@Column(name = "FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Column(name = "BIRTH_DATE")
	@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
	public DateTime getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(DateTime birthDate) {
		this.birthDate = birthDate;
	}	
	
	public String toString() {		
		return "Contact - Id: " + id + ", First name: " + firstName 
				+ ", Last name: " + lastName + ", Birthday: " + birthDate;
	}		
	
}
