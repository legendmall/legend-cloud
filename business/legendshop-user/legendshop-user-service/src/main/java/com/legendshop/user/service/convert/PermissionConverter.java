/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.user.service.convert;

import com.legendshop.common.core.service.BaseConverter;
import com.legendshop.user.dto.PermissionDTO;
import com.legendshop.user.entity.Permission;
import org.mapstruct.Mapper;

/**
 * @author legendshop
 */
@Mapper
public interface PermissionConverter extends BaseConverter<Permission, PermissionDTO> {

}
