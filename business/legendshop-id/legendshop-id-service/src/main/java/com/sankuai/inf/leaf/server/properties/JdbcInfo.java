/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.sankuai.inf.leaf.server.properties;

import lombok.Data;

import java.io.Serializable;

/**
 * Jdbc信息
 *
 * @author legendshop
 */
@Data
public class JdbcInfo implements Serializable {

	String url;

	String username;

	String password;
}
