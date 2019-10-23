package com.royal.admin.modular.api.json;

import lombok.Data;

import java.io.Serializable;

@Data
public class TopicJson implements Serializable {
    private String title;
    private String options;
    private String answer;
}
