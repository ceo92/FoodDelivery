package cielo.cielo.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderSaveDto {

  private Long memberId;
  private Long itemId;
  private Integer orderCount;

}
