package com.egs.bank.model.request;

import com.egs.bank.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequest {

    String firstName;

    String lastName;

    String fingerprint;

    Set<Currency> currencies;
}
