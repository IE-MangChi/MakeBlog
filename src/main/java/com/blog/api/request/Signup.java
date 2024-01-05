package com.blog.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
public class Signup {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    public Signup() {}

    @Builder
    public Signup(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
