package app.com.ChinChen.security.services;


import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.com.ChinChen.domain.Employee;

public class EmployeeDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private String id;

	private String account;

	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public EmployeeDetailsImpl(String id, String account, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.account = account;
		this.authorities = authorities;
	}

	public static EmployeeDetailsImpl build(Employee emp) {
		List<GrantedAuthority> authorities = emp.getJobList().stream()
				.map(joblist -> new SimpleGrantedAuthority(joblist))
				.collect(Collectors.toList());

		return new EmployeeDetailsImpl(
			emp.get_id(), 
			emp.getAccount(), 
			emp.getEmail(),
			emp.getPassword(), 
			authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return account;
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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		EmployeeDetailsImpl emp = (EmployeeDetailsImpl) o;
		return Objects.equals(id, emp.id);
	}
}