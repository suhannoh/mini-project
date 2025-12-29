package com.example.backend.auth.dto.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import org.apache.logging.log4j.message.Message;


@Setter
@Getter
@NoArgsConstructor
public class SignUpRequest {
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "이메일은 필수입니다")
    private String email;

    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 5 , message = "비밀번호는 5자리 이상입니다")
    private String password;

    @NotBlank(message = "이름은 필수입니다")
    private String name;

    private String phone;

    @NotBlank(message = "기본 설정 NONE 제출해주세요")
    private String gender;
}
