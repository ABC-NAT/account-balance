package com.example.repository;

import com.example.dto.AccountBalanceResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.security.auth.login.AccountNotFoundException;
import java.sql.CallableStatement;
import java.sql.ResultSet;

@Repository
public class AccountBalanceRepository {

    private static final String SQL =
            "{ call AC_GET_ACCOUNT_BALANCE(?, ?) }";

    private final JdbcTemplate jdbcTemplate;

    public AccountBalanceRepository(@Qualifier("secondaryJdbcTemplate")
                                    JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public AccountBalanceResponse getAccountBalance(
            String accountId) {

        return jdbcTemplate.execute(
                connection -> {
                    CallableStatement cs = connection.prepareCall(SQL);
                    cs.setString(1, accountId);
                    cs.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                    return cs;
                },

                (CallableStatementCallback<AccountBalanceResponse>) cs -> {
                    cs.execute();
                    try (ResultSet rs = (ResultSet) cs.getObject(2)) {
                        if (rs == null || !rs.next()) {
                            throw new AccountNotFoundException("Account not found: " + accountId);
                        }

                        AccountBalanceResponse response = mapResultSet(rs);
                        if (rs.next()) {
                            throw new IncorrectResultSizeDataAccessException("Expected one account but found multiple records", 1);
                        }

                        return response;

                    } catch (AccountNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    private AccountBalanceResponse mapResultSet(
            ResultSet rs) throws java.sql.SQLException {

        AccountBalanceResponse response =
                new AccountBalanceResponse();

        response.setAccountId(
                rs.getString("ACCOUNTID"));

        response.setCustomerId(
                rs.getString("CUSTOMERID"));

        response.setCurrency(
                rs.getString("CURRENCY"));

        response.setLimitRef(
                rs.getString("LIMITREF"));

        response.setCategory(
                rs.getString("CATEGORY"));

        response.setLimitId(
                rs.getString("LIMITID"));

        response.setLimitExpiryDate(
                rs.getString("LIMITEXPIRYDATE"));

        response.setWorkingBalance(
                rs.getBigDecimal("WORKINGBALANCE"));

        response.setLockedAmountTotal(
                rs.getBigDecimal("LOCKEDAMOUNTTOTAL"));

        response.setLimitBalance(
                rs.getBigDecimal("LIMITBALANCE"));

        return response;
    }
}
