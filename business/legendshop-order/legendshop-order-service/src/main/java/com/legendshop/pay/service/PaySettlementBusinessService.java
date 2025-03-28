/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.pay.service;

import com.legendshop.common.core.constant.R;

/**
 * @author legendshop
 */
public interface PaySettlementBusinessService {

	/**
	 * 处理支付成功但处于待支付的订单
	 *
	 * @return
	 */
	R<Void> paySuccessCompensateJobHandle();
}
