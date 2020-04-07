package laborator.networking.dto;

import java.io.Serializable;

public class AgentDTO implements Serializable {
    private String username;
    private String password;


    public AgentDTO(String username, String pass) {
        this.username=username;
        this.password=pass;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AgentDTO["+username+' '+password+"]";
    }
}
