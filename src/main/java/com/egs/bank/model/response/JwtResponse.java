package com.egs.bank.model.response;

import lombok.Data;

import java.util.List;
@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String refreshToken;
	private Long id;

	public JwtResponse(String token, String refreshToken, Long id) {
		this.token = token;
		this.refreshToken = refreshToken;
		this.id = id;
	}
}
