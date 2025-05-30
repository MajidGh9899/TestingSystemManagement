package ir.maktab127.service.base;

import ir.maktab127.entity.base.BaseEntity;
import ir.maktab127.repository.base.CrudRepository;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BaseServiceImpl<
        T extends BaseEntity<ID>,
        ID extends Serializable,
        R extends CrudRepository<T, ID>
        >
        implements BaseService<T, ID> {

    protected final R repository;

    @Override
    public T save(T entity) {
        repository.beginTransaction();
        entity = repository.save(entity);
        repository.commitTransaction();
        return entity;
    }

    @Override
    public List<T> saveAll(Collection<T> entities) {
        repository.beginTransaction();
        List<T> savedEntities = repository.saveAll(entities);
        repository.commitTransaction();
        return savedEntities;
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        return repository.findAllById(ids);
    }

    @Override
    public long countAll() {
        return repository.countAll();
    }

    @Override
    public void deleteById(ID id) {
        repository.beginTransaction();
        repository.deleteById(id);
        repository.commitTransaction();
    }

    @Override
    public void deleteAllById(Iterable<ID> ids) {
        repository.beginTransaction();
        repository.deleteAllById(ids);
        repository.commitTransaction();
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }
}