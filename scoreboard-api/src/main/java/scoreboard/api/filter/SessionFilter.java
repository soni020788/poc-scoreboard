package scoreboard.api.filter;

import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Singleton
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        setSessionInvalidationParam(httpServletRequest);
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, OPTIONS");
        httpServletResponse.setHeader("Access-Control-Allow-Max-Age", "3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "DNT, X-CustomHeader, Keep-Alive, User-Agent, X-Requested-With, If-Modified-Since, Cache-Control, Content-Type, Content-Range, Range");
        httpServletResponse.setHeader("Access-Control-Allow-Exposed-Headers", "DNT, X-CustomHeader, Keep-Alive, User-Agent, X-Requested-With, If-Modified-Since, Cache-Control, Content-Type, Content-Range, Range");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void setSessionInvalidationParam(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.setMaxInactiveInterval(60 * 60); // 1 hr
    }

    @Override
    public void destroy() {

    }
}
