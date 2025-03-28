/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.shop.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * 商家默认的轮换图表(ShopBanner)实体类
 *
 * @author legendshop
 */
@Data
public class ShopBannerDTO implements Serializable {


	private static final long serialVersionUID = 4251429045552965341L;

	private Long id;


	private Long shopId;


	private String imageFile;


	private String url;


	private Integer seq;

}
