package com.ec.tt.account.vo.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBankTransactionVo {
    private Date date;
    private String transactionType;
    private Integer amount;
    private Integer balance;
    private Long accountId;
}
