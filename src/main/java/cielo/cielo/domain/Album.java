package cielo.cielo.domain;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@Getter
@DiscriminatorValue("ALBUM")
public class Album extends Item {

  private String artist;
  private String etc;
}
