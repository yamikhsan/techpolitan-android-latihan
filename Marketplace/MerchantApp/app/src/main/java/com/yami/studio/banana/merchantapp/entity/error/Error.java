package com.yami.studio.banana.merchantapp.entity.error;

import java.util.List;

public class Error{

    private List<String> first_name;
    private List<String> last_name;
    private List<String> email;
    private List<String> password;
    private List<String> confirm_password;

    public Error(List<String> first_name, List<String> last_name, List<String> email, List<String> password, List<String> confirm_password) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
        this.confirm_password = confirm_password;
    }

    public String getFirst_name() {
        return first_name.get(0);
    }

    public String getLast_name() {
        return last_name.get(0);
    }

    public String getEmail() {
        return email.get(0);
    }

    public String getPassword() {
        return password.get(0);
    }

    public String getConfirm_password() {
        return confirm_password.get(0);
    }
}

