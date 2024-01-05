package br.com.bank.service;

import br.com.bank.persistence.dao.UserDao;
import br.com.bank.persistence.dto.UserDto;
import br.com.bank.persistence.model.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
   public class UserService {

    @Inject
    UserDao userDao;
  public void addUser(UserDto userData){
      Users users = new Users();
      users.setName(userData.getName());
      users.setAge(userData.getAge());
      users.setTelefone(userData.getTelefone());
      users.setEndereco(userData.getEndereco());
      this.userDao.save(users);
  }

    public List<Users> getUsers(){
        return userDao.getAll();
    }
    public Optional<Users> getUser(Long id){
        return this.userDao.get(id);
    }

    public void editUser(Long id, UserDto updatedUserData) {
        this.userDao.get(id).ifPresent(existingUser -> {
            existingUser.setName(updatedUserData.getName());
            existingUser.setAge(updatedUserData.getAge());
            existingUser.setEndereco(updatedUserData.getEndereco());
            existingUser.setTelefone(updatedUserData.getTelefone());
            this.userDao.update(existingUser);
        });
    }

  public void deleteUser(Long id){
      this.userDao.delete(id);
  }

}
