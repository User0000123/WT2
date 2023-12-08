package by.bsuir.wt2.webapp.classes.controller.logic;


/**
 * This class represents the list of pages
 * It contains static PageName's instances
 *
 * @author Aleksej
 * @since 2023-12-07
 * @version 1.0
 */
public class PageAddresses {
    public static final PageAddress MAIN_PAGE = new PageAddress("/pages/main.jsp",true);

    public static final PageAddress ERROR_PAGE = new PageAddress("/pages/error.jsp",true);

    public static final PageAddress LOGIN_PAGE = new PageAddress("/pages/login.jsp",true);

    public static final PageAddress REGISTER_PAGE = new PageAddress("/pages/register.jsp", true);

    public static final PageAddress MOVIE_ENTITY_CREATION = new PageAddress(
            "/pages/course/movieCreation.jsp", true);

    public static final PageAddress MOVIE_EDIT = new PageAddress("/pages/course/movieEdit.jsp", true);

    public static final PageAddress ORDER_ACCEPT = new PageAddress("/pages/order/orderAccept.jsp", true);

    public static final PageAddress ORDER_CREATION = new PageAddress("/pages/order/orderCreation.jsp", true);
    public static final PageAddress USER_EDIT = new PageAddress("pages/user/userEdit.jsp", true);
    public static final PageAddress USER_ORDERS = new PageAddress("/pages/user/userOrders.jsp", true);
    public static final PageAddress MOVIE_VIEW = new PageAddress("/pages/course/movieView.jsp", true);
}
