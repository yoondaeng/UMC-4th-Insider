//package com.example.umc_insider.websocket;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.*;
//
//import java.net.http.WebSocket;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebsSocketConfig implements WebSocketMessageBrokerConfigurer {
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/queue", "/topic"); // prefix가 붙은 메세지 발행시 브로커가 처리
//        registry.setApplicationDestinationPrefixes("/app"); // 메시지 핸들러로 라우팅되는 prefix
//    }
//
//    @Override // 웹소켓 연결주소
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/websocket").withSockJS();
//    }
//}
