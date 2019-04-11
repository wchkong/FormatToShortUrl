package com.wch.service.impl;

import com.wch.mapper.FormatUrlMapper;
import com.wch.mapper.RedirectRecordMapper;
import com.wch.model.FormatUrl;
import com.wch.model.RedirectRecord;
import com.wch.service.FormatUrlService;
import com.wch.utils.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class FormatUrlServiceImpl implements FormatUrlService {

    @Autowired
    private FormatUrlMapper formatUrlMapper;
    @Autowired
    private RedirectRecordMapper redirectRecordMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Value("${short.url.length}")
    private int length;

    @Override
    public boolean checkShortUrl(String shortUrl) {

        FormatUrl formatUrl = formatUrlMapper.selectByShortUrl(shortUrl);
        if (formatUrl != null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean saveShortUrl(FormatUrl formatUrl) {
        // 1.保存到数据库
        int insertDB = formatUrlMapper.insertUrl(formatUrl);

        // 2.保存到redis(保存7天)
        stringRedisTemplate.opsForValue().set(formatUrl.getShortUrl(),
                formatUrl.getLongUrl(),
                3,
                TimeUnit.DAYS);

        // 3.更新当前short——urls表最新id urlsLastId
        stringRedisTemplate.opsForValue().increment("urlsLastId", 1L);

        return insertDB > 0;
    }

    @Override
    public int getRecordCount(String shortUrl) {
        int i = formatUrlMapper.selectCount(shortUrl);
        return i > 0 ? i : 0;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        //查redis
        String longUrl = stringRedisTemplate.opsForValue().get(shortUrl);
        if (StringUtils.isEmpty(longUrl)) {
            // redis中不存在，查db，若记录存在则记录到redis中
            String longUrlFromDB = formatUrlMapper.selectLongUrlByShortUrl(shortUrl);
            if (!StringUtils.isEmpty(longUrl)) {
                stringRedisTemplate.opsForValue().set(shortUrl, longUrlFromDB, 3, TimeUnit.DAYS);
            }
            return longUrlFromDB;
        }
        stringRedisTemplate.expire(shortUrl, 3, TimeUnit.DAYS);
        return longUrl;
    }

    @Override
    public boolean addRecord(String shortUrl, String ip) {
        // 1.保存到Record数据库
        RedirectRecord redirectRecord = new RedirectRecord();
        redirectRecord.setIp(ip);
        redirectRecord.setShortUrl(shortUrl);
        redirectRecord.setCreateTime(new Date());
        int insertDB = redirectRecordMapper.insertRecord(redirectRecord);

        // 2.更新Format表的count值
        int updateDB = formatUrlMapper.updateUrlCount(shortUrl);

        return insertDB > 0 && updateDB > 0;
    }

    @Override
    public String formatUrl(String longUrl) {
        int lastId ;
        String shortUrl;
        String urlsLastIdStr = stringRedisTemplate.opsForValue().get("urlsLastId");
        if (StringUtils.isEmpty(urlsLastIdStr)) {
            // 更新 urlsLastId
            lastId = getUrlLastId();
        } else {
            lastId = Integer.valueOf(urlsLastIdStr);
        }

        // todo 长度配置
        shortUrl = FormatUtils.encode(lastId + 1, length);
        boolean b = checkShortUrl(shortUrl);
        if (b) {
            return shortUrl;
        }

        // todo 长度配置
        return FormatUtils.randomShortUrl(length);
    }


    private int getUrlLastId() {
        int lastId = formatUrlMapper.selectUrlLastId();
        // 不设过期时间
        stringRedisTemplate.opsForValue().set("urlsLastId", String.valueOf(lastId));
        return lastId;
    }

}
