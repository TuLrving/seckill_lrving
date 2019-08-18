package com.show.seckill.rabbitmq;

import com.show.seckill.domain.MiaoshaUser;
import com.show.seckill.redis.RedisService;
import com.show.seckill.service.GoodsService;
import com.show.seckill.service.MiaoshaService;
import com.show.seckill.vo.GoodsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 涂成
 * @Date: 2019/6/28 15:19
 * @Description: 消息接收者
 */
@Service
public class MQReceiver {

    private static final Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    GoodsService goodsService;

    @RabbitListener(queues = MQConfig.MIAOSHA_QUEUE)
    public void receiveMiaosha(String msg) {
        MiaoshaMessage message = RedisService.stringToBean(msg, MiaoshaMessage.class);
        MiaoshaUser user = message.getUser();
        long goodsId = message.getGoodsId();

        //查询是否还有库存
        GoodsVO goods = goodsService.findGoodsVoById(goodsId);
        Integer count = goods.getStockCount();
        if (count <= 0) {//已无库存
            return;
        }
        //执行秒杀
        miaoshaService.miaosha(user, goods);
    }

//    @RabbitListener(queues = MQConfig.QUEUE_NAME)
//    public void receive1(String message) {
//        logger.info("receive : dir1 message {}", message);
//    }
//
//    @RabbitListener(queues = MQConfig.QUEUE_NAME2)
//    public void receive2(String message) {
//        logger.info("receive : dir2 message {}", message);
//    }
//
//    @RabbitListener(queues = MQConfig.QUEUE_NAME3)
//    public void receive3(String message) {
//        logger.info("receive : dir3 message {}", message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE1)
//    public void receiveQueue1(String message) {
//        logger.info("receive : queue1 {}", message);
//    }
//
//    @RabbitListener(queues = MQConfig.TOPIC_QUEUE2)
//    public void receiveQueue2(String message) {
//        logger.info("receive : queue2 {}", message);
//    }
//
//    @RabbitListener(queues = MQConfig.HEADERS_QUEUE)
//    public void receiveHeadersQueue(byte[] message) {
//        logger.info("receive : HeadersQueue {}", new String(message));
//    }
}
