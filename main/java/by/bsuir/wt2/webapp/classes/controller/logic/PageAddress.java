package by.bsuir.wt2.webapp.classes.controller.logic;


/**
 * This class represents the page's name and redirection method
 * It contains name of page and condition for availability by sendRedirect() or forward()
 *
 * @author Aleksej
 * @since 2023-12-07
 * @version 1.0
 */
public class PageAddress {

    private String name;

    private boolean isRedirect;

    public PageAddress(){

    }

    public PageAddress(String name, boolean isRedirect){
        this.name = name;
        this.isRedirect = isRedirect;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }
}
