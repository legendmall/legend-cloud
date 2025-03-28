/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.pay.api;

import com.legendshop.common.core.constant.R;
import com.legendshop.common.core.constant.ServiceNameConstants;
import com.legendshop.pay.dto.RefundDTO;
import com.legendshop.pay.dto.RefundResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author legendshop
 */
@FeignClient(contextId = "refundApi", value = ServiceNameConstants.ORDER_SERVICE)
public interface RefundApi {

	String PREFIX = ServiceNameConstants.ORDER_SERVICE_RPC_PREFIX;

	@PostMapping(value = PREFIX + "/refund")
	R<RefundResultDTO> refund(@RequestBody RefundDTO refundDTO);
}
