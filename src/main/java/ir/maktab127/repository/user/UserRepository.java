package ir.maktab127.repository.user;

import ir.maktab127.entity.enumeration.UserStatus;
import ir.maktab127.entity.user.User;
import ir.maktab127.repository.base.CrudRepository;

import java.util.List;
import java.util.Optional;
public interface UserRepository <T extends User> extends CrudRepository<T, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);

    List<User> findByStatus(UserStatus status);

    List<User> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    boolean existsByUsername(String username);
}
