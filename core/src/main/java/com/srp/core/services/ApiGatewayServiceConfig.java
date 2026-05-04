package com.srp.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "SRP APIGateway Service Configuration", description = "APIGateway Service Configuration")
public @interface ApiGatewayServiceConfig {
	
	@AttributeDefinition(name = "SRP Integration Server Domain", description = "Integration Server Domain Value", type = AttributeType.STRING)
	String domainName();

	@AttributeDefinition(name = "SRP API Endpoint", description = "MethodName*|API-URL   (* should not be null/Empty)", type = AttributeType.STRING)
	String[] apiURLs();
}
