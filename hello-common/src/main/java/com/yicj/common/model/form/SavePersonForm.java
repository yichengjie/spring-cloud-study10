package com.yicj.common.model.form;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yicj
 * @date 2023年09月07日 14:11
 */
@Data
public class SavePersonForm implements Serializable {

    private String username ;

    private String address ;
}
