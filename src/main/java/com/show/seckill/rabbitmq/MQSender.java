package com.show.seckill.rabbitmq;

import com.show.seckill.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 涂成
 * @Date: 2019/6/28 15:13
 * @Description: 队列发送者
 */
@Service
public class MQSender {

    private static final Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    RedisService redisService;

    public void sendMiaoshaMsg(MiaoshaMessage message) {
        String result = redisService.beanToValue(message);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_EXCHANGAE, MQConfig.MIAOSHA_ROUTING_KEY, result);
    }

//    public void send(Object message) {
//        String result = redisService.beanToValue(message);
//        amqpTemplate.convertAndSend(MQConfig.DIRECT_EXCHANGE, "key.1", result);
//    }
//
//    public void sendTopic(Object message) {
//        logger.info("send topic message: {}", message);
//        String result = redisService.beanToValue(message);
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", result + "1");
//        amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", result + "2");
//    }
//
//    public void sendFanout(Object message) {
//        logger.info("send fanout message: {}", message);
//        String result = redisService.beanToValue(message);
//        amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", result + "fanout");
//    }
//
//    public void sendHeaders(Object message) {
//        logger.info("send headers message: {}", message);
//        String result = redisService.beanToValue(message);
//        MessageProperties messageProperties = new MessageProperties();
//        messageProperties.setHeader("headers1", "value1");
//        messageProperties.setHeader("headers2", "value2");
//        Message msg = new Message(result.getBytes(), messageProperties);
//        amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", msg);
//    }
}
