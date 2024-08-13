package com.ec.tt.account.vo.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountVo {
    private Integer accountNumber;
    private String accountType;
    private Integer initialBalance;
    private Long customerId;
}
