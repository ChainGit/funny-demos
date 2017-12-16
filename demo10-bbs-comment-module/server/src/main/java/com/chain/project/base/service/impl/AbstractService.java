package com.chain.project.base.service.impl;

import com.chain.project.base.mapper.BaseMapper;
import com.chain.project.base.service.BaseService;
import com.chain.project.common.exception.ChainProjectRuntimeException;

import java.io.Serializable;

public class AbstractService<T, ID extends Serializable> implements BaseService<T, ID> {

    private BaseMapper<T, ID> baseMapper;

    public void setBaseMapper(BaseMapper<T, ID> baseMapper) {
        this.baseMapper = baseMapper;
    }

    //增删改需要捕获异常并抛出

    @Override
    public int insert(T t) {
        try {
            return baseMapper.insert(t);
        } catch (Exception e) {
            throw new ChainProjectRuntimeException(e);
        }
    }

    @Override
    public int deleteById(Long id) {
        try {
            return baseMapper.deleteById(id);
        } catch (Exception e) {
            throw new ChainProjectRuntimeException(e);
        }
    }

    @Override
    public int update(T t) {
        try {
            return baseMapper.update(t);
        } catch (Exception e) {
            throw new ChainProjectRuntimeException(e);
        }
    }

    @Override
    public T findById(Long id) {
        return baseMapper.findById(id);
    }

}