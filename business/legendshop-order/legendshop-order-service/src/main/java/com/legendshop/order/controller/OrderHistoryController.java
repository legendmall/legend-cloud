/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.order.controller;

import com.legendshop.common.core.constant.R;
import com.legendshop.order.dto.OrderHistoryDTO;
import com.legendshop.order.service.OrderHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author legendshop
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderHistoryController {

	private final OrderHistoryService orderHistoryService;


	@PostMapping("/save")
	public R<List<Long>> save(List<OrderHistoryDTO> orderHistoryList) {
		return R.ok(orderHistoryService.save(orderHistoryList));
	}
}