package com.example.entities;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
		 

	@Column(unique=true,name="provider_id")
	 @Id
	 @GeneratedValue(strategy= GenerationType.IDENTITY)
	 private Integer providerId;
	 
	 @Column(name="provider_name", unique=true, nullable = false, length = 128)	 
	 private String providerName;
	 
	 @Column(name="provider_code", unique=true,nullable = false, length = 30)	 
	 private String providerCode;	 
	 
	 @Column(unique=true, nullable = false, length = 30)	
	 private String username;
	 
	 @Column(nullable = false, length = 128)	 	
	 private String password;
	 
	 @Column(unique=true, nullable = false, length=256)	 	 
	 private String email;
	 
	 @Column(name="is_active")
	 private Boolean isActive;
	
	 @CreationTimestamp
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "created_at", updatable = true, nullable = true)
	 private Date createdAt;

     @UpdateTimestamp
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "modified_at", updatable = true, nullable = true)
	 private Date modifiedAt;

     @JsonBackReference
     @OneToMany(mappedBy="provider")
     private List<RoleAssociation> roleAssociation;
     
     @JsonBackReference
     @OneToMany(mappedBy="provider")
     private List<PayerProvider> payerProvider;
     
     public enum Role{
    	 USER,ADMIN,PAYER
     }
     
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();	
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
		
	  
}
