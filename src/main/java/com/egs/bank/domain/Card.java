package com.egs.bank.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    String cardNo;

    String cardHolder;

    String pin;

    String fingerprint;

    int attempts;

    int isBlocked;

}
