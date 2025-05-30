package ir.maktab127.service;

import ir.maktab127.entity.user.Admin;
import ir.maktab127.service.base.BaseService;
import java.util.Optional;
public interface AdminService extends BaseService<Admin, Long> {

    Optional<Admin> findByUsername(String username);
}
