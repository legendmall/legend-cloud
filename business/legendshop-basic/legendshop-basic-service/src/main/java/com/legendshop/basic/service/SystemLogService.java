/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.basic.service;


import cn.legendshop.jpaplus.support.PageSupport;
import com.legendshop.basic.query.SystemLogQuery;
import com.legendshop.common.core.dto.SystemLogDTO;

/**
 * 记录日志的service
 *
 * @author legendshop
 */
public interface SystemLogService {

	void save(SystemLogDTO systemLogDTO);

	PageSupport<SystemLogDTO> page(SystemLogQuery systemLogQuery);
}
