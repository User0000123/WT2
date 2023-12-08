package by.bsuir.wt2.webapp.classes.entities;


/**
 * The Client class represents a client user.
 * It extends the User class and includes additional properties specific to clients.
 *
 * @version 1.0
 * @author Aleksej
 * @since 2023-12-07
 */
public class Client extends User {

    private int id;
    private boolean isBanned;

    public Client() {
        super();
    }

    public Client(int id,boolean isBanned){
        super();
        this.id = id;
        this.isBanned = isBanned;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
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
