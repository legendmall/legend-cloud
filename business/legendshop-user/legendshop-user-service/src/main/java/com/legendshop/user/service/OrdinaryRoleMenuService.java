/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.user.service;

/**
 * @author legendshop
 */
public interface OrdinaryRoleMenuService {

	/**
	 * 保存权限菜单
	 *
	 * @param roleCode
	 * @param id
	 * @param menuIds
	 * @return
	 */
	Boolean saveRoleMenus(String roleCode, Long id, String menuIds);
}
