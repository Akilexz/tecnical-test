package com.ec.tt.account.vo.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBankTransactionVo extends CreateBankTransactionVo {
    private Long bankTransactionId;
}
