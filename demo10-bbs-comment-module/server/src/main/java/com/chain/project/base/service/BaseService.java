package com.chain.project.base.service;

import java.io.Serializable;

public interface BaseService<T, ID extends Serializable> {

    int insert(T t);

    int deleteById(Long id);

    int update(T t);

    T findById(Long id);

}