package com.ec.tt.transaction.services;

import com.ec.tt.account.services.IAccountService;
import com.ec.tt.account.vo.account.UpdateAccountVo;
import com.ec.tt.account.vo.common.Status;
import com.ec.tt.account.vo.transaction.CreateBankTransactionVo;
import com.ec.tt.account.vo.transaction.FindAllBankTransactionVo;
import com.ec.tt.account.vo.transaction.FindReportVo;
import com.ec.tt.account.vo.transaction.UpdateBankTransactionVo;
import com.ec.tt.bank.entities.AccountEntity;
import com.ec.tt.bank.entities.BankTransactionEntity;
import com.ec.tt.transaction.repositories.IBankTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for bank transaction resources
 *
 * @author Joel Castro
 * @version 1.0
 */
@Lazy
@Service
@Transactional
public class BankTransactionService implements IBankTransactionService {
    @Lazy
    @Autowired
    private IBankTransactionRepository bankTransactionRepository;

    @Lazy
    @Autowired
    private IAccountService accountService;

    @Override
    public List<FindAllBankTransactionVo> findAll() {
        return bankTransactionRepository.findAll();
    }

    @Override
    public void createEntity(BankTransactionEntity bankTransactionEntity) {
        bankTransactionRepository.save(bankTransactionEntity);
    }

    @Override
    public void create(CreateBankTransactionVo createBankTransactionVo) {
        AccountEntity account = accountService.checkIfThereIsMoneyRequired(createBankTransactionVo.getAccountId(),
                createBankTransactionVo.getAmount());
        Integer initialAmount = accountService.findById(
                createBankTransactionVo.getAccountId()).getInitialBalance();
        createEntity(BankTransactionEntity.builder()
                .date(createBankTransactionVo.getDate())
                .transactionType(createBankTransactionVo.getTransactionType())
                .amount(createBankTransactionVo.getAmount())
                .balance(createBankTransactionVo.getAmount() < 0 ? initialAmount - (
                        createBankTransactionVo.getAmount() * (-1)) : initialAmount +
                        createBankTransactionVo.getAmount())
                .account(account)
                .status(Status.ACTIVE.value)
                .build());
        accountService.update(UpdateAccountVo.builder()
                        .id(createBankTransactionVo.getAccountId())
                        .initialBalance(createBankTransactionVo.getAmount() < 0 ? initialAmount - (
                                        createBankTransactionVo.getAmount() * (-1)) : initialAmount +
                                        createBankTransactionVo.getAmount())
                .build());
    }

    @Override
    public void updateEntity(BankTransactionEntity bankTransactionEntity) {
        bankTransactionRepository.update(bankTransactionEntity);
    }

    @Override
    public void update(UpdateBankTransactionVo updateBankTransactionVo) {
        bankTransactionRepository.updateBankTransaction(BankTransactionEntity.builder()
                        .balance(updateBankTransactionVo.getBalance())
                        .amount(updateBankTransactionVo.getAmount())
                        .date(updateBankTransactionVo.getDate())
                        .transactionType(updateBankTransactionVo.getTransactionType())
                        .account(updateBankTransactionVo.getBankTransactionId() == null ? null : AccountEntity.builder()
                                .id(updateBankTransactionVo.getAccountId()).build())
                .build());
    }

    @Override
    public void delete(Long bankTransactionId) {
        bankTransactionRepository.deleteAccount(bankTransactionId);
    }

    @Override
    public List<FindReportVo> findReport(Date initialDate, Date endDate){
        return bankTransactionRepository.findReport(initialDate, endDate);
    }
}
