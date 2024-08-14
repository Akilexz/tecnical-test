package com.ec.tt.account.services;

import com.ec.tt.account.repositories.IAccountRepository;
import com.ec.tt.account.vo.account.CreateAccountVo;
import com.ec.tt.account.vo.account.FindAllAccountVo;
import com.ec.tt.account.vo.account.UpdateAccountVo;
import com.ec.tt.account.vo.common.Status;
import com.ec.tt.bank.entities.AccountEntity;
import com.ec.tt.person.entities.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Service for account resources
 *
 * @author Joel Castro
 * @version 1.0
 */
@Lazy
@Service
@Transactional
public class AccountService implements IAccountService {
    @Lazy
    @Autowired
    private IAccountRepository accountRepository;

    @Override
    public List<FindAllAccountVo> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public void createEntity(AccountEntity accountEntity) {
        accountRepository.save(accountEntity);
    }

    @Override
    public void create(CreateAccountVo accountVo) {
        createEntity(AccountEntity.builder()
                .accountNumber(accountVo.getAccountNumber())
                .accountType(accountVo.getAccountType())
                .initialBalance(accountVo.getInitialBalance())
                .customer(accountVo.getCustomerId() == null ? null : CustomerEntity.builder().customerId(
                        accountVo.getCustomerId()).build())
                .status(Status.ACTIVE.value)
                .build());
    }

    @Override
    public void updateEntity(AccountEntity accountEntity) {
        accountRepository.update(accountEntity);
    }

    @Override
    public void update(UpdateAccountVo accountVo) {
        accountRepository.updateAccount(AccountEntity.builder()
                .id(accountVo.getId())
                .accountNumber(accountVo.getAccountNumber())
                .accountType(accountVo.getAccountType())
                .initialBalance(accountVo.getInitialBalance())
                .customer(accountVo.getCustomerId() == null ? null : CustomerEntity.builder().customerId(
                        accountVo.getCustomerId()).build())
                .build());
    }

    @Override
    public void delete(Long accountId) {
        accountRepository.deleteAccount(accountId);
    }

    @Override
    public AccountEntity checkIfThereIsMoneyRequired(Long accountId, Integer amount) {
        return accountRepository.checkIfThereIsMoneyRequired(accountId, amount).orElseThrow(() -> new EntityNotFoundException("Saldo no disponible"));
    }

    @Override
    public AccountEntity findById(Long accountId) {
        return accountRepository.findByAccountId(accountId).orElseThrow(() -> new EntityNotFoundException("No se encontro el account"));
    }
}
