package com.egs.bank.controller;

import com.egs.bank.enums.Currency;
import com.egs.bank.model.dto.CardDto;
import com.egs.bank.model.request.CardRequest;
import com.egs.bank.service.AccountService;
import com.egs.bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    private final AccountService accountService;

    @Autowired
    public CardController(CardService cardService, AccountService accountService) {
        this.cardService = cardService;
        this.accountService = accountService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CardDto registerCard(@RequestBody CardRequest cardRequest){

        return cardService.registerCard(cardRequest);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}/balance")
    Map<Currency, BigDecimal> getBalance(@PathVariable Long id){

        return accountService.getBalance(id);
    }
}
