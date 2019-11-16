package com.example.androidbarberstaffapp.Model;

import com.example.androidbarberstaffapp.Common.Common;

public class MyToken {

    private String token, user;
    private Common.TOKEN_TYPE tokenType;

    public MyToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Common.TOKEN_TYPE getTokenType() {
        return tokenType;
    }

    public void setTokenType(Common.TOKEN_TYPE tokenType) {
        this.tokenType = tokenType;
    }
}
