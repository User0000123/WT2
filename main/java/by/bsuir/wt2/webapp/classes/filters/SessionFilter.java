package by.bsuir.wt2.webapp.classes.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


/**
 * The SessionFilter class is a filter that manages user sessions.
 * It implements the Filter interface and overrides the doFilter method.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class SessionFilter implements Filter {
    /**
     * Manages user sessions by setting session attributes and redirecting to the main page.
     * If the session is new, it sets the maximum inactive interval, "log" attribute, and "locale" attribute.
     * It also sets other session attributes to null.
     * Then it redirects the user to the main page.
     * Finally, it continues the filter chain by invoking the next filter in the chain.
     *
     * @param request the ServletRequest object
     * @param response the ServletResponse object
     * @param chain the FilterChain object
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        if(session.isNew()){
            session.setMaxInactiveInterval(300 * 60);
            session.setAttribute("log", false);
            httpRequest.setAttribute("locale","en");
            session.setAttribute("courses",null);
            session.setAttribute("orders",null);
            session.setAttribute("input_error",null);
            httpResponse.sendRedirect("/pages/main.jsp");
        }
        chain.doFilter(request,response);
    }
}
