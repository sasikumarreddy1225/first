package com.srp.core.services;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition(name = "SRP Domain Configuration", description = "SRP Domain Configuration")
public @interface SrpDomainConfig {

	@AttributeDefinition(name="SRP Domain URL", description = "SRP Domain URL", type = AttributeType.STRING)
	String domainName();
}
