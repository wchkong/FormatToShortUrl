package com.wch.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
public class FormatUrl {

    private int id;
    private String longUrl;
    private String shortUrl;
    private int count;
    private Date createTime;
}
