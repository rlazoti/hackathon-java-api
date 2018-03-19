package com.rodrigolazoti.product.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.equalTo;

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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.rodrigolazoti.product.models.Product;
import com.rodrigolazoti.product.models.Store;
import com.rodrigolazoti.product.repositories.StoreRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = StoreController.class)
public class StoreControllerTest {

  private final String apiResource = "/api/v1/Store";
  private final List<Store> stores = Collections.unmodifiableList(Arrays.asList(
      new Store("McDonalds", Arrays.asList(new Product("A", null, "AA", BigDecimal.valueOf(1.99)))), 
      new Store("Burger King", Arrays.asList(
          new Product("B", null, "BB", BigDecimal.valueOf(1.99)),
          new Product("C", null, "CC", BigDecimal.valueOf(2.99))))));

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @MockBean
  private StoreRepository storeRepository;

  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void shouldReturnAllStoresAvailable() throws Exception {
    given(storeRepository.findAll()).willReturn(stores);

    mockMvc.perform(get(apiResource)
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void shouldReturnSomeStore() throws Exception {
    Store store = stores.get(0);
    given(storeRepository.findById(1L)).willReturn(Optional.of(store));

    mockMvc.perform(get(apiResource + "/1")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", equalTo(store.getName())));
  }

  @Test
  public void shouldReturnNoStore() throws Exception {
    given(storeRepository.findById(1L)).willReturn(Optional.empty());

    mockMvc.perform(get(apiResource + "/1")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

  @Test
  public void shouldReturnSimilarStores() throws Exception {
    given(storeRepository.findByNameContainingIgnoreCase("asd")).willReturn(stores);

    mockMvc.perform(get(apiResource + "/search/asd")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void shouldReturnNoStores() throws Exception {
    given(storeRepository.findByNameContainingIgnoreCase("asd"))
      .willReturn(Collections.emptyList());

    mockMvc.perform(get(apiResource + "/search/asd")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void shouldReturnStoreProducts() throws Exception {
    given(storeRepository.findById(2l)).willReturn(Optional.of(stores.get(1)));

    mockMvc.perform(get(apiResource + "/2/products")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)));
  }

  @Test
  public void shouldReturnNoStoreProducts() throws Exception {
    given(storeRepository.findById(2l)).willReturn(Optional.empty());

    mockMvc.perform(get(apiResource + "/2/products")
        .contentType(APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }

}
