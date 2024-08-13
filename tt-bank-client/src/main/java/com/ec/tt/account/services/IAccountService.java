package com.ec.tt.account.services;

import com.ec.tt.account.vo.account.CreateAccountVo;
import com.ec.tt.account.vo.account.FindAllAccountVo;
import com.ec.tt.account.vo.account.UpdateAccountVo;
import com.ec.tt.bank.entities.AccountEntity;

import java.util.List;

/**
 * Service interface for account resources
 *
 * @author Joel Castro
 * @version 1.0
 */
public interface IAccountService {
    /**
     * find all account
     *
     * @return PersonCategoryVo[]
     */
    List<FindAllAccountVo> findAll();

    /**
     * create account entity
     *
     * @param accountEntity AccountEntity
     */
    void createEntity(AccountEntity accountEntity);

    /**
     * create account
     *
     * @param accountVo CreateAccountVo
     */
    void create(CreateAccountVo accountVo);

    /**
     * update account entity
     *
     * @param accountEntity CreateAccountVo
     */
    void updateEntity(AccountEntity accountEntity);

    /**
     * update account
     *
     * @param accountVo UpdateAccountVo
     */
    void update(UpdateAccountVo accountVo);

    /**
     * delete account
     *
     * @param accountId Long
     */
    void delete(Long accountId);

    /**
     * verify if exist money
     *
     * @param accountId Long
     */
    AccountEntity checkIfThereIsMoneyRequired(Long accountId, Integer amount);

    /**
     * find by id
     *
     * @param id Long
     */
    AccountEntity findById(Long id);
}
