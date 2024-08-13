package com.ec.tt.transaction.repositories;

import com.ec.tt.account.repositories.IAccountRepository;
import com.ec.tt.account.vo.common.Status;
import com.ec.tt.account.vo.transaction.FindAllBankTransactionVo;
import com.ec.tt.account.vo.transaction.FindReportVo;
import com.ec.tt.bank.entities.AccountEntity;
import com.ec.tt.bank.entities.BankTransactionEntity;
import com.ec.tt.bank.entities.QAccountEntity;
import com.ec.tt.common.repositories.JPAQueryDslBaseRepository;
import com.ec.tt.person.entities.QCustomerEntity;
import com.ec.tt.person.entities.QPersonEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAUpdateClause;
import com.querydsl.sql.SQLExpressions;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ec.tt.bank.entities.QAccountEntity.accountEntity;
import static com.ec.tt.bank.entities.QBankTransactionEntity.bankTransactionEntity;

/**
 * Repository for bank transactional resources
 *
 * @author Joel Castro
 * @version 1.0
 */
@Lazy
@Repository
public class BankTransactionRepository extends JPAQueryDslBaseRepository<BankTransactionEntity> implements IBankTransactionRepository {
    public BankTransactionRepository() {
        super(BankTransactionEntity.class);
    }

    @Override
    public List<FindAllBankTransactionVo> findAll() {
        QAccountEntity accountEntity = QAccountEntity.accountEntity;
        return from(bankTransactionEntity).select(Projections.bean(FindAllBankTransactionVo.class,
                        bankTransactionEntity.id,
                        bankTransactionEntity.amount,
                        bankTransactionEntity.transactionType,
                        bankTransactionEntity.balance,
                        bankTransactionEntity.date,
                        accountEntity.id.as("accountId")
                ))
                .leftJoin(bankTransactionEntity.account, accountEntity).on(accountEntity.status.eq(Status.ACTIVE.value))
                .where(bankTransactionEntity.status.eq(Status.ACTIVE.value))
                .stream().collect(Collectors.toList());
    }

    @Override
    public void updateBankTransaction(BankTransactionEntity entity) {
        JPAUpdateClause updateClause = new JPAUpdateClause(Objects.requireNonNull(this.getEntityManager()), bankTransactionEntity);
        BooleanBuilder where = new BooleanBuilder();
        where.and(bankTransactionEntity.id.eq(entity.getId()));
        where.and(bankTransactionEntity.status.eq(Status.ACTIVE.value));
        updateClause.where(where);
        boolean isChange = updateIfNotNull(updateClause, bankTransactionEntity.transactionType, entity.getTransactionType(), "transactionType");
        isChange |= updateIfNotNull(updateClause, bankTransactionEntity.amount, entity.getAmount(), "amount");
        isChange |= updateIfNotNull(updateClause, bankTransactionEntity.date, entity.getDate(), "date");
        isChange |= updateIfNotNull(updateClause, bankTransactionEntity.balance, entity.getBalance(), "balance");
        isChange |= updateIfNotNull(updateClause, bankTransactionEntity.account, entity.getAccount(), "account");
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
    public void deleteAccount(Long bankTransactionId) {
        JPAUpdateClause updateClause = new JPAUpdateClause(Objects.requireNonNull(this.getEntityManager()), bankTransactionEntity);
        BooleanBuilder where = new BooleanBuilder();
        where.and(bankTransactionEntity.id.eq(bankTransactionId));
        where.and(bankTransactionEntity.status.eq(Status.ACTIVE.value));
        updateClause.where(where);
        updateClause.set(bankTransactionEntity.status, Status.INACTIVE.value);
    }

    @Override
    public List<FindReportVo> findReport(Date initialDate, Date endDate) {
        QCustomerEntity customer = QCustomerEntity.customerEntity;
        QPersonEntity person = QPersonEntity.personEntity;
        QAccountEntity account = new QAccountEntity("accountEntity");
        return from(bankTransactionEntity).select(Projections.bean(FindReportVo.class,
                        bankTransactionEntity.date,
                        person.name,
                        account.accountNumber,
                        account.accountType,
                        account.initialBalance,
                        new CaseBuilder()
                                .when(account.status.eq(Status.ACTIVE.value)).then(true).otherwise(false).as("status"),
                        bankTransactionEntity.amount,
                        bankTransactionEntity.balance.as("availableBalance")
                ))
                .innerJoin(bankTransactionEntity.account, account).on(account.status.eq(Status.ACTIVE.value))
                .innerJoin(account.customer, customer).on(customer.status.eq(Status.ACTIVE.value))
                .innerJoin(customer.person, person).on(person.status.eq(Status.ACTIVE.value))
                .where(bankTransactionEntity.status.eq(Status.ACTIVE.value))
                .where(bankTransactionEntity.date.between(initialDate, endDate))
                .stream().collect(Collectors.toList());
    }
}
