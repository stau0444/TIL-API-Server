package com.app.thingsilove.web.user;

import com.app.thingsilove.web.user.category.CategoryDto;
import com.app.thingsilove.web.user.thing.ThingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResp{
    private String email;
    private String createdAt;
    private List<CategoryDto> categories;
    private List<ThingDto> things;
}



