package com.egs.bank.service;

import com.egs.bank.domain.Account;
import com.egs.bank.domain.Card;
import com.egs.bank.enums.Currency;
import com.egs.bank.exception.EGSException;
import com.egs.bank.exception.ErrorKey;
import com.egs.bank.model.dto.CardDto;
import com.egs.bank.model.request.CardRequest;
import com.egs.bank.repository.AccountRepository;
import com.egs.bank.repository.CardRepository;
import com.egs.bank.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    private AccountService accountService;

    private PasswordEncoder encoder;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, AccountService accountService, PasswordEncoder encoder) {
        this.cardRepository = cardRepository;
        this.accountService = accountService;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public CardDto registerCard(CardRequest cardRequest) {
        String cardNo = Utils.randomNumber(16);
        String pin = Utils.randomNumber(4);

        Card card = generateCard(cardRequest, cardNo, pin);
        cardRepository.save(card);
        accountService.createAccount(cardRequest.getCurrencies(),card);

        return CardDto.builder()
                .cardNo(cardNo)
                .pin(pin)
                .build();
    }

    @Override
    public Card getCard(Long id){
        return cardRepository.findById(id)
                .orElseThrow(() -> new EGSException(ErrorKey.CARD_NOT_FOUND));
    }

    private Card generateCard(CardRequest cardRequest, String cardNo, String pin) {

        return Card.builder()
                .cardHolder(cardRequest.getFirstName().toUpperCase() + " " + cardRequest.getLastName().toUpperCase())
                .attempts(0)
                .isBlocked(0)
                .cardNo(Base64.getEncoder().encodeToString(cardNo.getBytes()))
                .fingerprint(encoder.encode(cardRequest.getFingerprint()))
                .pin(encoder.encode(pin))
                .build();
    }
}
