package br.com.bank.persistence.dao;

import br.com.bank.persistence.model.Account;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@ApplicationScoped
public class AccountDao implements dao<Account> {

    @Inject
    private EntityManager em;

    @Override
    public Optional<Account> get(Long id) {
        try {
            var query = this.em.createQuery("select a from Account a where id = :id", Account.class);
            return Optional.of(query.setParameter("id", id).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Account> getAll() {
        var query = this.em.createQuery("select a from Account a ", Account.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Account save(Account account) {
        String numContaUnico = gerarNumContaUnico();
        account.setNumConta(numContaUnico);
        this.em.persist(account);
        return account;
    }

    private String gerarNumContaUnico() {
        return null;
    }

    @Override
    @Transactional
    public void update(Account updatedAccount) {
        em.merge(updatedAccount);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Account accountToDelete = em.find(Account.class, id);
        if (accountToDelete != null) {
            em.remove(accountToDelete);
        }
    }

    public Optional<Account> getNumConta(String numConta) {
        try {
            var query = this.em.createQuery("select a from Account a where numConta = :numConta", Account.class);
            return Optional.of(query.setParameter("numConta", numConta).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Transactional
    public void updateSaldo(Account account) {
        em.merge(account);
    }
}
