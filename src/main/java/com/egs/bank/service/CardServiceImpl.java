package com.egs.bank.service;

import com.egs.bank.domain.Account;
import com.egs.bank.domain.Card;
import com.egs.bank.enums.Currency;
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
import java.util.HashSet;
import java.util.Set;

@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    private AccountRepository accountRepository;

    private PasswordEncoder encoder;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository, AccountRepository accountRepository, PasswordEncoder encoder) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public CardDto registerCard(CardRequest cardRequest) {
        String cardNo = Utils.randomNumber(16);
        String pin = Utils.randomNumber(4);

        Card card = generateCard(cardRequest, cardNo, pin);
        cardRepository.save(card);
        Set<Account> accounts = new HashSet<>();

        for (Currency currency : cardRequest.getCurrencies()) {
            Account account = Account.builder()
                    .card(card)
                    .currency(currency)
                    .balance(BigDecimal.ZERO)
                    .build();
            accounts.add(account);
        }
        accountRepository.saveAll(accounts);

        return CardDto.builder()
                .cardNo(cardNo)
                .pin(pin)
                .build();
    }

    private Card generateCard(CardRequest cardRequest, String cardNo, String pin) {

        return Card.builder()
                .cardHolder(cardRequest.getFirstName().toUpperCase() + " " + cardRequest.getLastName().toUpperCase())
                .attempts(0)
                .isBlocked(0)
                .cardNo(encoder.encode(cardNo))
                .fingerprint(encoder.encode(cardRequest.getFingerprint()))
                .pin(encoder.encode(pin))
                .build();
    }
}
