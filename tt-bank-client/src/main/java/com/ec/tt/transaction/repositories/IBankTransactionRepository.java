package com.ec.tt.transaction.repositories;

import com.ec.tt.account.vo.account.FindAllAccountVo;
import com.ec.tt.account.vo.transaction.FindAllBankTransactionVo;
import com.ec.tt.account.vo.transaction.FindReportVo;
import com.ec.tt.bank.entities.AccountEntity;
import com.ec.tt.bank.entities.BankTransactionEntity;
import com.ec.tt.common.repositories.IQueryDslBaseRepository;

import java.util.Date;
import java.util.List;

/**
 * Repository interface for bank transaction resources
 *
 * @author Joel Castro
 * @version 1.0
 */
public interface IBankTransactionRepository extends IQueryDslBaseRepository<BankTransactionEntity> {
    /**
     * find all bank transaction
     *
     * @return []
     */
    List<FindAllBankTransactionVo> findAll();

    /**
     * update
     *
     * @param entity BankTransactionEntity
     */
    void updateBankTransaction(BankTransactionEntity entity);

    /**
     * delete
     *
     * @param bankTransactionId Long
     */
    void deleteAccount(Long bankTransactionId);

    /**
     * find report
     *
     * @param initialDate Date
     * @param endDate Date
     */
    List<FindReportVo> findReport(Date initialDate, Date endDate);
}
