/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.user.dto;

import com.legendshop.basic.enums.SmsTemplateTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author legendshop
 */
@Data
public class VerifyShopUserDTO extends ShopUserDTO {

	private static final long serialVersionUID = 4564996990240187448L;
	/**
	 * 验证码
	 */
	@Schema(description = "授权码：验证码、临时凭据")
	private String code;

	@Schema(description = "发送验证码类型：注册：REGISTER、登录：LOGIN、忘记密码：FORGET_PASSWORD、修改登录密码：MODIFY_LOGIN_PASSWORD、修改支付密码：MODIFY_PAY_PASSWORD、换绑手机，旧号码：MODIFY_BINDING_MOBILE、换绑手机，新号码：CONFIRM_MOBILE_BIND")
	private SmsTemplateTypeEnum codeType;

}
