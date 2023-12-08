package by.bsuir.wt2.webapp.classes.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Order class represents an order.
 * It includes properties such as ID, creation date, summary price, and acceptance status.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class Order {

    private int id;
    private Date creationDate;

    private double summaryPrice;

    private boolean isAccepted;

    public Order() {

    }

    public Order(int id,Date creationDate, double summaryPrice, boolean isAccepted) {
        this.id = id;
        this.creationDate = creationDate;
        this.summaryPrice = summaryPrice;
        this.isAccepted = isAccepted;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public double getSummaryPrice() {
        return summaryPrice;
    }

    public void setSummaryPrice(double summaryPrice) {
        this.summaryPrice = summaryPrice;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public int getId() {
        return id;
    }

    public void setId(int orderId) {
        this.id = orderId;
    }


}
