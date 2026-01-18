package cielo.cielo.mvc.dto;


import cielo.cielo.domain.Book;
import cielo.cielo.domain.Item;

public class BookSaveDTO {

  private final String name;
  private final Integer price;
  private final Integer stockQuantity;
  private final String author;
  private final String isbn;

  public BookSaveDTO(String name, Integer price, Integer stockQuantity, String author, String isbn) {
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
    this.author = author;
    this.isbn = isbn;
  }

  public Item toEntity(){
    return new Book(name, price, stockQuantity, author, isbn);
  }
}
