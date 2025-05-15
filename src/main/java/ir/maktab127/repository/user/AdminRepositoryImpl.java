package ir.maktab127.repository.user;

import ir.maktab127.entity.user.Admin;
import jakarta.persistence.EntityManager;

public class AdminRepositoryImpl extends UserRepositoryImpl<Admin> implements AdminRepository {
    protected AdminRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Admin> getDomainClass() {
        return Admin.class;
    }
}
