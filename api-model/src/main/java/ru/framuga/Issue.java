package ru.framuga;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Запись о факте выдачи книги (в БД)
 */
@Entity
@Table(name = "issues")
@Schema(name = "Выдача книги")
@Data
public class Issue {

  @Id
  @Schema(name = "Идентификатор")
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  @JoinColumn(name = "bookId")
  @Schema(name = "Идентификатор книги")
//  @Column(name = "bookId")
  private Book book;

  @OneToOne
  @JoinColumn(name = "readerId")
  @Schema(name = "Идентификатор читателя")
//  @Column(name = "readerId")
  private Reader reader;

  @Schema(name = "Дата выдачи")
  @Column(name = "issueAt")
  private LocalDateTime issueAt;

  @Schema(name = "Дата возврата книги")
  @Column(name = "returnedAt")
  private LocalDateTime returnedAt;

}

