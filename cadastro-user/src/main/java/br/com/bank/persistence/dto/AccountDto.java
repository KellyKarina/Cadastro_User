package br.com.bank.persistence.dto;

import br.com.bank.persistence.model.Account;

import java.math.BigDecimal;

public class AccountDto {

    private Account.AccountType tipoConta;
    private Long userId;
    private BigDecimal valorDeposito;
    private String numConta;
    private BigDecimal valorSaque;

    public Account.AccountType getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(Account.AccountType tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getValorDeposito() {
        return valorDeposito;
    }

    public void setValorDeposito(BigDecimal valorDeposito) {
        this.valorDeposito = valorDeposito;
    }

    public String getNumConta() {
        return numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }


    public BigDecimal getValorSaque() {
        return valorSaque;
    }

    public void setValorSaque(BigDecimal valorSaque) {
        this.valorSaque = valorSaque;
    }
}
