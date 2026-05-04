package com.srp.core.models.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.srp.core.models.LabelsDataModel;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })

class GetCountriesDataModelTest {
	
	private final AemContext context = new AemContext();
	GetCountriesDataModel getCountriesDataModel;
	

	@BeforeEach
	void setUp() {
		context.addModelsForClasses(GetCountriesDataModel.class);
		context.load().json("/com/srp/core/models/common/getcountriestest.json", "/json");
	}

	@Test
	void testToString() {
		context.currentResource("/json/countries");
		getCountriesDataModel = context.request().adaptTo(GetCountriesDataModel.class);
		String expRespString = "GetCountriesDataModel [getCountryList()=[GetCountriesListDataModel [getLabel()=France, getCode()=FR, getName()=France], GetCountriesListDataModel [getLabel()=Germany, getCode()=DE, getName()=Germany]]]";
		String actuRespString = getCountriesDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}

	@Test
	void testLabelsNullToString() {
		getCountriesDataModel = new GetCountriesDataModel();
		List<GetCountriesListDataModel> result = getCountriesDataModel.getCountryList();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
