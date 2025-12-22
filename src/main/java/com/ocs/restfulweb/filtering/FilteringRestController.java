package com.ocs.restfulweb.filtering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/filtering")
public class FilteringRestController {

    @GetMapping
    public SomeBean someBean() {
        return new SomeBean("value1", "value2", "value3");
    }

    @GetMapping("/list")
    public List<SomeBean> someBeanList() {
        return Arrays.asList(new SomeBean("value1", "value2", "value3"),
                new SomeBean("value4", "value5", "value6"));
    }
}
