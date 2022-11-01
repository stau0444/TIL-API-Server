package com.app.thingsilove.web.user.thing;

import com.app.thingsilove.web.user.receipt.PostReceiptResp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ThingDetailResp {
    private Long id;
    private String name;
    private String comment;
    private Integer visitTime;
    private List<PostReceiptResp> receiptList;

}
