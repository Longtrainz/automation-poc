package com.framework.models.contact_info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@Setter
@ToString
@Accessors(fluent = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactInfo {
    private String name;
    private String email;
    private String phone;
    private String subject;
    private String message;
}