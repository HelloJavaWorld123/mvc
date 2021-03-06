package com.jzy.api.model.auth;

import com.jzy.api.cnd.auth.SysEmpCnd;
import com.jzy.framework.bean.model.GenericModel;
import com.jzy.framework.cache.ContextHolder;
import com.jzy.framework.cache.EmpCache;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Author : RXK
 * Date : 2019/5/30 12:14
 * Version: V1.0.0
 * Desc:
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysEmp extends GenericModel implements Serializable {

	private static final long serialVersionUID = -8829951463593067822L;

	private String name;

	private String password;

	private int status;

	private Long dealerId;

	private Set<String> roleValues;

	private Set<String> permValues;

	private SysEmp(Long id,Integer delFlag, Date createTime, Long creatorId, Date modifyTime, Long modifierId, String name, String password, int status, Long dealerId) {
		super(id,delFlag, createTime, creatorId, modifyTime, modifierId);
		this.name = name;
		this.password = password;
		this.status = status;
		this.dealerId = dealerId;
	}


	private SysEmp(Long id, Date modifyTime, Long modifierId, String name, String password, int status, Long dealerId) {
		super(id, modifyTime, modifierId);
		this.name = name;
		this.password = password;
		this.status = status;
		this.dealerId = dealerId;
	}

	public static SysEmp build(SysEmpCnd sysEmpCnd) {
		return new SysEmp(sysEmpCnd.getId(),0,new Date(),sysEmpCnd.getOperatorId(),new Date(),sysEmpCnd.getOperatorId(),sysEmpCnd.getName(),sysEmpCnd.getPassword(),sysEmpCnd.getStatus(),sysEmpCnd.getDealerId());
	}

	public static SysEmp update(SysEmpCnd sysEmpCnd) {
		return new SysEmp(sysEmpCnd.getId(),new Date(),sysEmpCnd.getOperatorId(),sysEmpCnd.getName(),sysEmpCnd.getPassword(),sysEmpCnd.getStatus(),sysEmpCnd.getDealerId());
	}
}
