package com.app.thingsilove.core.user.category;


import com.app.thingsilove.core.common.CreatedUpdateDateTime;
import com.app.thingsilove.core.user.thing.ThingRepository;
import com.app.thingsilove.core.user.NotExistUserException;
import com.app.thingsilove.core.user.User;
import com.app.thingsilove.core.user.UserRepository;
import com.app.thingsilove.web.user.category.PostCategoryReq;
import com.app.thingsilove.web.user.category.PostCategoryResp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final ThingRepository thingRepository;
    @Transactional
    public PostCategoryResp addCategory(PostCategoryReq req){
        Optional<User> opUser = userRepository.findByEmail(req.getUserEmail());
        User user = opUser.orElseThrow(NotExistUserException::new);
        Category savedCategory = categoryRepository.save(
                Category
                        .builder()
                        .name(req.getCategory())
                        .user(user)
                        .userDateTime(new CreatedUpdateDateTime())
                        .build()
        );
        return PostCategoryResp
                .builder()
                .id(savedCategory.getId())
                .name(savedCategory.getName())
                .build();
    }


    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
