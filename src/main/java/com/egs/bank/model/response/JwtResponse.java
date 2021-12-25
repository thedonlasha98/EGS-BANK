package com.egs.bank.model.response;

import lombok.Data;

@Data
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long cardId;

	public JwtResponse(String token, Long cardId) {
		this.token = token;
		this.cardId = cardId;
	}
}
