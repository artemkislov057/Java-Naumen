package urfu.bookingStand.api.dto.user;

import jakarta.validation.constraints.NotBlank;

public class RegisterUserDto {
    @NotBlank
    private String name;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
