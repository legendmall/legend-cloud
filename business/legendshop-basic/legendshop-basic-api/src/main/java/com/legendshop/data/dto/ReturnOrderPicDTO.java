/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author legendshop
 */
@Data
@Schema(description = "运营概况售后统计")
public class ReturnOrderPicDTO {

	@Schema(description = "退款率")
	private BigDecimal refundRate;

	@Schema(description = "退货率")
	private BigDecimal returnRate;

	@Schema(description = "时间")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date createTime;

	/**
	 * 退款商品数
	 */
	private Integer refundNum;

	/**
	 * 退款相关的支付商品数
	 */
	private Integer refundPayNum;

	/**
	 * 退货商品数
	 */
	private Integer returnNum;

	/**
	 * 退货相关的支付商品数
	 */
	private Integer returnPayNum;
}