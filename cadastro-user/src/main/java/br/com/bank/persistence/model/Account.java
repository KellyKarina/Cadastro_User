package br.com.bank.persistence.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Contas")
public class Account {

    public enum AccountType {
        CONTA_CORRENTE,
        CONTA_POUPANCA;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipoConta")
    @Enumerated(EnumType.STRING)
    private AccountType tipoConta;

    @JoinColumn(name = "userId")
    @OneToOne
    private Users users;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Column(name = "numConta", unique = true)
    private String numConta;

    public Account() {
        this.saldo = BigDecimal.ZERO;
    }

    public String getNumConta() {
        return numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public AccountType getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(AccountType tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
}
