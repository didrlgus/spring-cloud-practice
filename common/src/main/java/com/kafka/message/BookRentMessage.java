package com.kafka.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utils.alert.AlertType;
import lombok.*;

import java.time.LocalDate;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookRentMessage {

    private Long bookId;
    private String identifier;
    private String email;
    private String bookTitle;
    private String bookAuthor;
    private AlertType alertType;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate rentExpiredDate;

}
