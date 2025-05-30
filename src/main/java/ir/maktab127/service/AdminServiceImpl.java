package ir.maktab127.service;

import ir.maktab127.entity.user.Admin;
import ir.maktab127.repository.user.AdminRepository;
import ir.maktab127.repository.user.UserRepository;
import ir.maktab127.service.base.BaseService;
import ir.maktab127.service.base.BaseServiceImpl;

import java.util.Optional;

public  class AdminServiceImpl extends BaseServiceImpl<Admin, Long, AdminRepository> implements AdminService {
    private final UserRepository userRepository;

    public AdminServiceImpl(AdminRepository repository, UserRepository userRepository) {
        super(repository);
        this.userRepository = userRepository;
    }


    @Override
    public Optional<Admin> findByUsername(String username) {

        try {
            repository.beginTransaction();
            Optional<Admin> admin = userRepository.findByUsername(username);
            repository.commitTransaction();
            return admin.filter(user-> true)
                    .map(user->(Admin)user);
        } catch (RuntimeException e) {
            if(repository.isTransactionActive()){
                repository.rollbackTransaction();
            }
            throw new RuntimeException(e);
        }
    }
}
