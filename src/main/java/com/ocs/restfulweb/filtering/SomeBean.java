package com.ocs.restfulweb.filtering;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties({"field1", "field2"})
public class SomeBean {

    private String field1;
    private String field2;
    private String field3;
}
