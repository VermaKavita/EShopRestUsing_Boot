package com.ecom.shopping_cart.security;

import com.ecom.shopping_cart.entities.Roles;
import com.ecom.shopping_cart.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class MyUserDetails  implements UserDetails {

	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private Set<GrantedAuthority> authorities;
	public MyUserDetails(User user) {
		this.email=user.getEmail();
		this.password=user.getPassword();
		this.authorities = extractAuthorities(user.getRoles());
	}
	public static MyUserDetails createInstance(User user) {
		return new MyUserDetails(user);
	}


	private Set<GrantedAuthority> extractAuthorities(Set<Roles> roles) {
		Set<GrantedAuthority> authorities = new HashSet<> ();
		for (Roles role : roles) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
		}
		return authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
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

