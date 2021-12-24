package com.egs.bank.security.services;

import com.egs.bank.domain.Card;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String cardNo;

	private String fingerprint;

	@JsonIgnore
	private String pin;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String cardNo, String fingerprint, String pin,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.cardNo = cardNo;
		this.fingerprint = fingerprint;
		this.pin = pin;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(Card card) {
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ADMIN"));

		return new UserDetailsImpl(
				card.getId(),
				card.getCardNo(),
				card.getFingerprint(),
				card.getPin(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getPin() {
		return pin;
	}

	@Override
	public String getPassword() {
		return fingerprint;
	}

	@Override
	public String getUsername() {
		return cardNo;
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
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}
}
