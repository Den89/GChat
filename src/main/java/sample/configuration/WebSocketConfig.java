package sample.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import sample.service.auth.AuthenticateService;
import sample.service.message.handling.MessageHandlingStrategy;
import sample.service.ws.WSHandler;
import sample.service.ws.session.WsSessionManager;

import java.util.List;

/**
 * Created by delf.
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final List<MessageHandlingStrategy> handlers;
    private final AuthenticateService authService;
    private final WsSessionManager wsSessionManager;

    @Autowired
    public WebSocketConfig(List<MessageHandlingStrategy> handlers, AuthenticateService authService, WsSessionManager wsSessionManager) {
        this.handlers = handlers;
        this.authService = authService;
        this.wsSessionManager = wsSessionManager;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/WebSocket").setAllowedOrigins("*");
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(4096);
        container.setMaxBinaryMessageBufferSize(4096);
        container.setAsyncSendTimeout(10000000L);
        container.setMaxSessionIdleTimeout(10000000L);
        return container;
    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new WSHandler(handlers, authService, wsSessionManager);
    }
}
