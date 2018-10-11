package com.company.project.core;


import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class BaseCRUDService<T> implements Service<T> {

    @Autowired
    protected OwnerMapper<T> mapper;

    private Class<T> modelClass;    // 当前泛型真实类型的Class

    public BaseCRUDService() {

        try {
            Type type = this.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                this.modelClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];
            }
        } catch (RuntimeException var2) {
            var2.printStackTrace();
        }
    }
    @Override
    public void save(T model) {
        mapper.insertSelective(model);
    }
    @Override
    public void save(List<T> models) {
        mapper.insertList(models);
    }
    @Override
    public void deleteById(Integer id) {
        mapper.deleteByPrimaryKey(id);
    }
    @Override
    public void deleteByIds(String ids) {
        mapper.deleteByIds(ids);
    }
    @Override
    public void update(T model) {
        mapper.updateByPrimaryKeySelective(model);
    }
    @Override
    public T findById(Integer id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T findOneByFiledName(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    @Override
    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }
    @Override
    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }
    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }
}
