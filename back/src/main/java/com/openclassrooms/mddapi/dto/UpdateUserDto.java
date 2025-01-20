package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "User Update DTO")
public class UpdateUserDto {
    @Size(max = 30)
    @Schema(description = "username", example = "myAlias1|null")
    private String username;

    @Size(min = 6, max = 40)
    @Schema(description = "password", example = "newPassword123|null")
    private String password;

    @Size(max = 36)
    @Schema(description = "picture uuid", example = "90ac3cab|null")
    private String picture;
}
