package cdu.gu.onlinechat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * Spring websocket配置类
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    /**
     * public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry)这个方法签名配置websocket的端点
     *  stompEndpointRegistry.addEndpoint("/websocket")指定了客户端连接时websocket的url路径
     *  addInterceptors(new HttpSessionHandshakeInterceptor())添加了拦截器，在执行websocket连接前判断session中的数据
     *  .withSockJS(): 这一部分代码表示你正在使用 SockJS 支持。SockJS 是一个用于提供 WebSocket 兼容性的库，
     *  它允许在不支持 WebSocket 的浏览器上使用类似 WebSocket 的通信
     * @param stompEndpointRegistry
     */
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/websocket").addInterceptors(new HttpSessionHandshakeInterceptor()).withSockJS();
    }
    /**
     * public void configureMessageBroker(MessageBrokerRegistry registry)这是一个方法签名，表示正在配置消息代理，消息代理
     * 用于处理websocket消息的路由和分发
     * /topic直接进入消息代理
     * registry.setApplicationDestinationPrefixes("/app");指定客户端发送消息的目标前缀，
     * 意味着客户端发送的消息的目标路径应以 "/app" 开头
     * registry.enableSimpleBroker("/topic");简单消息代理用于将消息从服务器发送到客户端。在这里，配置了一个简单消息代理，
     * 它将消息发送到以 "/topic" 开头的目标路径，客户端可以订阅这些目标路径以接收消息。
     * @param registry
     */

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}
