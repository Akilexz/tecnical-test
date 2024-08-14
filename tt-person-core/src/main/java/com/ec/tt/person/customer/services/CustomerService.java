package com.ec.tt.person.customer.services;

import com.ec.tt.account.vo.common.Status;
import com.ec.tt.account.vo.customer.CreateCustomerVo;
import com.ec.tt.account.vo.customer.FindAllCustomerVo;
import com.ec.tt.account.vo.customer.FindCustomerByIdVo;
import com.ec.tt.account.vo.customer.UpdateCustomerVo;
import com.ec.tt.person.customer.repositories.ICustomerRepository;
import com.ec.tt.person.entities.CustomerEntity;
import com.ec.tt.person.entities.PersonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Service for customer resources
 *
 * @author Joel Castro
 * @version 1.0
 */
@Lazy
@Service
@Transactional
public class CustomerService implements ICustomerService {
    @Lazy
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public List<FindAllCustomerVo> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void createEntity(CustomerEntity customerEntity) {
        customerRepository.save(customerEntity);
    }

    @Override
    public void create(CreateCustomerVo customerVo) {
        createEntity(CustomerEntity.builder()
                .password(customerVo.getPassword())
                .person(customerVo.getPersonId() == null ? null : PersonEntity.builder().id(customerVo.getPersonId()).build())
                .status(Status.ACTIVE.value)
                .build());
    }

    @Override
    public void updateEntity(CustomerEntity customerEntity) {
        customerRepository.update(customerEntity);
    }

    @Override
    public void update(UpdateCustomerVo customerVo) {
        customerRepository.updateCustomer(CustomerEntity.builder()
                .customerId(customerVo.getCustomerId())
                .password(customerVo.getPassword())
                .person(customerVo.getPersonId() == null ? null : PersonEntity.builder().id(customerVo.getPersonId()).build())
                .build());
    }

    @Override
    public void delete(Long customerId) {
        customerRepository.deleteCustomer(customerId);
    }

    @Override
    public FindCustomerByIdVo findById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(()-> new EntityNotFoundException(
                "No se encontro resultados."));
    }
}
