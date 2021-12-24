package com.egs.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    private Card card;

    private String token;

    private Instant expiryDate;
}
