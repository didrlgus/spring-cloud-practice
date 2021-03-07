package com.kafka.message;

import com.domain.book.BookAlertType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookRentMessage {

    private Long bookId;
    private String identifier;
    private String bookTitle;
    private String bookAuthor;
    private BookAlertType bookAlertType;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate rentExpiredDate;

}
