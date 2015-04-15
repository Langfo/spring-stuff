package com.test;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@SpringBootApplication
public class App {

	private static SockJsClient sockJsClient;
	private final static WebSocketHttpHeaders httpHeaders = new WebSocketHttpHeaders();
	private final static StompHeaders stompHeaders = new StompHeaders();
	

	public static void main(String[] args) {
		
		SpringApplication.run(App.class, args);
	
		try {
			
			List<Transport> transports = new ArrayList<>();
			transports.add(new WebSocketTransport(new StandardWebSocketClient()));
			RestTemplateXhrTransport xhrTransport = new RestTemplateXhrTransport(new RestTemplate());
			xhrTransport.setRequestHeaders(httpHeaders);
			transports.add(xhrTransport);

			sockJsClient = new SockJsClient(transports);
			
			URI uri = new URI("ws://localhost:" + 8080 + "/endpoint");
			WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
			stompClient.setMessageConverter(new MappingJackson2MessageConverter());
			
			
			stompClient.connect(uri, httpHeaders, stompHeaders, new MySessionHandler());
			stompClient.start();
			
			while(true) {
				try {
					Thread.sleep(100);
				}
				catch(Exception e) {}
			}
			
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		

	}

}
