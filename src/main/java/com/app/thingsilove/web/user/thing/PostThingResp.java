package com.app.thingsilove.web.user.thing;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostThingResp {
    private Long id;
    private String name;
    private String comment;
    private Long categoryId;
}
