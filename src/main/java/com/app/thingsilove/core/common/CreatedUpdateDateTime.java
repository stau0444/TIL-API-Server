package com.app.thingsilove.core.common;


import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
public class CreatedUpdateDateTime {
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public CreatedUpdateDateTime() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
