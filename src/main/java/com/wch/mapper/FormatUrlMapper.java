package com.wch.mapper;

import com.wch.model.FormatUrl;
import org.apache.ibatis.annotations.*;

public interface FormatUrlMapper {

    @Insert("insert into short_urls (short_url, long_url, create_time, count) values(#{formatUrl.shortUrl}, #{formatUrl.longUrl}, #{formatUrl.createTime}, 0)")
    int insertUrl(@Param("formatUrl") FormatUrl formatUrl);

    @Select("select id, long_url, short_url, count, create_time from short_urls where short_url=#{shortUrl} limit 1")
    @Results({
            @Result(property = "shortUrl", column = "short_url"),
            @Result(property = "longUrl", column = "long_url"),
            @Result(property = "createTime", column = "create_time")
    })
    FormatUrl selectByShortUrl(@Param("shortUrl") String shortUrl);

    @Select("select long_url from short_urls where short_url=#{shortUrl} limit 1")
    String selectLongUrlByShortUrl(@Param("shortUrl") String shortUrl);

    @Select("select count from short_urls where short_url=#{shortUrl} limit 1")
    int selectCount(@Param("shortUrl") String shortUrl);

    @Update("update short_urls set count=count+1 where short_url = #{shortUrl} limit 1")
    int updateUrlCount(@Param("shortUrl") String shortUrl);

    @Select("select id from short_urls order by id DESC limit 1")
    int selectUrlLastId();
}
