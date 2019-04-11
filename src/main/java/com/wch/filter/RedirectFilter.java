package com.wch.filter;

import com.wch.model.FormatUrl;
import com.wch.service.FormatUrlService;
import com.wch.utils.FormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Component
public class RedirectFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(RedirectFilter.class);

    @Autowired
    private FormatUrlService formatUrlService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI().substring(1);
        String IP = FormatUtils.getIP(httpServletRequest);

        String longUrl = formatUrlService.getLongUrl(requestURI);

        if (StringUtils.isEmpty(longUrl)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            //添加访问记录和更新count操作
            try {
                //用线程池执行更新逻辑更合理
                formatUrlService.addRecord(requestURI, IP);
            } catch (Exception error) {
                logger.error("[RedirectFilter] addRecord error:", error);
            } finally {
                //出现异常的情况下也会跳转
                HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
                httpServletResponse.sendRedirect(longUrl);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
