package com.srp.core.beans.request;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class TagsBeanClassTest {
    TagsBeanClass bean = new TagsBeanClass();
    ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    void setup() throws IOException {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        String responseFile = "src/test/resources/com/srp/core/beans/TagBeanClassTest.json";
        String responseJson = new String(Files.readAllBytes(Paths.get(responseFile)));
        bean = mapper.readValue(responseJson, TagsBeanClass.class);
    }

    @Test
    void tagBean() throws IOException {
        String expected = "TagsBeanClass [getGeoTags()=[usa], getRoleTags()=[sales-manager, service-advisor], getBrandTags()=[fiat, jeep]]";
        String actual = bean.toString();
        assertEquals(expected, actual);

    }

    @Test
    void TagsNullTest() throws IOException {
        String responseFile = "src/test/resources/com/srp/core/beans/TagsBeanErrorTest.json";
        String responseJson = new String(Files.readAllBytes(Paths.get(responseFile)));
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        bean = mapper.readValue(responseJson, TagsBeanClass.class);
        TagsBeanClass tags = new TagsBeanClass();
        tags.setGeoTags(null);
        tags.setRoleTags(null);
        tags.setBrandTags(null);
        String expected = "TagsBeanClass [getGeoTags()=[], getRoleTags()=[], getBrandTags()=[]]";
        String actual = bean.toString();
        assertEquals(expected, actual);

    }
}
