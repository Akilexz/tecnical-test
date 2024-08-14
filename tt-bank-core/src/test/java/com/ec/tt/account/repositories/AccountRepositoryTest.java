package com.ec.tt.account.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountRepositoryTest {
    @Mock
    private AccountRepository accountRepository;

    @Test
    void isNegative(){
        when(accountRepository.isNegative(-1)).thenReturn(true);
        when(accountRepository.isNegative(0)).thenReturn(false);
        Boolean isNegative = accountRepository.isNegative(-1);
        Boolean isPositive = accountRepository.isNegative(0);
        assertEquals(true, isNegative);
        assertEquals(false, isPositive);
        verify(accountRepository, times(1)).isNegative(-1);
    }
}