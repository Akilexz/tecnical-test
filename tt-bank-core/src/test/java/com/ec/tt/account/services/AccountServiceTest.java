package com.ec.tt.account.services;

import com.ec.tt.account.repositories.IAccountRepository;
import com.ec.tt.bank.entities.AccountEntity;
import com.ec.tt.person.entities.CustomerEntity;
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
class AccountServiceTest {
    @Mock
    private IAccountRepository accountRepository;
    @InjectMocks
    private AccountService accountService;

    @Test
    void findById() {
        AccountEntity account = AccountEntity.builder()
                .id(1L).accountNumber(123).accountType("Corriente").initialBalance(100).status("1")
                .customer(CustomerEntity.builder().customerId(1L).build())
                .build();

        when(accountRepository.findByAccountId(1L)).thenReturn(Optional.ofNullable(account));

        AccountEntity accountFound = accountService.findById(1L);

        assertEquals(1L, accountFound.getId());
        assertEquals("Corriente", accountFound.getAccountType());
        assertEquals(123, accountFound.getAccountNumber());
        assertEquals(100, accountFound.getInitialBalance());
        assertEquals("1", accountFound.getStatus());
        assertEquals(CustomerEntity.builder().customerId(1L).build(), accountFound.getCustomer());
        verify(accountRepository, times(1)).findByAccountId(1L);
    }
}