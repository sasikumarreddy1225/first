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
class ProductCategoriesDataModelTest {
	
	private final AemContext context = new AemContext();
	
	ProductCategoriesDataModel productCategoriesDataModel;
	
	@BeforeEach
	void setUp() {
		context.addModelsForClasses(ProductCategoriesDataModel.class);
		context.load().json("/com/srp/core/models/common/ProductCategoriesDataModelTest.json", "/json");
	}

	@Test
	void testToString() {
		context.currentResource("/json/product_categories");
		productCategoriesDataModel = context.request().adaptTo(ProductCategoriesDataModel.class);
		String expRespString = "ProductCategoriesDataModel [getLabels()=[LabelsDataModel [getLabel()=FrequentlyPurchased, getLabelId()=6ddf37d0-9790-82e0-4be8-132127bd7bf5, getLabelType()=FAVOURITES], LabelsDataModel [getLabel()=Workshop Products, getLabelId()=8a3df591-431b-2bfa-f1f6-1306065a3ce7, getLabelType()=FAVOURITES_MESSAGE], LabelsDataModel [getLabel()=Search PlaceHolder Text, getLabelId()=6ddf37d0-9790-82e0-4be8-132127bd7bf5, getLabelType()=FAVOURITES], LabelsDataModel [getLabel()=My Favorites, getLabelId()=6ddf37d0-9790-82e0-4be8-132127bd7bf5, getLabelType()=FAVOURITES]], getExperienceFragments()=[ExperienceFragmentsDataModel [getXfPath()=/content/experience-fragments/srp/ee/it/en/banner/master]], getCategories()=[CategroyDataModel [getName()=, getLabel()=Air Conditioning, getIconPath()=/images/cat_auto_transmission.svg, getSubcategories()=[SubCategroyDataModel [getName()=Abrasive Matting, getLabel()=Abrasive Matting, getIconPath()=/images/SC_Abrasive_Matting.svg], SubCategroyDataModel [getName()=Abrasive Sanding, getLabel()=Abrasive Sanding, getIconPath()=/images/SC_Abrasive_Sanding.svg], SubCategroyDataModel [getName()=Paint Accessories, getLabel()=Paint Accessories, getIconPath()=/images/SC_Paint_Accessories.svg], SubCategroyDataModel [getName()=Paint Colors, getLabel()=Paint Colors, getIconPath()=/images/SC_Paint_Colors.svg]]], CategroyDataModel [getName()=, getLabel()=Bodywork, getIconPath()=/images/cat_car_gear.svg, getSubcategories()=[SubCategroyDataModel [getName()=Electrical Components, getLabel()=Electrical Components, getIconPath()=/images/SC_Abrasive_Sanding.svg], SubCategroyDataModel [getName()=Abrasive Sanding, getLabel()=Abrasive Sanding, getIconPath()=/images/SC_Abrasive_Sanding.svg]]], CategroyDataModel [getName()=, getLabel()=Boutique, getIconPath()=/images/cat_electrical_services.svg, getSubcategories()=[SubCategroyDataModel [getName()=Steering System, getLabel()=Steering System, getIconPath()=/images/SC_Paint_Colors.svg], SubCategroyDataModel [getName()=Electrical Components, getLabel()=Electrical Components, getIconPath()=/images/SC_Abrasive_Sanding.svg]]]]]";
		String actuRespString = productCategoriesDataModel.toString();
		assertEquals(expRespString, actuRespString);
	}

	@Test
	void testLabelsNullToString() {
		productCategoriesDataModel = new ProductCategoriesDataModel();
		List<LabelsDataModel> result = productCategoriesDataModel.getLabels();
		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

}
