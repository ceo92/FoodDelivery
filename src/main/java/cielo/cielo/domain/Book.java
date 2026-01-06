package cielo.cielo.domain;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("BOOK")
public class Book extends Item {

  private String author;
  private String isbn;
}
