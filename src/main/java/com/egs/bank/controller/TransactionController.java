package com.egs.bank.controller;

import com.egs.bank.model.request.CashRequest;
import com.egs.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cash")
public class TransactionController {

    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/deposit")
    void cashDeposit(@RequestBody CashRequest cashRequest){
        transactionService.cashDeposit(cashRequest);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/withdrawal")
    void cashWithdrawal(@RequestBody CashRequest cashRequest){
        transactionService.cashWithdrawal(cashRequest);
    }
}
