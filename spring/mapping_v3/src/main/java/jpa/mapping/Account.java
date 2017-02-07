package jpa.mapping;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {

	private static final long serialVersionUID = -3394712924710949728L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "fullname")
	private String fullname;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "account", cascade = CascadeType.ALL)
	private UserProfile userProrile;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public UserProfile getUserProrile() {
		return userProrile;
	}

	public void setUserProrile(UserProfile userProrile) {
		this.userProrile = userProrile;
	}

}
