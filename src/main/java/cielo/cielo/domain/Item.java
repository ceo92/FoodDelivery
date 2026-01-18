package cielo.cielo.domain;

import cielo.cielo.mvc.exception.NotEnoughStockException;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 조인전략 1. DTYPE 2. NOT NULL
@DiscriminatorColumn(name = "DTYPE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Item {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "item_id")
  private Long id;

  private String name;
  private Integer stockQuantity;
  private Integer price;

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<Category>();

  public Item(String name, Integer stockQuantity, Integer price) {
    this.name = name;
    this.stockQuantity = stockQuantity;
    this.price = price;
  }

  public void addStock(int stockQuantity){
    this.stockQuantity += stockQuantity;
  }

  public void removeStock(int quantity){
    int remainStock = this.stockQuantity - quantity;
    if(remainStock < 0){
      throw new NotEnoughStockException("재고가");
    }
    this.stockQuantity = remainStock;
  }
}
