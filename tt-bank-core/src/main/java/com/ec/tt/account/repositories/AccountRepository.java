package com.ec.tt.account.repositories;

import com.ec.tt.account.vo.account.FindAllAccountVo;
import com.ec.tt.account.vo.common.Status;
import com.ec.tt.bank.entities.AccountEntity;
import com.ec.tt.common.repositories.JPAQueryDslBaseRepository;
import com.ec.tt.person.entities.QCustomerEntity;
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

import static com.ec.tt.bank.entities.QAccountEntity.accountEntity;

/**
 * Repository for account resources
 *
 * @author Joel Castro
 * @version 1.0
 */
@Lazy
@Repository
public class AccountRepository extends JPAQueryDslBaseRepository<AccountEntity> implements IAccountRepository {
    public AccountRepository() {
        super(AccountEntity.class);
    }

    @Override
    public List<FindAllAccountVo> findAll() {
        QCustomerEntity customer = QCustomerEntity.customerEntity;
        return from(accountEntity).select(Projections.bean(FindAllAccountVo.class,
                        accountEntity.id,
                        accountEntity.accountNumber,
                        accountEntity.accountType,
                        accountEntity.initialBalance,
                        customer.customerId
                ))
                .leftJoin(accountEntity.customer, customer).on(customer.status.eq(Status.ACTIVE.value))
                .where(accountEntity.status.eq(Status.ACTIVE.value))
                .stream().collect(Collectors.toList());
    }

    @Override
    public void updateAccount(AccountEntity entity) {
        JPAUpdateClause updateClause = new JPAUpdateClause(Objects.requireNonNull(this.getEntityManager()), accountEntity);
        BooleanBuilder where = new BooleanBuilder();
        where.and(accountEntity.id.eq(entity.getId()));
        where.and(accountEntity.status.eq(Status.ACTIVE.value));
        updateClause.where(where);
//        updateClause.set(accountEntity.status, Status.INACTIVE.value);
        boolean isChange = updateIfNotNull(updateClause, accountEntity.accountNumber, entity.getAccountNumber(), "accountNumber");
        isChange |= updateIfNotNull(updateClause, accountEntity.accountType, entity.getAccountType(), "accountType");
        isChange |= updateIfNotNull(updateClause, accountEntity.initialBalance, entity.getInitialBalance(), "initialBalance");
        if (isChange) {
            updateClause.execute();
        }
    }

    private <T> boolean updateIfNotNull(JPAUpdateClause updateClause, Path<T> property, T value, String logMessage) {
        if (value != null) {
            updateClause.set(property, value);
            return true;
        }
        System.out.println("no se presento cambios: " + logMessage);
        return false;
    }

    @Override
    public void deleteAccount(Long accountId) {
        JPAUpdateClause updateClause = new JPAUpdateClause(Objects.requireNonNull(this.getEntityManager()), accountEntity);
        BooleanBuilder where = new BooleanBuilder();
        where.and(accountEntity.id.eq(accountId));
        where.and(accountEntity.status.eq(Status.ACTIVE.value));
        updateClause.where(where);
        updateClause.set(accountEntity.status, Status.INACTIVE.value);
        updateClause.execute();
    }

    @Override
    public Optional<AccountEntity> checkIfThereIsMoneyRequired(Long accountId, Integer amount) {
        BooleanBuilder where = new BooleanBuilder();
        if(isNegative(amount)){
            where.and(accountEntity.initialBalance.goe(amount * (-1)));
        }
        return from(accountEntity).select(Projections.bean(AccountEntity.class,
                        accountEntity.id
                ))
                .where(accountEntity.status.eq(Status.ACTIVE.value))
                .where(accountEntity.id.eq(accountId))
                .where(where)
                .stream().findFirst();
    }

    @Override
    public Optional<AccountEntity> findByAccountId(Long accountId) {
        return from(accountEntity).select(Projections.bean(AccountEntity.class,
                        accountEntity.id,
                        accountEntity.initialBalance
                ))
                .where(accountEntity.status.eq(Status.ACTIVE.value))
                .where(accountEntity.id.eq(accountId))
                .stream().findFirst();
    }

    public boolean isNegative(Integer number) {
        return number < 0;
    }
}
