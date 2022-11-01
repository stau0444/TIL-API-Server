package com.app.thingsilove.core.user.thing;

import com.app.thingsilove.core.user.category.QCategory;
import com.app.thingsilove.web.user.thing.QThingDto;
import com.app.thingsilove.web.user.thing.ThingDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

import static com.app.thingsilove.core.user.category.QCategory.category;
import static com.app.thingsilove.core.user.thing.QThing.thing;

@RequiredArgsConstructor
public class ThingSearchRepositoryImpl implements ThingSearchRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ThingDto> search(String categoryName,String sort) {
        OrderSpecifier sortQuery = null;
        switch (sort){
            case "byName":
                sortQuery = thing.name.asc();
                break;
            case "visitTime":
                sortQuery = thing.visitTime.desc();
                break;
            case "recently":
                sortQuery = thing.lastVisitDateTime.desc();
                break;
            default:
                sortQuery = thing.name.asc();
                break;
        }
        if(!Objects.equals(categoryName, "all")){
            return queryFactory.select(new QThingDto(thing.id,thing.name,thing.myComment,thing.category.id))
                    .from(thing)
                    .innerJoin(category)
                    .on(thing.category.id.eq(category.id))
                    .where(category.name.eq(categoryName))
                    .orderBy(
                            sortQuery
                    ).fetch();
        }
        return queryFactory.select(new QThingDto(thing.id,thing.name,thing.myComment,thing.category.id))
                .from(thing)
                .orderBy(
                        sortQuery
                ).fetch();
    }
}
