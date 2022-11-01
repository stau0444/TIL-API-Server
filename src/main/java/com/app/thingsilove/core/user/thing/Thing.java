package com.app.thingsilove.core.user.thing;

import com.app.thingsilove.core.user.category.Category;
import com.app.thingsilove.core.common.CreatedUpdateDateTime;
import com.app.thingsilove.core.user.User;
import com.app.thingsilove.core.user.receipt.Receipt;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Thing{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "thing_id")
    private Long id;

    private String name;

    private String myComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "thing" , cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Receipt> receiptList;

    private int visitTime;

    private LocalDateTime lastVisitDateTime;
    @Embedded
    private CreatedUpdateDateTime userDateTime;

    public Thing(String name, String myComment, Category category, User user, List<Receipt> receiptList, CreatedUpdateDateTime userDateTime) {
        this.name = name;
        this.myComment = myComment;
        this.category = category;
        this.user = user;
        this.receiptList = receiptList;
        this.userDateTime = userDateTime;
    }

    @Override
    public String toString() {
        return "Thing{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", myComment='" + myComment + '\'' +
                ", category=" + category +
                ", user=" + user +
                ", receiptList=" + receiptList +
                ", userDateTime=" + userDateTime +
                '}';
    }
}

