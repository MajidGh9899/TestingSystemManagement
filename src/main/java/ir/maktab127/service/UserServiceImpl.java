package ir.maktab127.service;

import ir.maktab127.entity.enumeration.UserStatus;
import ir.maktab127.entity.user.User;
import ir.maktab127.repository.user.UserRepository;
import ir.maktab127.service.base.BaseServiceImpl;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl extends BaseServiceImpl<User,Long , UserRepository<User>> implements UserService {

    public UserServiceImpl(UserRepository<User> repository) {
        super(repository);
    }

    @Override
    public Optional<User> findByUsername(String username) {

        Optional<User> user = Optional.empty();
        try {
            repository.beginTransaction();
             user = repository.findByUsername(username);
            repository.commitTransaction();

        } catch (Exception e) {
            if (repository.isTransactionActive()) {
                repository.rollbackTransaction();
            }
        }
        return user;

    }

    @Override
    public List<User> findByStatus(UserStatus status) {

        try {
            repository.beginTransaction();
            List<User> users = repository.findByStatus(status);
            repository.commitTransaction();
            return users;
        }catch (RuntimeException e) {
            if (repository.isTransactionActive()) {
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> findByName(String name) {

        try {
            repository.beginTransaction();
            List<User> users = repository.findByFirstNameContainingOrLastNameContaining(name,name);
            repository.commitTransaction();
            return users;
        } catch (RuntimeException e) {
            if( repository.isTransactionActive())
                repository.rollbackTransaction();
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean existsByUsername(String username) {
        try {
            repository.beginTransaction();
            boolean exists = repository.existsByUsername(username);
            repository.commitTransaction();
            return exists;
        } catch (RuntimeException e) {
            if( repository.isTransactionActive())
                repository.rollbackTransaction();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStatus(Long userId, UserStatus status) {
        Optional<User> userOptional = findById(userId);
        if (userOptional.isPresent()) {
            try {
                User usr = userOptional.get();
                usr.setStatus(status);
                repository.beginTransaction();
                repository.save(usr);
                repository.commitTransaction();
            } catch (Exception e) {
                if (repository.isTransactionActive()) {
                    repository.rollbackTransaction();
                }
                throw new RuntimeException(e);
            }
        }
        else {
            throw new RuntimeException("User not found with id: " + userId );
        }
    }
}
