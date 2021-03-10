package com.kafka.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.utils.alert.AlertType;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookReturnMessage {

    private Long bookId;
    private String bookTitle;
    private String bookAuthor;
    private Long rentId;
    private String identifier;
    private String email;
    private AlertType alertType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime returnDateTime;

}
