/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.user.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 权限表(Perm)实体类
 *
 * @author legendshop
 */
@Data
public class PermissionDTO implements Serializable {


	private static final long serialVersionUID = -3398763023050045713L;
	/**
	 * 角色ID
	 */
	private Integer roleId;


	/**
	 * 权限ID
	 */
	private Integer functionId;

}