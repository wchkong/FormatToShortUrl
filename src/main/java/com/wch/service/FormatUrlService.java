package com.wch.service;

import com.wch.model.FormatUrl;

public interface FormatUrlService {

    /**
     * 检查短链是否可用
     * @param shortUrl
     * @return
     */
    boolean checkShortUrl(String shortUrl);

    /**
     * 保存短链对象
     * @param formatUrl
     * @return
     */
    boolean saveShortUrl(FormatUrl formatUrl);

    /**
     * 获取短链访问次数
     * @param shortUrl
     * @return
     */
    int getRecordCount(String shortUrl);

    /**
     * 通过短连接获取长连接
     * @param shortUrl
     * @return
     */
    String getLongUrl(String shortUrl);

    /**
     * 访问记录和次数统计更新
     * @param shortUrl
     * @param ip
     * @return
     */
    boolean addRecord(String shortUrl, String ip);

    /**
     * 生成短链接
     * @param longUrl
     * @return
     */
    String formatUrl(String longUrl);
}
