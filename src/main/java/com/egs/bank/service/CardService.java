package com.egs.bank.service;

import com.egs.bank.model.dto.CardDto;
import com.egs.bank.model.request.CardRequest;

public interface CardService {

    CardDto registerCard(CardRequest cardRequest);
}
