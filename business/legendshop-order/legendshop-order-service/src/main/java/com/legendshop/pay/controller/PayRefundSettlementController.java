/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.pay.controller;

import com.legendshop.common.core.constant.R;
import com.legendshop.pay.dto.PayRefundSettlementDTO;
import com.legendshop.pay.service.PayRefundSettlementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 支付退款结算
 *
 * @author legendshop
 * @create: 2021-07-26 16:38
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/payRefundSettlement")
public class PayRefundSettlementController {

	final PayRefundSettlementService payRefundSettlementService;

	/**
	 * 根据支付退款号获取支付退款信息
	 *
	 * @param payRefundSn
	 * @return
	 */
	@GetMapping(value = "/getByPayRefundSn")
	public R<PayRefundSettlementDTO> getByPayRefundSn(@RequestParam("payRefundSn") String payRefundSn) {
		return R.ok(payRefundSettlementService.getByPayRefundSn(payRefundSn));
	}

	/**
	 * 根据退款订单获取退款信息
	 *
	 * @param refundSn
	 * @return
	 */
	@GetMapping(value = "/getByRefundSn")
	public R<List<PayRefundSettlementDTO>> queryByRefundSn(String refundSn) {
		return R.ok(payRefundSettlementService.queryByRefundSn(refundSn));
	}
}