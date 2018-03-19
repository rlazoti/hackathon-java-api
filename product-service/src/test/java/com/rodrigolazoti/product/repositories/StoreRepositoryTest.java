package com.rodrigolazoti.product.repositories;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rodrigolazoti.product.models.Store;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StoreRepositoryTest {

  @Autowired
  private StoreRepository repository;

  @After
  public void tearDown() throws Exception {
    repository.deleteAll();
  }

  @Test
  public void shouldSaveAndSearhStores() throws Exception {
    Store mcdonalds = new Store("McDonalds", Collections.emptyList());
    Store burgerKing = new Store("Burger King", Collections.emptyList());

    repository.save(mcdonalds);
    repository.save(burgerKing);

    assertTrue(repository.findByNameContainingIgnoreCase("kin").contains(burgerKing));
    assertTrue(repository.findByNameContainingIgnoreCase("DONAL").contains(mcdonalds));
    assertTrue(repository.findByNameContainingIgnoreCase("rodrigo").isEmpty());
  }

}
