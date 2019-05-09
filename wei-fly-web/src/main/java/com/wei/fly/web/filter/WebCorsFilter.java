package com.wei.fly.web.filter;

import com.thetransactioncompany.cors.CORSConfiguration;
import com.thetransactioncompany.cors.CORSFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author Feinik
 * @Discription 跨域顾虑器
 * @Data 2019/2/26
 * @Version 1.0.0
 */
public class WebCorsFilter extends CORSFilter {

    private static final Logger log = LoggerFactory.getLogger(WebCorsFilter.class);

    public WebCorsFilter(CORSConfiguration config) {
        super(config);
    }

    public WebCorsFilter() {
        super();
    }

    @Override
    public void setConfiguration(CORSConfiguration config) {
        super.setConfiguration(config);
    }

    @Override
    public CORSConfiguration getConfiguration() {
        return super.getConfiguration();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("初始化CORS配置...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
