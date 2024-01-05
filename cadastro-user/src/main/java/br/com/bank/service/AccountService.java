package br.com.bank.service;

import br.com.bank.persistence.dao.AccountDao;
import br.com.bank.persistence.dto.AccountDto;
import br.com.bank.persistence.model.Account;
import br.com.bank.persistence.model.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@ApplicationScoped
public class AccountService {

    @Inject
    private AccountDao accountDao;

    public Account addAccount(AccountDto userData) {
        Account account = new Account();
        Users user = new Users();
        user.setId(userData.getUserId());
        account.setUsers(user);
        account.setTipoConta(userData.getTipoConta());
        account.setNumConta(gerarNumContaUnico());
        Account savedAccount = accountDao.save(account);
        return savedAccount;
    }

    public List<Account> getAccounts() {
        List<Account> accounts = accountDao.getAll();
        System.out.println("Contas listadas: " + accounts);
        return accounts;
    }

    public Optional<Account> editAccount(Long id, AccountDto updatedAccountData) {
        Optional<Account> existsAccountOptional = this.accountDao.get(id);
        existsAccountOptional.ifPresent(existingAccount -> {
            existingAccount.setTipoConta(updatedAccountData.getTipoConta());
            this.accountDao.update(existingAccount);
        });
        return existsAccountOptional;
    }


    public void deposit(String numeroConta, BigDecimal valorDeposito) {
        try {
            Optional<Account> contaOptional = accountDao.getNumConta(numeroConta);
            contaOptional.ifPresent(conta -> {
                BigDecimal novoSaldo = conta.getSaldo().add(valorDeposito);
                if (conta.getTipoConta() == Account.AccountType.CONTA_POUPANCA) {
                    novoSaldo = novoSaldo.add(new BigDecimal("0.5"));
                }
                conta.setSaldo(novoSaldo);
                accountDao.update(conta);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sacar(String numeroConta, BigDecimal valorSaque) {
        try {
            if (valorSaque.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
            }
            Optional<Account> contaOptional = accountDao.getNumConta(numeroConta);
            contaOptional.ifPresent(conta -> {
                try {
                    BigDecimal saldoAtual = conta.getSaldo();
                    if (saldoAtual.compareTo(valorSaque) >= 0) {
                        BigDecimal novoSaldo = saldoAtual.subtract(valorSaque);
                        conta.setSaldo(novoSaldo);
                        accountDao.update(conta);
                    } else {
                        throw new RuntimeException("Saldo insuficiente para realizar o saque.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Erro interno ao realizar o saque: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao realizar o saque: " + e.getMessage());
        }
    }


    private String gerarNumContaUnico() {
        Random rand = new Random();
        int numGerado = rand.nextInt(1000000);
        return String.format("%06d", numGerado);
    }

    public Optional<Account> getNumConta(Integer numConta) {
        return accountDao.getNumConta(String.valueOf(numConta));
    }

    public Optional<Account> getAccount(Long id) {
        return this.accountDao.get(id);
    }

    public void deleteAccount(Long id) {
        this.accountDao.delete(id);
    }

    public void ContaCorrente() {
        this.saldo = 0.0;
    }
    private double saldo;

}