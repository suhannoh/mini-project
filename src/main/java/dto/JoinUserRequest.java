package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class JoinUserRequest {
    private String email;
    private String password;
    private String name;
    private String phone;
}
