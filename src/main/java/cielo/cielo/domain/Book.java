package cielo.cielo.domain;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DiscriminatorValue("BOOK")
@NoArgsConstructor
public class Book extends Item {
  private String author;
  private String isbn;

  public Book(String name, Integer price, Integer stockQuantity, String author, String isbn) {
    super(name, price, stockQuantity);
    this.author = author;
    this.isbn = isbn;
  }


}
