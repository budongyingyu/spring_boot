package com.li.test.spring_boot.readinglist;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@ConfigurationProperties("amazon")
public class AmazonProperties {
    //注入带amazon前缀的属性
    private String associateId;

    public void setAssociateId(String associateId) {
        this.associateId = associateId;
    }
    public String getAssociateId() {
        return associateId;
    }
}
