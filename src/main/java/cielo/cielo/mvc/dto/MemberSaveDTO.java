package cielo.cielo.mvc.dto;

import cielo.cielo.domain.Address;
import cielo.cielo.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSaveDTO {

  private String name;
  private String city;
  private String street;
  private String zipcode;

  public Member toEntity(){
    return Member.builder().name(name).address(new Address(city, street, zipcode)).build();
  }

}
