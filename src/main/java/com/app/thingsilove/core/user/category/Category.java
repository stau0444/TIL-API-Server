package com.app.thingsilove.core.user.category;

import com.app.thingsilove.core.user.thing.Thing;
import com.app.thingsilove.core.common.CreatedUpdateDateTime;
import com.app.thingsilove.core.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Thing> thingList;
    @Embedded
    private CreatedUpdateDateTime userDateTime;

    public Category(String name, User user, CreatedUpdateDateTime userDateTime) {
        this.name = name;
        this.user = user;
        this.userDateTime = userDateTime;
    }
}
