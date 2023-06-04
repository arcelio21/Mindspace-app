package com.mindspace.app.model.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AuthenticationUser {

    private String email;
    private String password;
}
