package io.svinoczar.api.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("${user.table-name}")
@Builder(toBuilder = true)
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEntity {
    @Id
    private Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private UserRole userRole;

    private String firstName;
    private String lastName;
    private String email;

    private Float xp;
    private Integer level;

    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ToString.Include(name = "password")
    private String maskPassword () {
        return "********";
    }
}
