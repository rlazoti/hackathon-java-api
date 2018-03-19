package com.rodrigolazoti.product.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.rodrigolazoti.product.Application;
import com.rodrigolazoti.product.models.Product;
import com.rodrigolazoti.product.models.Store;
import com.rodrigolazoti.product.repositories.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class ProductControllerTest {

  private final String apiResource = "/api/v1/Product";
  private final Store store = new Store(1L, "Happy Food");
  private final List<Product> products = Collections.unmodifiableList(Arrays.asList(
      new Product("Cheese Burger", store, "Delicious Cheese Burger (200g)", BigDecimal.valueOf(4.99)),
      new Product("Cheese Pizza", store, "Big pizza", BigDecimal.valueOf(9.99)),
      new Product("Rice and Beans", store, "White Rice and Black Beans", BigDecimal.valueOf(3.99))));

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private ProductRepository productRepository;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void shouldReturnAllProductsAvailable() throws Exception {
    given(productRepository.findAll()).willReturn(products);

    mockMvc.perform(get(apiResource)
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  public void shouldReturnSomeProduct() throws Exception {
    Product product = products.get(0);
    given(productRepository.findById(1L)).willReturn(Optional.of(product));

    mockMvc.perform(get(apiResource + "/1")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(product.getName())));
  }

  @Test
  public void shouldReturnNoProduct() throws Exception {
    given(productRepository.findById(1L)).willReturn(Optional.empty());

    mockMvc.perform(get(apiResource + "/1")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnSimilarProducts() throws Exception {
    given(productRepository.findByNameContainingIgnoreCase("asd")).willReturn(products);

    mockMvc.perform(get(apiResource + "/search/asd")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(3)));
  }

  @Test
  public void shouldReturnNoProducts() throws Exception {
    given(productRepository.findByNameContainingIgnoreCase("asd"))
      .willReturn(Collections.emptyList());

    mockMvc.perform(get(apiResource + "/search/asd")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

}
