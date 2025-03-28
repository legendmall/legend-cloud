/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.data.api;

import com.legendshop.common.core.constant.R;
import com.legendshop.data.service.ShopOutStockRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

/**
 * Feign调用实现
 *
 * @author legendshop
 */
@RestController
@Validated
@RequiredArgsConstructor
public class ShopOutStockRateApiImpl implements ShopOutStockRateApi {

	final ShopOutStockRateService shopOutStockRateService;

	@Override
	public R<Void> shopOutStockRateJobHandle() {
		return shopOutStockRateService.shopOutStockRateJobHandle();
	}
}
