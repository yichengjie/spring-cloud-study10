package com.yicj.webflux.repository.entity;

import lombok.Data;
import java.io.Serializable;

/**
 * @author yicj
 * @date 2023年08月26日 17:29
 */
@Data
public class PersonEntity implements Serializable {

    private String id ;

    private String username ;

    private String address ;
}
