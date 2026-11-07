package com.example.controller;

import com.example.dto.AccountBalanceResponse;
import com.example.service.AccountBalanceService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
@Validated
public class AccountBalanceController {

    @Autowired
    private final AccountBalanceService service;

    public AccountBalanceController(AccountBalanceService service) {
        this.service = service;
    }

//    @GetMapping("/{accountId}")
//    public AccountBalanceResponse getAccountBalance(
//            @PathVariable
//            @NotBlank
//            String accountId) {
//
//        return service.getAccountBalance(accountId);
//    }

    @GetMapping("/{accountId}")
    Map<String, String> routController(@PathVariable String accountId) {
        return Map.of("message",accountId);
    }
}
