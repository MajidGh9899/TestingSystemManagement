package ir.maktab127.service;

import ir.maktab127.entity.enumeration.UserStatus;
import ir.maktab127.entity.user.User;
import ir.maktab127.service.base.BaseService;

import java.util.List;
 import java.util.Optional;
public interface UserService extends BaseService<User,Long> {
    Optional<User> findByUsername(String username);

    List<User> findByStatus(UserStatus status);

    List<User> findByName(String name);

    boolean existsByUsername(String username);

    void updateStatus(Long userId, UserStatus status);
}
