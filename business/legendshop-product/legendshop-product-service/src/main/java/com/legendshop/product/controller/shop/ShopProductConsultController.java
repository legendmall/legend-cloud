/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.product.controller.shop;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.legendshop.jpaplus.support.PageSupport;
import com.legendshop.basic.api.WxApi;
import com.legendshop.common.core.constant.R;
import com.legendshop.common.security.utils.SecurityUtils;
import com.legendshop.product.dto.ProductConsultDTO;
import com.legendshop.product.query.ProductConsultQuery;
import com.legendshop.product.service.ProductConsultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商家端商品咨询控制器
 *
 * @author legendshop
 */
@Tag(name = "商家端商品咨询")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/s/product/consult", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopProductConsultController {

	final WxApi wxApi;
	final ProductConsultService productConsultService;


	@GetMapping("/page")
	@Operation(summary = "【商家】商品咨询列表查询")
	public R<PageSupport<ProductConsultDTO>> queryShopProductConsultList(ProductConsultQuery query) {
		Long shopId = SecurityUtils.getShopUser().getShopId();
		query.setShopId(shopId);
		query.setDelSts(0);

		PageSupport<ProductConsultDTO> favorProd = productConsultService.getProductConsultCenterPage(query);
		if (CollUtil.isNotEmpty(favorProd.getResultList())) {
			List<ProductConsultDTO> resultList = favorProd.getResultList();
			for (ProductConsultDTO productConsultDTO : resultList) {
				//手机号码脱敏
				String desensitization = getDesensitization(productConsultDTO.getAskUserIphone());
				productConsultDTO.setAskUserIphone(desensitization);
			}
		}
		return R.ok(favorProd);
	}

	@PreAuthorize("@pms.hasPermission('shop_product_consult_offline')")
	@Operation(summary = "【商家】商品咨询列表上下线 shop_product_consult_offline")
	@GetMapping("/offline")
	public R<Integer> offline(@RequestParam Long id, @RequestParam Integer status) {
		return productConsultService.offlineById(id, status);
	}

	@PreAuthorize("@pms.hasPermission('shop_product_consult_delete')")
	@Operation(summary = "【商家】商品咨询列表删除 shop_product_consult_delete")
	@DeleteMapping("/delete/{id}")
	public R<Integer> delete(@PathVariable Long id) {

		return productConsultService.deleteById(id);
	}

	@PreAuthorize("@pms.hasPermission('shop_product_consult_reply')")
	@Operation(summary = "【商家】商品咨询回复 shop_product_consult_reply")
	@GetMapping("/reply")
	public R<Integer> reply(@RequestParam Long id, String content) {
		//审核敏感字(内容)
		R<Void> result = wxApi.checkContent(content);
		if (!result.success()) {
			return R.fail(result.getMsg());
		}
		return productConsultService.replyById(id, content);
	}

	/**
	 * 手机号码脱敏
	 *
	 * @param mobile
	 * @return
	 */
	private String getDesensitization(String mobile) {
		if (StrUtil.isBlank(mobile)) {
			return "";
		}
		return mobile.substring(0, 3) + "****" + mobile.substring(7, 11);
	}
}
