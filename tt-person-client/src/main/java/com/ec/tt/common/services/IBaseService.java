package com.ec.tt.common.services;

public interface IBaseService<T> {
    void save(T obj);

    void update(T obj);
}
