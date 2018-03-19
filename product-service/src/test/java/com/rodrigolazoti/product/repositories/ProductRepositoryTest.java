package com.rodrigolazoti.product.repositories;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rodrigolazoti.product.models.Product;
import com.rodrigolazoti.product.models.Store;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private StoreRepository storeRepository;

  @After
  public void tearDown() throws Exception {
    productRepository.deleteAll();
  }

  @Test
  public void shouldSaveAndSearhStores() throws Exception {
    Store mcdonalds = new Store("McDonalds");
    Product burger = new Product("Cheese Burger", mcdonalds, "Delicious Cheese Burger (200g)",
        BigDecimal.valueOf(4.99));
    Product pizza = new Product("Cheese Pizza", mcdonalds, "Big pizza", BigDecimal.valueOf(9.99));

    storeRepository.save(mcdonalds);
    productRepository.save(burger);
    productRepository.save(pizza);

    assertTrue(productRepository.findByNameContainingIgnoreCase("rger").contains(burger));
    assertTrue(productRepository.findByNameContainingIgnoreCase("cheese").size() == 2);
    assertTrue(productRepository.findByNameContainingIgnoreCase("rodrigo").isEmpty());
  }

}
