package model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import security.Permission;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @author PC
 *
 */
@Entity
@Table(name = "user_table")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    private String type; //rola
	private String activationCode;
	private boolean activated;
	private LocalDateTime activationCodeValidity;
	private String passwordResetCode;
	private LocalDateTime passwordResetCodeValidity;
	private String loginCode;
	private LocalDateTime loginCodeValidity;


	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	@JsonManagedReference
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Permission> permissions = new HashSet<>();


	public LocalDateTime getActivationCodeValidity() {
		return activationCodeValidity;
	}

	public void setActivationCodeValidity(LocalDateTime activationCodeValidity) {
		this.activationCodeValidity = activationCodeValidity;
	}

	public String getPasswordResetCode() {
		return passwordResetCode;
	}

	public void setPasswordResetCode(String passwordResetCode) {
		this.passwordResetCode = passwordResetCode;
	}

	public LocalDateTime getPasswordResetCodeValidity() {
		return passwordResetCodeValidity;
	}

	public void setPasswordResetCodeValidity(LocalDateTime passwordResetCodeValidity) {
		this.passwordResetCodeValidity = passwordResetCodeValidity;
	}

	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public LocalDateTime getLoginCodeValidity() {
		return loginCodeValidity;
	}

	public void setLoginCodeValidity(LocalDateTime loginCodeValidity) {
		this.loginCodeValidity = loginCodeValidity;
	}

	public Long getId() {
        return this.id;
    }
    public String getRole() {
        return type.toString();
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
	public String getEmail() {
		return email;
	}
	public String getType() {
		return type;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getActivationCode() {
		return activationCode;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public boolean isActivated() {
		return activated;
	}
	public void setActivated(boolean activated) {
		this.activated = activated;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}