package com.ec.tt.account.vo.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindAllCustomerVo {
    private Long customerId;
    private Long personId;
    private String password;
}
