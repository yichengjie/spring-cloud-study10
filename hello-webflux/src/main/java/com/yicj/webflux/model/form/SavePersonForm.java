package com.yicj.webflux.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yicj
 * @date 2023年08月26日 17:46
 */
@Data
public class SavePersonForm implements Serializable {

    private String username ;

    private String address ;
}
