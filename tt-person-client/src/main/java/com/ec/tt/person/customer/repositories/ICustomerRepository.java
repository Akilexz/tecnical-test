package com.ec.tt.person.customer.repositories;

import com.ec.tt.account.vo.customer.FindAllCustomerVo;
import com.ec.tt.account.vo.customer.FindCustomerByIdVo;
import com.ec.tt.common.repositories.IQueryDslBaseRepository;
import com.ec.tt.person.entities.CustomerEntity;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for customer resources
 *
 * @author Joel Castro
 * @version 1.0
 */
public interface ICustomerRepository extends IQueryDslBaseRepository<CustomerEntity> {
    /**
     * find all customer
     *
     * @return FindAllCustomerVo[]
     */
    List<FindAllCustomerVo> findAll();

    /**
     * update
     *
     * @param entity CustomerEntity
     */
    void updateCustomer(CustomerEntity entity);

    /**
     * delete
     *
     * @param customerId Long
     */
    void deleteCustomer(Long customerId);

    /**
     * find by id
     *
     * @param customerId Long
     * @return FindCustomerByIdVo
     */
    Optional<FindCustomerByIdVo> findById(Long customerId);
}
