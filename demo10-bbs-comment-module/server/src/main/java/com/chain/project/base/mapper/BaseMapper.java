package com.chain.project.base.mapper;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

//其他Mapper继承这个接口，已实现druid监控
public interface BaseMapper<T, ID extends Serializable> {
    //提供增删查方法

    int insert(T t);

    int deleteById(@Param("id") Long id);

    int update(T t);

    T findById(@Param("id") Long id);

}
