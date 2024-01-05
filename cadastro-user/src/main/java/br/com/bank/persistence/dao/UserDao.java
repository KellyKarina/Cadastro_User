package br.com.bank.persistence.dao;

import br.com.bank.persistence.model.Account;
import br.com.bank.persistence.model.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserDao implements dao<Users> {

    @Inject
    EntityManager em;

    @Override
    public Optional<Users> get(Long id) {
        try {
            var query = this.em.createQuery("select u from Users u where id = :id", Users.class);
            return Optional.of(query.setParameter("id", id).getSingleResult());
        } catch (NoResultException ex){
            return Optional.empty();
        }
    }

    @Override
    public List<Users> getAll() {
        var query = this.em.createQuery("select u from Users u ", Users.class);
        return query.getResultList();
    };

    @Override
    @Transactional
    public Account save(Users users) {
    this.em.persist(users);
        return null;
    }

    @Override
    @Transactional
    public void update(Users updateUser) {
        em.merge(updateUser);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Users userDelete = em.find(Users.class, id);
        if (userDelete != null){
            em.remove(userDelete);
        }
    }

}
