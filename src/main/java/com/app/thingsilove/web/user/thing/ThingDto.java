package com.app.thingsilove.web.user.thing;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

@Data
@Builder
@ToString
public class ThingDto {
    private Long id;
    private String name;
    private String comment;
    private Long categoryId;

    @QueryProjection
    public ThingDto(Long id, String name, String comment, Long categoryId) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.categoryId = categoryId;
    }
}
