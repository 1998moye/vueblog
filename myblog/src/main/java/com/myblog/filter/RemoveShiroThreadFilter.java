package com.myblog.filter;

import org.apache.shiro.util.ThreadContext;

import javax.servlet.*;
import java.io.IOException;

public class RemoveShiroThreadFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try{
            filterChain.doFilter(servletRequest, servletResponse);
        }finally {
            ThreadContext.remove();
        }
    }
}
