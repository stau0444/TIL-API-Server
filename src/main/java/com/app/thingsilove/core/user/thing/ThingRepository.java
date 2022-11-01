package com.app.thingsilove.core.user.thing;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ThingRepository extends JpaRepository<Thing,Long> ,ThingSearchRepository {

}
