package com.ec.tt.person.customer.services;

import com.ec.tt.account.vo.customer.CreateCustomerVo;
import com.ec.tt.account.vo.customer.FindAllCustomerVo;
import com.ec.tt.account.vo.customer.UpdateCustomerVo;
import com.ec.tt.person.entities.CustomerEntity;

import java.util.List;

/**
 * Service interface for customer resources
 *
 * @author Joel Castro
 * @version 1.0
 */
public interface ICustomerService {
    /**
     * find all customer
     *
     * @return FindAllCustomerVo[]
     */
    List<FindAllCustomerVo> findAll();

    /**
     * create customer entity
     *
     * @param customerEntity AccountEntity
     */
    void createEntity(CustomerEntity customerEntity);

    /**
     * create customer
     *
     * @param customerVo CreateCustomerVo
     */
    void create(CreateCustomerVo customerVo);

    /**
     * update account entity
     *
     * @param customerEntity CustomerEntity
     */
    void updateEntity(CustomerEntity customerEntity);

    /**
     * update account
     *
     * @param customerVo UpdateCustomerVo
     */
    void update(UpdateCustomerVo customerVo);

    /**
     * delete account
     *
     * @param customerId Long
     */
    void delete(Long customerId);
}
