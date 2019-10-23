package com.royal.admin.modular.api.json;

import lombok.Data;

import java.io.Serializable;

/**
 * 章节题目数
 */
@Data
public class WritingsCount implements Serializable {
    private String id;

    private Integer tCount;
}
