package by.bsuir.wt2.webapp.classes.entities;

/**
 * The Admin class represents an administrator user.
 * It extends the User class and includes additional properties specific to administrators.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class Admin extends User {

    private int id;
    private boolean isActive;

    public Admin() {
        super();
    }

    public Admin(int id,boolean isActive){
        super();
        this.id = id;
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
