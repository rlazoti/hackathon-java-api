package com.rodrigolazoti.customer.controllers;

import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.rodrigolazoti.customer.Application;
import com.rodrigolazoti.customer.models.Customer;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CustomerControllerTest {

  private final String apiResource = "/api/v1/Customer";

  private MockMvc mockMvc;

  private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  void setConverters(HttpMessageConverter<Object>[] converters) {
    this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
        .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
        .findAny()
        .orElse(null);

    assertNotNull("the JSON message converter must not be null",
        this.mappingJackson2HttpMessageConverter);
  }

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void testShouldNotAcceptToCreateCustomerWithoutName() throws Exception {
    Customer customer = new Customer(" ", "bruce@wayne.com", "hidden place", "iambatman");
    shouldNotAcceptToCreateInvalidCustomer(customer);
  }

  @Test
  public void testShouldNotAcceptToCreateCustomerWithoutEmail() throws Exception {
    Customer customer = new Customer("bruce", " ", "hidden place", "iambatman");
    shouldNotAcceptToCreateInvalidCustomer(customer);
  }

  @Test
  public void testShouldNotAcceptToCreateCustomerWithInvalidEmail() throws Exception {
    Customer customer = new Customer("bruce", "invalid email", "hidden place", "iambatman");
    shouldNotAcceptToCreateInvalidCustomer(customer);
  }

  @Test
  public void testShouldNotAcceptToCreateCustomerWithoutAddress() throws Exception {
    Customer customer = new Customer("bruce", "bruce@waybe.com", " ", "iambatman");
    shouldNotAcceptToCreateInvalidCustomer(customer);
  }

  @Test
  public void testShouldNotAcceptToCreateCustomerWithoutPassword() throws Exception {
    Customer customer = new Customer("bruce", "bruce@waybe.com", "hidden place", " ");
    shouldNotAcceptToCreateInvalidCustomer(customer);
  }

  private void shouldNotAcceptToCreateInvalidCustomer(Customer customer) throws Exception {
    mockMvc.perform(
        post(apiResource)
            .content(json(customer))
            .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private String json(Object o) throws IOException {
    MockHttpOutputMessage mockOutputMsg = new MockHttpOutputMessage();
    this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockOutputMsg);

    return mockOutputMsg.getBodyAsString();
  }

}
