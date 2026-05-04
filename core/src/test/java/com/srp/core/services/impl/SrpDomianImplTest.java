
  package com.srp.core.services.impl;
  
  import org.junit.jupiter.api.Test; import org.mockito.Mockito;
  
  import com.srp.core.services.SrpDomainConfig; import static
  org.mockito.Mockito.lenient; import static
  org.junit.jupiter.api.Assertions.assertEquals;
  
  class SrpDomianImplTest {
  
  SrpDomainConfig config = Mockito.mock(SrpDomainConfig.class);
  
  SrpDomainImpl impl = new SrpDomainImpl();
  
  @Test void testGetDomainName() {
  lenient().when(config.domainName()).thenReturn("www.adobe.com");
  impl.activate(config); impl.modify(config); String expected =
  "www.adobe.com"; String actual = impl.getDomainUrl(); assertEquals(expected,
  actual); }
  
  @Test void testDeactivate() {
  lenient().when(config.domainName()).thenReturn("www.adobe.com");
  impl.deactivate(config); String expected = ""; String actual =
  impl.getDomainUrl(); assertEquals(expected, actual); }
  
  }
 