/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.common.logistics.kuaidi100.request;

import lombok.Data;

import java.io.File;

/**
 * @author legendshop
 * @Author: api.kuaidi100.com
 * @Date: 2020-07-20 9:36
 */
@Data
public class CloudAttachmentReq extends BaseReq<CloudPrintAttachmentParam> {
	private File file;
}
