package io.svinoczar.api.entity;

import io.svinoczar.api.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder ( toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private UserDTO user;
}
