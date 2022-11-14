package com.app.thingsilove.web.user.thing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetSearchReq {
    private String category;
    private String sort;
    private String email;
}
