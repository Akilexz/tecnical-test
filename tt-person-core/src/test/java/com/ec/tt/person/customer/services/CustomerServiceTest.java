package com.ec.tt.person.customer.services;


import com.ec.tt.account.vo.customer.FindCustomerByIdVo;
import com.ec.tt.person.customer.repositories.ICustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private ICustomerRepository customerRepository;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void findById() {
        FindCustomerByIdVo customer = FindCustomerByIdVo.builder().customerId(1L).password("12345").personId(1L).build();

        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));

        FindCustomerByIdVo customerFound = customerService.findById(1L);

        assertEquals(1L, customerFound.getPersonId());
        assertEquals("12345", customerFound.getPassword());
        assertEquals(1L, customerFound.getPersonId());
        verify(customerRepository, times(1)).findById(1L);
    }
}