package com.royal.admin.modular.api.json;

import lombok.Data;

import java.util.List;

/**
 * 答题情况
 */
@Data
public class QuestionPaperStatus {

    private List<CampStatus> south;
    private List<CampStatus> north;

}
