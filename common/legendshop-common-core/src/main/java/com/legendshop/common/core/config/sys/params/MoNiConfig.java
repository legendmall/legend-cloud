/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.common.core.config.sys.params;

import lombok.Data;

/**
 * 模拟支付
 *
 * @author legendshop
 */
@Data
public class MoNiConfig implements ParamsConfig {

	private String key;

	private String token;

	private Boolean enabled;
}
