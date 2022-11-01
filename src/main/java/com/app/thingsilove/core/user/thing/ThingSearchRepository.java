package com.app.thingsilove.core.user.thing;

import com.app.thingsilove.web.user.thing.ThingDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThingSearchRepository {
    List<ThingDto> search(@Param(value = "category") String category,String sort);
}
