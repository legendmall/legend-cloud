/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.basic.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author legendshop
 */
@Data
public class SysParamItemCheckBoxDTO implements Serializable {

	private String name;

	private List<SysParamValueItemDTO> sysParamValueItemDTOS;

	private List<Long> checkBoxIds;
}
