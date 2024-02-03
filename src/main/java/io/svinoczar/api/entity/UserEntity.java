package io.svinoczar.api.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("users")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEntity {
    @Id
    private Long id;
    private String username;
    private String password;
    private UserRole userRole;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ToString.Include(name = "password")
    private String maskPassword () {
        return "********";
    }
}
