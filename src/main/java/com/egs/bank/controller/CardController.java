package com.egs.bank.controller;

import com.egs.bank.model.dto.CardDto;
import com.egs.bank.model.request.CardRequest;
import com.egs.bank.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CardDto registerCard(@RequestBody CardRequest cardRequest){

        return cardService.registerCard(cardRequest);
    }
}
