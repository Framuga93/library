package ru.framuga;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reader")
@Schema(name = "Читатель")
@Data
public class Reader {

  @Id
  @Schema(name = "Идентификатор")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Schema(name = "Имя читателя")
  @Column
  private String name;

  public Reader(String name) {
    this.name = name;
  }

  public Reader() {

  }
}
