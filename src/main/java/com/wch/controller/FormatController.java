package com.wch.controller;

import com.wch.model.APIResponse;
import com.wch.model.FormatUrl;
import com.wch.service.FormatUrlService;
import com.wch.utils.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@ResponseBody
public class FormatController extends BaseController{

    @Autowired
    private FormatUrlService formatUrlService;

    @GetMapping("/format")
    public APIResponse format(@RequestParam(value = "long_url") String longUrl,
                              @RequestParam(value = "short_url") String shortUrl,
                              @RequestParam(value = "type") String type) {

        logger.info("[FormatController] format url! longUrl={}, shortUrl={}, type={}", longUrl, shortUrl, type);

        // type : 1.auto 2.self
        if ("self".equals(type)) {
            // 用户自定义短链
            if (StringUtils.isEmpty(shortUrl)) {
                return APIResponse.error("自定义短链为空！");
            }
            // 检查自定义短链是否可用
            boolean check = formatUrlService.checkShortUrl(shortUrl);
            if (!check) {
                return APIResponse.error("自定义短链不可用，请重新输入或选择自动生成！");
            }
        } else {
            // 系统自动生成
            shortUrl = formatUrlService.formatUrl(longUrl);
        }

        FormatUrl formatUrl = new FormatUrl();
        formatUrl.setCreateTime(new Date());
        formatUrl.setShortUrl(shortUrl);
        formatUrl.setLongUrl(longUrl);

        boolean success = formatUrlService.saveShortUrl(formatUrl);
        return success ? new APIResponse(200, "成功！", "http://localhost:8080/" + shortUrl) : APIResponse.error("出现错误，请重新尝试！");
    }

    @GetMapping("/count")
    public APIResponse getCount(@RequestParam(value = "short_url") String shortUrl) {
        if (StringUtils.isEmpty(shortUrl)) {
            return APIResponse.error("输入短链为空！请重新输入");
        }
        int count = formatUrlService.getRecordCount(shortUrl);
        return new APIResponse(200, "成功", count);
    }

}
