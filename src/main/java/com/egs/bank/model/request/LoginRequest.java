package com.egs.bank.model.request;

import lombok.Data;

@Data
public class LoginRequest {

    String cardNo;

    String fingerprint;

    String pin;
}
