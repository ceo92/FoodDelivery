package cielo.cielo.domain;

import cielo.cielo.mvc.dto.FoodUpdateDTO;
import cielo.cielo.mvc.dto.UploadFile;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Food")
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE) // 빌더는 여길 사용
@Setter(AccessLevel.PRIVATE)
public class Food {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Embedded
    private UploadFile image;

    private Integer price;
    private LocalDateTime createdDateTime;
    private LocalDateTime updateDateTime;

    public void updateFood(FoodUpdateDTO foodUpdateDTO) {
        setName(foodUpdateDTO.name());
//        setImage(foodUpdateDTO.image());
        setPrice(foodUpdateDTO.price());
    }


}
