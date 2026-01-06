package cielo.cielo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 조인전략 1. DTYPE 2. NOT NULL
@DiscriminatorColumn(name = "DTYPE")
@Getter
public abstract class Item {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long id;

  private String name;
  private Integer stockQuantity;
  private Integer price;

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<Category>();


}
