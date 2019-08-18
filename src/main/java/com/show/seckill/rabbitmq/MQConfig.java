package com.show.seckill.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Auther: 涂成
 * @Date: 2019/6/28 15:09
 * @Description: rabbitmq配置类
 */
@Configuration
public class MQConfig {


    public static final String MIAOSHA_QUEUE = "miaosha.queue";

    public static final String MIAOSHA_EXCHANGAE = "miaosha.exchange";

    public static final String MIAOSHA_ROUTING_KEY = "miaosha.do";

    @Bean
    public Queue miaoshaQueue() {
        return new Queue(MIAOSHA_QUEUE, true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(MIAOSHA_EXCHANGAE);
    }

    @Bean
    public Binding bindMiaosha() {
        return BindingBuilder.bind(miaoshaQueue()).to(directExchange()).with(MIAOSHA_ROUTING_KEY);
    }


//    public static final String QUEUE_NAME = "queue1";
//
//    public static final String QUEUE_NAME2 = "queue2";
//
//    public static final String QUEUE_NAME3 = "queue3";
//
//    public static final String TOPIC_QUEUE1 = "topic_queue1";
//
//    public static final String TOPIC_QUEUE2 = "topic_queue2";
//
//    public static final String TOPIC_EXCHANGE = "topicExchange";
//
//    public static final String FANOUT_EXCHANGE = "fanoutExchange";
//
//    public static final String HEADERS_EXCHANGE = "headersExchange";
//
//    public static final String HEADERS_QUEUE = "headers.queue";
//
//    public static final String DIRECT_EXCHANGE = "direct.queue";
//
//    /**
//     * direct 模式 默认Exchange交换机
//     *
//     * @return
//     */
//    @Bean
//    public Queue directQueue1() {
//        return new Queue(QUEUE_NAME, true);
//    }
//
//    @Bean
//    public Queue directQueue2() {
//        return new Queue(QUEUE_NAME2, true);
//    }
//
//    @Bean
//    public Queue directQueue3() {
//        return new Queue(QUEUE_NAME3, true);
//    }
//
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange(DIRECT_EXCHANGE);
//    }
//
//    @Bean
//    public Binding bindingDirectExchange1() {
//        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("key.1");
//    }
//
//    @Bean
//    public Binding bindingDirectExchange2() {
//        return BindingBuilder.bind(directQueue2()).to(directExchange()).with("key.1");
//    }
//
//    @Bean
//    public Binding bindingDirectExchange3() {
//        return BindingBuilder.bind(directQueue3()).to(directExchange()).with("key.2");
//    }
//
//    /**
//     * topic 模式 topicExchange交换机
//     *
//     * @return
//     */
//    @Bean
//    public Queue topicQueue1() {
//        return new Queue(TOPIC_QUEUE1, true);
//    }
//
//    @Bean
//    public Queue topicQueue2() {
//        return new Queue(TOPIC_QUEUE2, true);
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//
//    /**
//     * 将topicQueue1与topicExchange交换机绑定
//     *
//     * @return
//     */
//    @Bean
//    public Binding bindQueue1() {
//        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("topic.key1");
//    }
//
//    /**
//     * 将topicQueue2与topicExchange交换机绑定
//     *
//     * @return
//     */
//    @Bean
//    public Binding bindQueue2() {
//        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
//    }
//
//    /**
//     * fanoutExchange交换机 (广播)
//     *
//     * @return
//     */
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange(FANOUT_EXCHANGE);
//    }
//
//    /**
//     * 将queue1和queue2分别与fanoutExchange绑定
//     *
//     * @return
//     */
//    @Bean
//    public Binding fanoutQueue1() {
//        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
//    }
//
//    @Bean
//    public Binding fanoutQueue2() {
//        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
//    }
//
//    /**
//     * HeadersExchange交换机
//     *
//     * @return
//     */
//    @Bean
//    public HeadersExchange headersExchange() {
//        return new HeadersExchange(HEADERS_EXCHANGE);
//    }
//
//    @Bean
//    public Queue headersQueue() {
//        return new Queue(HEADERS_QUEUE);
//    }
//
//    /**
//     * 将headersQueue与HeadersExchange交换机绑定
//     *
//     * @return
//     */
//    @Bean
//    public Binding bingHeadersQueue() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("headers1", "value1");
//        map.put("headers2", "value2");
//        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
//    }
}
