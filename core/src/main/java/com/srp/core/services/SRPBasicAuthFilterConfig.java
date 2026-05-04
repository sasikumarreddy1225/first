
package com.srp.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "SRP Basic Auth Filter Config", description = "SRP Basic Auth Filter Configuration")
public @interface SRPBasicAuthFilterConfig {

	@AttributeDefinition(name = "User Name", description = "Valid SRP Basic Auth Username", type = AttributeType.STRING)
	String userName();

	@AttributeDefinition(name = "Password", description = "Valid SRP Basic Auth Password", type = AttributeType.STRING)
	String passWord();
}
