package com.rodrigolazoti.customer.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.rodrigolazoti.order.Application;
import com.rodrigolazoti.order.models.Order;
import com.rodrigolazoti.order.models.OrderItem;
import com.rodrigolazoti.order.repositories.OrderRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class OrderControllerTest {

  private final String apiResource = "/api/v1/Order";

  private final Order order = new Order(1L, 2L, "delivery address", "contact");
  private final List<OrderItem> items = Collections.unmodifiableList(Arrays.asList(
      new OrderItem(1L, order, BigDecimal.valueOf(1.99), 2L),
      new OrderItem(2L, order, BigDecimal.valueOf(2.25), 4L)));

  private MockMvc mockMvc;

  private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

  @MockBean
  private OrderRepository orderRepository;

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
    this.items.forEach(this.order::addOrderItem);
  }

  @Test
  public void shouldReturnSomeOrder() throws Exception {
    given(orderRepository.findById(1L)).willReturn(Optional.of(order));

    mockMvc.perform(get(apiResource + "/1")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderItems", hasSize(2)))
        .andExpect(jsonPath("$.contact", equalTo(order.getContact())));
  }

  @Test
  public void testShouldNotAcceptToCreateOrderWithoutCustomer() throws Exception {
    Order order = new Order(null, 1L, "deliveryAddress", "contact");
    shouldNotAcceptToCreateInvalidOrder(order);
  }

  @Test
  public void testShouldNotAcceptToCreateOrderWithoutStore() throws Exception {
    Order order = new Order(1L, null, "deliveryAddress", "contact");
    shouldNotAcceptToCreateInvalidOrder(order);
  }

  @Test
  public void testShouldNotAcceptToCreateOrderWithInvalidAddress() throws Exception {
    Order order = new Order(1L, 1L, " ", "contact");
    shouldNotAcceptToCreateInvalidOrder(order);
  }

  @Test
  public void testShouldNotAcceptToCreateOrderWithoutContact() throws Exception {
    Order order = new Order(1L, 1L, "deliveryAddress", " ");
    shouldNotAcceptToCreateInvalidOrder(order);
  }

  private void shouldNotAcceptToCreateInvalidOrder(Order order) throws Exception {
    mockMvc.perform(
        post(apiResource)
            .content(json(order))
            .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private String json(Object o) throws IOException {
    MockHttpOutputMessage mockOutputMsg = new MockHttpOutputMessage();
    this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockOutputMsg);

    return mockOutputMsg.getBodyAsString();
  }

}
