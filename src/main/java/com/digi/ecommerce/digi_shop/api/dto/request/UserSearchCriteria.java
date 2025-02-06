package com.digi.ecommerce.digi_shop.api.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchCriteria {
    private String firstName;
    private String lastName;
    private String email;

}
