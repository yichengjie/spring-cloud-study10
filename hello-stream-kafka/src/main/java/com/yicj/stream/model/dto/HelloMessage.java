package com.yicj.stream.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author: yicj
 * @date: 2023/9/24 21:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HelloMessage implements Serializable {

    private Integer id ;

    private String message ;
}
