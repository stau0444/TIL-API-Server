package com.app.thingsilove.web.user.thing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostThingReq {
    private String name;
    private Long categoryId;
    private String comment;
    private String email;
}
