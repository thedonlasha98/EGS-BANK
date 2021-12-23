package com.egs.bank.model.request;

import com.egs.bank.enums.Currency;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CashRequest {

    Long cardId;

    Currency currency;

    BigDecimal amount;
}
