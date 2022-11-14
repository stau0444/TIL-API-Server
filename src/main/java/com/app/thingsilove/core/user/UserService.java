package com.app.thingsilove.core.user;

import com.app.thingsilove.core.user.category.Category;
import com.app.thingsilove.core.user.category.CategoryRepository;
import com.app.thingsilove.core.user.category.NotExistCategoryException;
import com.app.thingsilove.core.common.CreatedUpdateDateTime;
import com.app.thingsilove.core.user.receipt.Receipt;
import com.app.thingsilove.core.user.receipt.ReceiptRepository;
import com.app.thingsilove.core.user.thing.*;
import com.app.thingsilove.web.user.LoginReq;
import com.app.thingsilove.web.user.LoginResp;
import com.app.thingsilove.web.user.SignUpReq;
import com.app.thingsilove.web.user.category.CategoryDto;
import com.app.thingsilove.web.user.receipt.PostReceiptReq;
import com.app.thingsilove.web.user.receipt.PostReceiptResp;
import com.app.thingsilove.web.user.thing.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ThingRepository thingRepository;
    private final ReceiptRepository receiptRepository;

    private final UserAuthRepository userAuthRepository;
    private final PasswordEncoder encoder;

    @Transactional
    public void saveUser(SignUpReq signUpReq){
        Optional<User> isExist = userRepository.findByEmail(signUpReq.getEmail());
        isExist.ifPresent(user -> {throw new UserExistException();});

        User newUser = User.builder()
                .email(signUpReq.getEmail())
                .password(encoder.encode(signUpReq.getPassword()))
                .userDateTime(new CreatedUpdateDateTime())
                .enabled(true)
                .build();

        User savedUser = userRepository.save(newUser);
        Set<UserAuthority> userRole = Set.of(userAuthRepository.save(new UserAuthority(savedUser.getId(), "ROLE_USER")));
        newUser.setAuthorities(userRole);
    }

//    @Transactional(readOnly = true)
//    public LoginResp login(LoginReq loginReq){
//
//        Optional<User> byEmail = userRepository.findByEmail(loginReq.getEmail());
//        User user = byEmail.orElseThrow(LoginFailException::new);
//
//        LoginResp loginResp = new LoginResp();
//        loginResp.setEmail(user.getEmail());
//        loginResp.setCreatedAt(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm").format(user.getUserDateTime().getCreatedAt()));
//        loginResp.setThings(
//                user.getThingList().stream().map(t->
//                        ThingDto.builder()
//                                .id(t.getId())
//                                .categoryId(t.getCategory().getId())
//                                .comment(t.getMyComment())
//                                .name(t.getName())
//                                .build()
//                ).collect(Collectors.toList())
//        );
//        loginResp.setCategories(
//                user.getCategoryList().stream().map(c ->
//                        CategoryDto.builder()
//                                .id(c.getId())
//                                .name(c.getName())
//                                .build()
//                ).collect(Collectors.toList())
//        );
//        return loginResp;
//    }

    public PostThingResp addThing(PostThingReq thingReq) {
        Category category = categoryRepository.findById(thingReq.getCategoryId()).orElseThrow(NotExistCategoryException::new);
        User user = userRepository.findByEmail(thingReq.getEmail()).orElseThrow(NotExistUserException::new);

        Thing newThing = thingRepository.save(
                Thing.builder()
                        .category(category)
                        .receiptList(new ArrayList<>())
                        .name(thingReq.getName())
                        .myComment(thingReq.getComment())
                        .user(user)
                        .userDateTime(new CreatedUpdateDateTime())
                        .build()
        );

        return PostThingResp
                .builder()
                .id(newThing.getId())
                .categoryId(thingReq.getCategoryId())
                .comment(newThing.getMyComment())
                .name(newThing.getName())
                .build();
    }


    @Transactional(readOnly = true)
    public ThingDetailResp findThing(Long id) {

        Thing thing = thingRepository.findById(id).orElseThrow(NotExistThingException::new);

        return ThingDetailResp.builder()
                .comment(thing.getMyComment())
                .id(thing.getId())
                .name(thing.getName())
                .receiptList(thing.getReceiptList().stream().map(
                        r->new PostReceiptResp(
                                r.getId(),
                                r.getName(),
                                r.getMenuList(),
                                r.getComment(),
                                thing.getId(),
                                DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm").format(r.getUserDateTime().getCreatedAt())
                        )
                ).collect(Collectors.toList()))
                .visitTime(thing.getReceiptList().size())
                .build();
    }

    @Transactional
    public PostReceiptResp addReceipt(PostReceiptReq req) {
        Optional<Thing> byId = thingRepository.findById(req.getThingId());
        Thing thing = byId.orElseThrow(NotExistThingException::new);
        Receipt receipt = new Receipt(
                req.getName(),
                req.getComment(),
                new CreatedUpdateDateTime(),
                thing,
                req.getMenuList()
                );
        Receipt save = receiptRepository.save(receipt);

        return PostReceiptResp
                .builder()
                .id(save.getId())
                .thingName(thing.getName())
                .createdAt(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm").format(save.getUserDateTime().getCreatedAt()))
                .comment(save.getComment())
                .menuList(save.getMenuList())
                .thingId(thing.getId())
                .build();
    }

    public List<ThingDto> search(String category, String sort,String email) {
        List<ThingDto> searchList = thingRepository.search(category, sort ,email);
        for (ThingDto thing : searchList) {
            System.out.println("thing = " + thing);
        }
        return searchList;
    }


}
