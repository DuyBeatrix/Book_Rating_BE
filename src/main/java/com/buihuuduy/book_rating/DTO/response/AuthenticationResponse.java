package com.buihuuduy.book_rating.DTO.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationResponse
{
    boolean isAuthenticated;

    String token;
}
