package com.wch.model;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
@Table(name = "short_urls")
public class FormatUrl {

    private int id;
    @Column(name = "long_url")
    private String longUrl;
    @Column(name = "short_url")
    private String shortUrl;
    private int count;
    @Column(name = "create_date")
    private Date createTime;
}
