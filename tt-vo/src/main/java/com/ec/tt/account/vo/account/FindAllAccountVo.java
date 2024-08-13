package com.ec.tt.account.vo.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindAllAccountVo {
    private Long id;
    private Integer accountNumber;
    private String accountType;
    private Integer initialBalance;
    private Long customerId;
}
