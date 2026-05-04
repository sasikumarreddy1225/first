package com.srp.core.beans.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TagsListBeanTest {

    TagsListBean list = new TagsListBean();
    ObjectMapper mapper = new ObjectMapper();
    String[] roles = { "sales" };
    String[] geo = { "usa" };
    String[] brands = { "fiat" };

    @Test
    void beanTest() {
        list.setBrandTag(brands);
        list.setGeoTag(geo);
        list.setRoleTag(roles);
        String expected = "TagsListBean [getRoleTag()=[sales], getGeoTag()=[usa], getBrandTag()=[fiat]]";
        String actual = list.toString();
        assertEquals(expected, actual);
    }


    @Test
    void beanNULLTest() throws IOException {
        String responseFile = "src/test/resources/com/srp/core/beans/TagsBeanErrorTest.json";
        String responseJson = new String(Files.readAllBytes(Paths.get(responseFile)));
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        list = mapper.readValue(responseJson, TagsListBean.class);
        String expected = "TagsListBean [getRoleTag()=[], getGeoTag()=[], getBrandTag()=[]]";
        String actual = list.toString();
        assertEquals(expected, actual);
        list.setBrandTag(null);
        list.setGeoTag(null);
        list.setRoleTag(null);
    }
}
