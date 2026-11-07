package com.example.service;

import com.example.dto.AccountBalanceResponse;
import com.example.repository.AccountBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBalanceService {

    @Autowired
    private final AccountBalanceRepository balanceRepository;

    public AccountBalanceService(AccountBalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    public AccountBalanceResponse getAccountBalance(
            String accountId) {

        if (accountId == null || accountId.isBlank()) {
            throw new IllegalArgumentException("Account ID cannot be empty");
        }

        return balanceRepository.getAccountBalance(accountId);
    }
}
