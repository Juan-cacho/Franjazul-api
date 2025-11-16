package com.franjazul.api.dto;

public class CambioPasswordRequest {

    private String email;
    private String passwordNueva;

    public CambioPasswordRequest() {
    }

    public CambioPasswordRequest(String email, String passwordNueva) {
        this.email = email;
        this.passwordNueva = passwordNueva;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordNueva() {
        return passwordNueva;
    }

    public void setPasswordNueva(String passwordNueva) {
        this.passwordNueva = passwordNueva;
    }
}
