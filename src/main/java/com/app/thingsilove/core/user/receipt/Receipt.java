package com.app.thingsilove.core.user.receipt;

import com.app.thingsilove.core.common.CreatedUpdateDateTime;
import com.app.thingsilove.core.user.thing.Thing;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Receipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long id;
    private String name;
    private String comment;

    @Embedded
    private CreatedUpdateDateTime userDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "thing_id")
    private Thing thing;

    @ElementCollection
    @CollectionTable(
            name="receipt_menu",
            joinColumns = @JoinColumn(name = "receipt_id")
    )
    @Column(name = "receipt_menu")
    private List<String> menuList;

    public Receipt(String name, String comment, CreatedUpdateDateTime userDateTime, Thing thing, List<String> menuList) {
        this.name = name;
        this.comment = comment;
        this.userDateTime = userDateTime;
        this.thing = thing;
        this.menuList = menuList;
        this.thing.setVisitTime(this.thing.getVisitTime()+1);
        this.thing.setLastVisitDateTime(userDateTime.getCreatedAt());
    }
}
