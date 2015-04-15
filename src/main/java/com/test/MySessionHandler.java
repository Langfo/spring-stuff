package com.test;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;


public class MySessionHandler extends StompSessionHandlerAdapter {

	private StompSession stompSession;

	
    @Override
    public void afterConnected(StompSession stompSession, StompHeaders connectedHeaders) {
        
    	this.stompSession = stompSession;
    	
    	stompSession.subscribe("/topic/timer", new StompFrameHandler() {

    	    @Override
    	    public Type getPayloadType(StompHeaders headers) {
    	        return String.class;
    	    }

    	    @Override
    	    public void handleFrame(StompHeaders headers, Object payload) {
    	        System.out.println("handling frame - " + payload.toString());
    	        
    	    }
    	});
    }
}
