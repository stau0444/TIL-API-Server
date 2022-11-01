package com.app.thingsilove.core.user.category;

import com.app.thingsilove.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByUser(User user);
}
