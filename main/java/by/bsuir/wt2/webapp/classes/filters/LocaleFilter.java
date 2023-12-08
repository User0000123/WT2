package by.bsuir.wt2.webapp.classes.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * The LocaleFilter class is a filter that sets the language/locale for the current session.
 * It implements the Filter interface and overrides the doFilter method.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class LocaleFilter implements Filter {
    /**
     * Sets the language/locale for the current session based on the "locale" parameter in the request.
     * If the "locale" parameter is present, it sets the "lang" attribute in the session to the specified locale.
     * Then it continues the filter chain by invoking the next filter in the chain.
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
        if(httpRequest.getParameter("locale") != null){
            session.setAttribute("lang", httpRequest.getParameter("locale"));
        }
        chain.doFilter(request,response);
    }
}
