package com.app.thingsilove.web.user.receipt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostReceiptReq {
    private String name;
    private List<String> menuList;
    private String comment;
    private Long thingId;
}
