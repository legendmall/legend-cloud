/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.user.query;

import cn.legendshop.jpaplus.support.PageParams;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author legendshop
 */
@Data
@Component
public class FavoriteShopQuery extends PageParams {


	private Long userId;
}
