/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.common.core.dto;

import cn.legendshop.jpaplus.persistence.Column;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author legendshop
 */
@Data
public class BaseEntity implements Serializable {
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date createTime;


	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

}