package br.com.bank.persistence.dao;


import br.com.bank.persistence.model.Account;


import java.util.List;
import java.util.Optional;

public interface dao<T>{

    Optional<T> get(Long id);

    List<T> getAll();

    Account save(T data);

    void update(T data);

    void delete(Long id);

}
