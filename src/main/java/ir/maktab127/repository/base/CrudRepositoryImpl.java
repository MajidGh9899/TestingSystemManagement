package ir.maktab127.repository.base;


import ir.maktab127.entity.base.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


//@RequiredArgsConstructor
public abstract class CrudRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable> implements CrudRepository<T, ID> {

    protected final EntityManager entityManager;

    protected Class<T> domainClass;

    protected CrudRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
//        TODO use reflection and get Domain class this.domainClass = ...
    }

    @Override
    public T save(T entity) {
//        begin transaction
        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }
//        commit transaction
        return entity;
    }

    @Override
    public List<T> saveAll(Collection<T> entities) {
        List<T> savedEntities = new ArrayList<>();
        for (T entity : entities) {
            savedEntities.add(
                    save(entity)
            );
        }
        return savedEntities;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(
                entityManager.find(
                        getDomainClass(),
                        id
                )
        );
    }

    protected abstract Class<T> getDomainClass();

    @Override
    public List<T> findAll() {
//        return entityManager.createQuery(
//                "from " + getDomainClass().getSimpleName(),
//                getDomainClass()
//        ).getResultList();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getDomainClass());
        Root<T> from = query.from(getDomainClass());
        query.select(from);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
//        List<T> entities = new ArrayList<>();
//        ids.forEach(id -> findById(id).ifPresent(entities::add));
//        return entities;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getDomainClass());
        Root<T> from = query.from(getDomainClass());
        query.select(from);

        query.where(
                cb.in(from.get(BaseEntity_.ID)).value(ids)
        );

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public long countAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> from = query.from(getDomainClass());
        query.select(cb.count(from));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public void deleteById(ID id) {
        findById(id).ifPresent(entityManager::remove);
    }

    @Override
    public void deleteAllById(Iterable<ID> ids) {
        ids.forEach(this::deleteById);
    }

    @Override
    public boolean existsById(ID id) {
//        return findById(id).isPresent();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> from = query.from(getDomainClass());
        query.select(cb.count(from));
        query.where(
                cb.equal(from.get(BaseEntity_.ID), id)
        );
        return entityManager.createQuery(query).getSingleResult() > 0;

//        try {
//            T reference = entityManager.getReference(getDomainClass(), id);
//            return true;
//        } catch (EntityNotFoundException e) {
//            return false;
//        }
    }

    @Override
    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    @Override
    public void commitTransaction() {
        entityManager.getTransaction().commit();
    }

    @Override
    public void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }
}
