
package com.srp.core.models.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class AdminConfigDataModelTest {

    private final AemContext ctx = new AemContext();

    @BeforeEach
    void setUp() {
        ctx.addModelsForClasses(AdminConfigDataModel.class);
        ctx.load().json("/com/srp/core/models/configuration/AdminConfigDataModelTest.json", "/json");
        ctx.currentResource("/json");
    }


    @Test
    void toStringTestCase() {
        AdminConfigDataModel labels = ctx.request().adaptTo(AdminConfigDataModel.class);
        String actualResp = labels.toString();
        String expectedResp = "AdminConfigDataModel [getAdminGroups()=[AdminGroupsListDataModel [getGroup()=asdf|123, getGroupId()=123, getGroupName()=asdf]]]";
        assertEquals(expectedResp, actualResp);
    }

    @Test
    void toStringNullTestCase() {
        ctx.currentResource("/json/adminConfigNull");
        AdminConfigDataModel labels = ctx.request().adaptTo(AdminConfigDataModel.class);
        String actualResp = labels.toString();
        String expectedResp = "AdminConfigDataModel [getAdminGroups()=[]]";
        assertEquals(expectedResp, actualResp);
    }

    @Test
    void toStringNoGroTestCase() {
        ctx.currentResource("/json/adminConfigNoGrp");
        AdminConfigDataModel labels = ctx.request().adaptTo(AdminConfigDataModel.class);
        String actualResp = labels.toString();
        String expectedResp = "AdminConfigDataModel [getAdminGroups()=[AdminGroupsListDataModel [getGroup()=null, getGroupId()=null, getGroupName()=null]]]";
        assertEquals(expectedResp, actualResp);
    }
}
