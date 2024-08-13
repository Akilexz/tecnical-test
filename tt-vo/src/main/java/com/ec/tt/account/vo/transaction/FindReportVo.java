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
public class FindReportVo {
    private Date date;
    private String name;
    private Integer accountNumber;
    private String accountType;
    private Integer initialBalance;
    private Boolean status;
    private Integer amount;
    private Integer availableBalance;
}
