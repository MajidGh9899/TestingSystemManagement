package ir.maktab127.repository.user;

import ir.maktab127.entity.user.Teacher;
import jakarta.persistence.EntityManager;

public  class TeacherRepositoryImpl extends UserRepositoryImpl<Teacher> implements  TeacherRepository {

    public TeacherRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    protected Class<Teacher> getDomainClass() {
        return Teacher.class;
    }

}
