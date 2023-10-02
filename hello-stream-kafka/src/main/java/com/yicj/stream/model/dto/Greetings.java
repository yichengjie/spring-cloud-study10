package com.yicj.stream.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Greetings {

    private long timestamp;

    private String message;

}
