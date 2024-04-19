package Model.DTO;

import java.util.UUID;

public class Account {

    //region [ - Fields - ]
    private UUID id;
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
            this.email = email;
    }

    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
            this.password = password;
    }

    private Boolean hasLoggedIn;
    public Boolean getHasLoggedIn() {
        return hasLoggedIn;
    }
    public void setHasLoggedIn(Boolean hasLoggedIn) {
        this.hasLoggedIn = hasLoggedIn;
    }

    private String information;
    public String getInformation() {
        return String.format("%s;%s;%s;false;\n",id, email, password);
    }

    //endregion

    //region [ - Constructor - ]

    //region [ - Account() - ]
    public Account() {
        id = UUID.randomUUID();
        hasLoggedIn = false;
    }
    //endregion

    //region [ - Account(UUID id, String email, String password) - ]
    public Account(UUID id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        hasLoggedIn = false;
    }
    //endregion

    //region [ - Account(String email, String password) - ]
    public Account(String email, String password) {
        id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        hasLoggedIn = false;
    }
    //endregion

    //endregion

}
