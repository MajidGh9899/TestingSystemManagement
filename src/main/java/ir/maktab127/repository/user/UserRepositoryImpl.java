package ir.maktab127.repository.user;

import ir.maktab127.config.ApplicationContext;
import ir.maktab127.entity.enumeration.UserStatus;
import ir.maktab127.entity.user.User;
import ir.maktab127.repository.base.CrudRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class UserRepositoryImpl<T extends User> extends CrudRepositoryImpl<T,Long> implements UserRepository<T> {


    protected UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }
    @Override
    public Optional<User> findByUsername(String username) {
        try{
            TypedQuery<User> query = entityManager.createQuery(
                    "select  u from User u where u.userName = :username", User.class);
            query.setParameter("username", username);
            try {
                User user = query.getSingleResult();
                return Optional.of(user);
            }catch (Exception e){
                return Optional.empty();
            }
        }finally {
            entityManager.close();
        }
    }
    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "select  u from User u where u.userName = :username and u.password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            try {
                User user = query.getSingleResult();
                return Optional.of(user);
            }   catch (Exception e){
                return Optional.empty();
            }
        }finally {
             entityManager.close();
        }
    }

    @Override
    public List<User> findByStatus(UserStatus status) {

        try {
            TypedQuery<User> query = entityManager.createQuery(
                    "SELECT u FROM User u WHERE u.status = :status", User.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
    @Override
    public List<User> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);

            List<Predicate> predicates = new ArrayList<>();
            if (firstName != null && !firstName.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%"));
            }
            if (lastName != null && !lastName.isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%"));
            }

            cq.where(cb.or(predicates.toArray(new Predicate[0])));
            return entityManager.createQuery(cq).getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        try {
            TypedQuery<Long> query = entityManager.createQuery(
                    "SELECT COUNT(u) FROM User u WHERE u.userName = :username", Long.class);
            query.setParameter("username", username);
            return query.getSingleResult() > 0;
        } finally {
            entityManager.close();
        }
    }



}
