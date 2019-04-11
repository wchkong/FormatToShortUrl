package com.wch.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class RedirectRecord {

    private int id;
    private String shortUrl;
    private Date createTime;
    private String ip;
}
