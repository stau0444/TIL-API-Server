package com.app.thingsilove.web.user.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCategoryReq {
    private String category;
    private String userEmail;
}
