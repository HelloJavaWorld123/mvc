package com.jzy.api.dao.auth;

import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.api.model.auth.SysEmp;
import com.jzy.api.model.sys.Emp;
import com.jzy.api.vo.auth.SysEmpVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

/**
 * Author : RXK
 * Date : 2019/5/30 14:02
 * Version: V1.0.0
 * Desc: 用户信息相关方法
 **/
public interface SysEmpMapper {
	List<SysEmpVo> list(@Param("sysEmpCnd") SysEmpCnd sysEmpCnd);

	Integer add(@Param("sysEmp") SysEmp sysEmp) throws DuplicateKeyException;

	SysEmp findById(@Param("id") Long id);

	Integer update(@Param("sysEmp") SysEmp sysEmp);

	Integer deleteById(@Param("id") Long id, @Param("operatorId") Long operatorId);

	List<SysEmp> findByName(@Param("name") String name, @Param("userId") Long userId);

	List<Emp> findNameByDealerId(@Param("name") String name, @Param("dealerId") Long dealerId);
}
