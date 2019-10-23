package com.royal.admin.modular.api.json;

import lombok.Data;

/**
 * 章节用户答题状况
 */
@Data
public class CampStatus {

    private String wId;

    private Integer userCount;

    private Integer rightUserCount;
}
