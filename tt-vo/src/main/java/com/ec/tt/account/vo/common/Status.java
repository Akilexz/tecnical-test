package com.ec.tt.account.vo.common;

/**
 * Enums for status
 *
 * @author Joel Castro
 * @version 1.0
 */
public enum Status {
    INACTIVE("0"), ACTIVE("1");

    public final String value;

    Status(String value) {
        this.value = value;
    }
}
