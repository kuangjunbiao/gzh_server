package com.gaoan.forever.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.gaoan.forever.exception.DotnarException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * Created by dotnar on 2017/3/31.
 */
public class BaseService<T> implements IBaseService<T> {

	@Autowired
	protected BaseMapper<T> baseMapper;

	@Override
	public T queryByPrimaryKey(Long id) throws DotnarException {
		try {
			return baseMapper.queryByPrimaryKey(id);
		} catch (Exception e) {
			throw new DotnarException(2, "根据主键查询实体出错", e.getMessage());
		}
	}

	@Override
	public T queryInfoByEntity(T entity) throws DotnarException {
		try {
			return baseMapper.queryInfoByEntity(entity);
		} catch (Exception e) {
			throw new DotnarException(2, "根据实体查询自身出错", e.getMessage());
		}
	}

	@Override
	public List<T> queryAll(T entity) throws DotnarException {
		try {
			return baseMapper.queryAll(entity);
		} catch (Exception e) {
			throw new DotnarException(2, "根据实体查询列表(不分页)出错", e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryAllByParams(Map<String, Object> params) throws DotnarException {
		try {
			return baseMapper.queryAllByParams(params);
		} catch (Exception e) {
			throw new DotnarException(2, "根据map参数查询列表(不分页)出错", e.getMessage());
		}
	}

	@Override
	public Long queryCountByParams(Map<String, Object> params) throws DotnarException {
		try {
			return baseMapper.queryCountByParams(params);
		} catch (Exception e) {
			throw new DotnarException(2, "根据map参数查询列表总数出错", e.getMessage());
		}
	}

	@Override
	public <T extends BaseEntity> PageInfo<T> queryListByParams(T entity) throws DotnarException {
		try {
			// PageHelper.startPage(entity.getPageNumber(),
			// entity.getPageSize(), true);
			// if(!Strings.isNullOrEmpty(entity.getOrderBy())) {
			// PageHelper.orderBy(entity.getOrderBy());
			// }
			return new PageInfo<>(baseMapper.queryListByParams(entity));
		} catch (Exception e) {
			throw new DotnarException(2, "根据map参数查询列表(分页)出错", e.getMessage());
		}
	}

	@Override
	public <T extends BaseEntity> PageInfo<T> queryListByParamsOrder(T entity) throws DotnarException {
		try {
			// PageHelper.startPage(entity.getPageNumber(),
			// entity.getPageSize(), true);
			// if(Strings.isNullOrEmpty(entity.getOrderBy())) {
			// PageHelper.orderBy(DataBaseConstant.ORDER_STRATEGY);
			// }
			return new PageInfo<>(baseMapper.queryListByParams(entity));
		} catch (Exception e) {
			throw new DotnarException(2, "根据map参数查询列表(分页)出错", e.getMessage());
		}
	}

	@Override
	public Long queryCount(T entity) throws DotnarException {
		try {
			return baseMapper.queryCount(entity);
		} catch (Exception e) {
			throw new DotnarException(2, "根据实体参数查询列表总数出错", e.getMessage());
		}
	}

	// @Override
	// public List<T> queryList(T entity)
	// throws DotnarException {
	// try {
	// return baseMapper.queryList(entity);
	// } catch (Exception e) {
	// throw new DotnarException(2, "根据实体参数查询列表(分页)出错",e.getMessage());
	// }
	// }

	@Override
	public int deleteByPrimaryKey(Long id) throws DotnarException {
		try {
			return baseMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			throw new DotnarException(2, "删除（根据主键ID删除）出错", e.getMessage());
		}
	}

	@Override
	public int insert(T entity) throws DotnarException {
		try {
			return baseMapper.insert(entity);
		} catch (Exception e) {
			throw new DotnarException(2, "添加(所有字段)出错", e.getMessage());
		}
	}

	@Override
	public int insertSelective(T entity) throws DotnarException {
		try {
			return baseMapper.insertSelective(entity);
		} catch (Exception e) {
			throw new DotnarException(2, "添加 （匹配有值的字段）出错", e.getMessage());
		}
	}

	@Override
	public int updateByPrimaryKeySelective(T entity) throws DotnarException {
		try {
			return baseMapper.updateByPrimaryKeySelective(entity);
		} catch (Exception e) {
			throw new DotnarException(2, "修改 （匹配有值的字段）出错", e.getMessage());
		}
	}

	@Override
	public int updateByPrimaryKey(T entity) throws DotnarException {
		try {
			return baseMapper.updateByPrimaryKey(entity);
		} catch (Exception e) {
			throw new DotnarException(2, "修改（根据主键ID修改）出错", e.getMessage());
		}
	}

	public List<T> selectByExample(Object example) {

		return baseMapper.selectByExample(example);
	}

	@Override
	public <V, E> PageInfo<V> queryExportData(E entity, int pageNum, int pageSize) throws DotnarException {
		PageHelper.startPage(pageNum, pageSize);
		List<V> list = baseMapper.queryExportData(entity);
		return new PageInfo<V>(list);
	}

	/**
	 * 校验是否有导出文件的权限及数据
	 * 
	 * @param map
	 */
	@Override
	public void checkPermissionAndData(Map<String, String> map) {

	}
}
