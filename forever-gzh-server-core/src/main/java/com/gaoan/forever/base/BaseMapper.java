package com.gaoan.forever.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.entity.Example;

public interface BaseMapper<T> {

    /**
     * 根据主键查询实体
     * 
     * @param id
     *            主键
     * @return
     */
    public T queryByPrimaryKey(@Param("id") Long id);

    /**
     * queryAllByParams 根据实体查询列表(不分页)
     * 
     * @param entity
     *            实体参数
     * @return
     */
    public List<T> queryAll(T entity);

    /**
     * 根据map参数查询列表(不分页)
     * 
     * @param params
     *            map参数
     * @return
     */
    public List<Map<String, Object>> queryAllByParams(Map<String, Object> params);

    /**
     * 根据map参数查询列表总数
     * 
     * @param params
     *            实体参数
     * @return
     */
    public Long queryCountByParams(Map<String, Object> params);

    /**
     * 根据map参数查询列表(分页)
     * 
     * @param entity
     *            参数
     * @return
     */
    public <T extends BaseEntity> List<T> queryListByParams(T entity);

    /**
     * 根据实体参数查询列表总数
     * 
     * @param entity
     *            实体参数
     * @return
     */
    public Long queryCount(T entity);

    /**
     * 分页查询
     * 
     * @param example
     * @return
     */
    public List<T> selectByExample(Object example);

    /**
     * 删除
     * 
     * @param example
     * @return
     */
    public Integer deleteByExample(Example example);

    // /**
    // * 根据实体参数查询列表(分页)
    // * @param entity 实体参数
    // * @return
    // */
    // public List<T> queryList(T entity);

    /**
     * 删除（根据主键ID删除） @param id 主键ID @return @throws
     */
    public int deleteByPrimaryKey(@Param("id") Long id);

    /**
     * 添加(所有字段) @param entity 实体 @return @throws
     */
    public int insert(T entity);

    /**
     * 添加 （匹配有值的字段） @param entity 实体 @return @throws
     */
    public int insertSelective(T entity);

    /**
     * 修改 （匹配有值的字段） @param entity 实体 @return @throws
     */
    public int updateByPrimaryKeySelective(T entity);

    /**
     * 修改（根据主键ID修改） @param entity @return @throws
     */
    public int updateByPrimaryKey(T entity);

    /**
     * 根据实体条件查询自身
     * 
     * @param entity
     * @return
     */
    T queryInfoByEntity(T entity);

    /**
     * 根据实体条件查询导出数据
     * 
     * @param entity
     * @return
     */
    public <V, E> List<V> queryExportData(E entity);
}
