package com.yicj.kafka.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: yicj
 * @date: 2023/9/24 17:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloMessage {

    private Integer id ;

    private String message ;

}
