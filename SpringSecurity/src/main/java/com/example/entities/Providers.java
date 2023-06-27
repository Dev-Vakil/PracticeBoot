package com.example.entities;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="providers")
public class Providers  implements UserDetails{
	
	 @Column(unique=true)
	 @Id
	 @GeneratedValue
	 private Integer provider_id;
	 
	 @Column(unique=true, nullable = false, length = 128)	 
	 private String provider_name;
	 
	 @Column(unique=true,nullable = false, length = 30)	 
	 private String provider_code;	 
	 
	 @Column(unique=true, nullable = false, length = 30)	
	 private String username;
	 
	 @Column(nullable = false, length = 128)	 	
	 private String password;
	 
	 @Column(unique=true, nullable = false, length=256)	 	 
	 private String email;
	 
	 private Boolean is_active;
	
	 @CreationTimestamp
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "created_at", updatable = true, nullable = true)
	 private Date created_at;

     @UpdateTimestamp
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "modified_at", updatable = true, nullable = true)
	 private Date modified_at;

     @OneToMany(mappedBy="provider")
     private List<RoleAssociation> roleAssociation; 
     
     public enum Role{
    	 USER,ADMIN
     }
     
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
		
	  
}
