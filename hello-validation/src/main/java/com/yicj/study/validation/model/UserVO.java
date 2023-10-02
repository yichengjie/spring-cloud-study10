package com.yicj.study.validation.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author: yicj
 * @date: 2023/9/29 13:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserVO implements Serializable {
    @NotNull(message = "name not be null")
    @Size(min = 3, max = 18, message = "name.length >=3 and <= 18")
    private String name ;
    //
    @Min(value = 18, message = "age must >= 18")
    private int age ;
}
