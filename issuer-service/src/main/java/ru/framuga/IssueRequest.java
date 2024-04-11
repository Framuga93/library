package ru.framuga;

import lombok.Data;

import java.util.UUID;

/**
 * Запрос на выдачу
 */
@Data
public class IssueRequest {

  /**
   * Идентификатор читателя
   */
  private UUID readerId;

  /**
   * Идентификатор книги
   */
  private UUID bookId;

}
