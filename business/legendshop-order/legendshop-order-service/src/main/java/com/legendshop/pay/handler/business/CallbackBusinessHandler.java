/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.pay.handler.business;

import com.legendshop.pay.dto.PaySettlementDTO;

/**
 * @author legendshop
 */
public interface CallbackBusinessHandler {

	void handler(PaySettlementDTO paySettlementDTO);
}