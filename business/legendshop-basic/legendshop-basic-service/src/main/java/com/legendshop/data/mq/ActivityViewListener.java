/*
 * Copyright (c) 2015-2999 广州朗尊软件科技有限公司<www.legendshop.cn> All rights reserved.
 *
 * https://www.legendshop.cn/
 *
 * 版权所有,并保留所有权利
 *
 */
package com.legendshop.data.mq;


import com.legendshop.data.constants.AmqpConst;
import com.legendshop.data.dto.ActivityViewDTO;
import com.legendshop.data.service.DataService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 活动浏览记录数据存储监听
 *
 * @author legendshop
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ActivityViewListener {

	@Autowired
	private DataService dataService;

	@RabbitListener(bindings = @QueueBinding(value = @Queue(value = AmqpConst.LEGENDSHOP_DATA_ACTIVITY_VIEW_QUEUE, durable = "true"),
			exchange = @Exchange(value = AmqpConst.LEGENDSHOP_DATA_EXCHANGE), key = {AmqpConst.LEGENDSHOP_DATA_ACTIVITY_VIEW_LOG_ROUTING_KEY}
	))
	@SneakyThrows
	public void viewListener(ActivityViewDTO dto, Message message, Channel channel) {

		try {
			dataService.saveActivityViewData(dto);
		} catch (Exception e) {
			log.info(e.getMessage());
		} finally {
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		}

	}


}
