/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.activity.query;

import cn.legendshop.jpaplus.support.PageParams;
import lombok.Data;

/**
 * @author legendshop
 */
@Data
public class ShippingActiveQuery extends PageParams {

	private static final long serialVersionUID = -2311616554826783001L;

	/**
	 * 商家ID
	 */
	private Long shopId;


	/**
	 * 活动名称
	 */
	private String name;

}
