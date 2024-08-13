package com.ec.tt.account.vo.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindAllBankTransactionVo {
    private Date date;
    private String transactionType;
    private Integer amount;
    private Integer balance;
    private Long accountId;
}
