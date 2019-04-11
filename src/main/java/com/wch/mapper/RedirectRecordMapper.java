package com.wch.mapper;

import com.wch.model.FormatUrl;
import com.wch.model.RedirectRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface RedirectRecordMapper {

    @Insert("insert into url_record (ip, short_url, create_time) values(#{redirectRecord.ip}, #{redirectRecord.shortUrl}, #{redirectRecord.createTime})")
    int insertRecord(@Param("redirectRecord") RedirectRecord redirectRecord);
}
