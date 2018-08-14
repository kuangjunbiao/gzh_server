package com.gaoan.forever.base;

import java.util.List;
import java.util.Map;

import com.gaoan.forever.exception.DotnarException;
import com.github.pagehelper.PageInfo;

public interface IBaseService<T> {

    /**
     * 根据主键查询实体
     * 
     * @param id
     *            主键
     * @return
     * @throws DotnarException
     */
    public T queryByPrimaryKey(Long id) throws DotnarException;

    /**
     * 根据实体条件查询自身
     * 
     * @param entity
     * @return
     */
    public T queryInfoByEntity(T entity) throws DotnarException;

    /**
     * 根据实体查询列表(不分页)
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
    public List<Map<String, Object>> queryAllByParams(Map<String, Object> params) throws DotnarException;

    /**
     * 分页查询
     * 
     * @param example
     * @return
     * @throws DotnarException
     */
    public List<T> selectByExample(Object example) throws DotnarException;

    /**
     * 根据实体参数查询列表总数
     * 
     * @param params
     *            查询参数
     * @return
     */
    public Long queryCountByParams(Map<String, Object> params) throws DotnarException;

    /**
     * 根据实体参数查询列表(分页)
     * 
     * @param entity
     *            查询参数
     * @return
     */
    public <T extends BaseEntity> PageInfo<T> queryListByParams(T entity) throws DotnarException;

    /**
     * 根据实体参数查询列表(分页、排序)
     * 
     * @param entity
     *            查询参数
     * @return
     */
    public <T extends BaseEntity> PageInfo<T> queryListByParamsOrder(T entity) throws DotnarException;

    /**
     * 根据实体参数查询列表总数
     * 
     * @param entity
     *            实体参数
     * @return
     */
    public Long queryCount(T entity) throws DotnarException;

    // /**
    // * 根据实体参数查询列表(分页)
    // * @param entity 实体参数
    // * @return
    // */
    // public Page<T> queryList(T entity) throws DotnarException;

    /**
     * 删除（根据主键ID删除）
     * 
     * @param id
     *            主键ID
     * @return
     * @throws DotnarException
     */
    public int deleteByPrimaryKey(Long id) throws DotnarException;

    /**
     * 添加(所有字段)
     * 
     * @param entity
     *            实体
     * @return
     * @throws DotnarException
     */
    public int insert(T entity) throws DotnarException;

    /**
     * 添加 （匹配有值的字段）
     * 
     * @param entity
     *            实体
     * @return
     * @throws DotnarException
     */
    public int insertSelective(T entity) throws DotnarException;

    /**
     * 修改 （匹配有值的字段）
     * 
     * @param entity
     *            实体
     * @return
     * @throws DotnarException
     */
    public int updateByPrimaryKeySelective(T entity) throws DotnarException;

    /**
     * 修改（根据主键ID修改）
     * 
     * @param entity
     * @return
     * @throws DotnarException
     */
    public int updateByPrimaryKey(T entity) throws DotnarException;

    /**
     * 查询需要导出的数据
     * 
     * @param entity
     * @throws DotnarException
     */
    public <V, E> PageInfo<V> queryExportData(E entity, int pageNum, int pageSize) throws DotnarException;

    /**
     * 校验是否有导出文件的权限及数据
     * 
     * @param map
     */
    public void checkPermissionAndData(Map<String, String> map);
}
