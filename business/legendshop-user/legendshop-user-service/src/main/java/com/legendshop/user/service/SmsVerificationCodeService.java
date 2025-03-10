/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.user.service;

import com.legendshop.common.core.constant.R;
import com.legendshop.user.dto.SmsVerificationCodeDTO;

/**
 * @author legendshop
 */
public interface SmsVerificationCodeService {

	/**
	 * 发送手机验证码
	 *
	 * @param smsVerificationCode the code
	 * @return code
	 */
	R<String> sendSmsCode(SmsVerificationCodeDTO smsVerificationCode);

}
