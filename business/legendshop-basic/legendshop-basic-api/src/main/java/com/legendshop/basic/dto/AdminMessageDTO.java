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

/**
 * 后台消息实体.
 *
 * @author legendshop
 */
@Data
public class AdminMessageDTO implements Serializable {

	private static final long serialVersionUID = 3569185552877539093L;

	private String name;

	private Integer num;

	private String url;

	private String menuId;

}