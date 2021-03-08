package com.kafka.message;

import com.utils.alert.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookReturnMessage {

    private Long bookId;
    private Long rentId;
    private String identifier;
    private AlertType alertType;

}
