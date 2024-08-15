package com.ec.tt.person.customer.repositories;

import com.ec.tt.account.vo.common.Status;
import com.ec.tt.account.vo.customer.FindAllCustomerVo;
import com.ec.tt.account.vo.customer.FindCustomerByIdVo;
import com.ec.tt.common.repositories.JPAQueryDslBaseRepository;
import com.ec.tt.person.entities.CustomerEntity;
import com.ec.tt.person.entities.QPersonEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ec.tt.person.entities.QCustomerEntity.customerEntity;

/**
 * Repository for customer resources
 *
 * @author Joel Castro
 * @version 1.0
 */
@Lazy
@Repository
public class CustomerRepository extends JPAQueryDslBaseRepository<CustomerEntity> implements ICustomerRepository {
    public CustomerRepository() {
        super(CustomerEntity.class);
    }

    @Override
    public List<FindAllCustomerVo> findAll() {
        QPersonEntity personEntity = QPersonEntity.personEntity;
        return from(customerEntity).select(Projections.bean(FindAllCustomerVo.class,
                        customerEntity.customerId,
                        customerEntity.password,
                        personEntity.id.as("personId")
                ))
                .leftJoin(customerEntity.person, personEntity).on(personEntity.status.eq(Status.ACTIVE.value))
                .where(customerEntity.status.eq(Status.ACTIVE.value))
                .stream().collect(Collectors.toList());
    }

    @Override
    public void updateCustomer(CustomerEntity entity) {
        JPAUpdateClause updateClause = new JPAUpdateClause(Objects.requireNonNull(this.getEntityManager()), customerEntity);
        BooleanBuilder where = new BooleanBuilder();
        where.and(customerEntity.customerId.eq(entity.getCustomerId()));
        where.and(customerEntity.status.eq(Status.ACTIVE.value));
        updateClause.where(where);
        boolean isChange = updateIfNotNull(updateClause, customerEntity.password, entity.getPassword(), "password");
        isChange |= updateIfNotNull(updateClause, customerEntity.person, entity.getPerson() == null ? null : entity.getPerson(), "person");
        if (isChange) {
            updateClause.execute();
        }
    }

    private <T> boolean updateIfNotNull(JPAUpdateClause updateClause, Path<T> property, T value, String logMessage) {
        if (value != null) {
            updateClause.set(property, value);
            return true;
        }
        System.out.println("no se presento cambios");
        return false;
    }

    @Override
    public void deleteCustomer(Long customerId) {
        JPAUpdateClause updateClause = new JPAUpdateClause(Objects.requireNonNull(this.getEntityManager()), customerEntity);
        BooleanBuilder where = new BooleanBuilder();
        where.and(customerEntity.customerId.eq(customerId));
        where.and(customerEntity.status.eq(Status.ACTIVE.value));
        updateClause.where(where);
        updateClause.set(customerEntity.status, Status.INACTIVE.value);
        updateClause.execute();
    }

    @Override
    public Optional<FindCustomerByIdVo> findById(Long customerId) {
        QPersonEntity personEntity = QPersonEntity.personEntity;
        return from(customerEntity).select(Projections.bean(FindCustomerByIdVo.class,
                        customerEntity.customerId,
                        customerEntity.password,
                        personEntity.id.as("personId")
                ))
                .leftJoin(customerEntity.person, personEntity).on(personEntity.status.eq(Status.ACTIVE.value))
                .where(customerEntity.status.eq(Status.ACTIVE.value))
                .where(customerEntity.customerId.eq(customerId))
                .stream().findFirst();
    }
}
