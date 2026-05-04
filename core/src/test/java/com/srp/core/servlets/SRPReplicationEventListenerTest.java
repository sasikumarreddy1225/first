package com.srp.core.servlets;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.osgi.service.event.Event;

import com.day.cq.replication.ReplicationAction;
import com.day.cq.replication.ReplicationActionType;
import com.srp.core.services.ApiServiceCall;
import com.srp.core.services.ResourceResolverService;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;


@ExtendWith(AemContextExtension.class)
class SRPReplicationEventListenerTest {
	
	private final AemContext context = new AemContext();
	
	private SRPReplicationEventListener listener;
	
	@Mock
	private ApiServiceCall apiServiceCall;
	
	@Mock
	private ResourceResolverService resourceResolverService;
	
	
	@BeforeEach
	void setUp() throws LoginException {
  
		MockitoAnnotations.openMocks(this);
		
		context.registerService(ApiServiceCall.class, apiServiceCall);
		context.registerService(ResourceResolverService.class, resourceResolverService);
		
		listener = context.registerInjectActivateService(new SRPReplicationEventListener());
		
		Mockito.when(resourceResolverService.getResourceResolver()).thenReturn(context.resourceResolver());
		context.create().resource("/content/srp/test");
	}
	

	
	private Event eventHandler(String type) {
			
			       Map<String, Object> propsMap = new HashMap<>();
			       propsMap.put("path", "/content/srp/test");
			       propsMap.put("type", type);
			       propsMap.put("userId", "admin");
			       
			       Event event = new Event(ReplicationAction.EVENT_TOPIC, propsMap);
			       return event;
	}
	
	
	@Test
	void testActivateEvent() throws IOException {
		Event event = eventHandler(ReplicationActionType.ACTIVATE.name());
		
        Mockito.when(apiServiceCall.updateSRPContent(Mockito.any())).thenReturn("SUCCESS");
		listener.handleEvent(event);
		assertNotNull(listener);    
	}
	
	
	@Test
	void testDeactivateEvent() throws IOException {
		Event event = eventHandler(ReplicationActionType.DEACTIVATE.name());
		listener.handleEvent(event);
		assertNotNull(listener);
	}
	
	@Test
	void testInvalidEvent() throws IOException{
		Event event = eventHandler(ReplicationActionType.ACTIVATE.name());
		Mockito.when(apiServiceCall.updateSRPContent(Mockito.any())).thenThrow(new RuntimeException("API Failure"));
		listener.handleEvent(event);
	}
	

}
