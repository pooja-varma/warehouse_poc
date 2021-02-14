package com.warehouse.repository;

import com.warehouse.entities.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {
  
  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ProductRepository productRepository;


  @Test
  public void whenFindByName_thenReturnEmployee() {
    // given
    Product chair = new Product(2,"chair");
    entityManager.persist(chair);
    entityManager.flush();

    // when
    Product found = productRepository.findAllByProductID(chair.getProduct_id());

    // then
    assertThat(found.getProduct_name())
        .isEqualTo(chair.getProduct_name());
  }
}
