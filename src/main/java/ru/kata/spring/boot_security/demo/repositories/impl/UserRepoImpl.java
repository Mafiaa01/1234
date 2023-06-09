package ru.kata.spring.boot_security.demo.repositories.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserRepoImpl implements UserRepo {
    @PersistenceContext
    private EntityManager entityManager;

    protected EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public void addUser(User user) {
        getEntityManager().persist(user);

    }

    @Override
    public void deleteUser(Long id) {
        try {
            User user = getEntityManager().find(User.class, id);
            if (user != null) {
                getEntityManager().remove(user);
            }
        } catch (NullPointerException e) {
            System.out.println("Dont user id");
        }
    }

    @Override
    public void editUser(User user) {
        getEntityManager().merge(user);
    }

    @Override
    public User getUserById(Long id) {
        return getEntityManager().find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        return getEntityManager().createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUserByUsername(String username) {
        return getEntityManager().createQuery("select u from User u where u.username = :username", User.class).setParameter("username", username).getSingleResult();
    }
}
