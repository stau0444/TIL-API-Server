package com.app.thingsilove.web.user;

import com.app.thingsilove.core.user.category.CategoryService;
import com.app.thingsilove.web.user.category.DeleteCategoryResp;
import com.app.thingsilove.web.user.category.PostCategoryReq;
import com.app.thingsilove.web.user.category.PostCategoryResp;
import com.app.thingsilove.core.user.*;
import com.app.thingsilove.web.user.receipt.PostReceiptReq;
import com.app.thingsilove.web.user.receipt.PostReceiptResp;
import com.app.thingsilove.web.user.thing.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;
    private final CategoryService categoryService;

    @PostMapping("")
    public void saveUser(@RequestBody SignUpReq signUpReq){
        userService.saveUser(signUpReq);
    }

//    @GetMapping(value = "/login")
//    public ResponseEntity<LoginResp> login(String email) {
//        System.out.println("passed /login controller ");
//        LoginResp loginResp = userService.login(LoginReq.builder().email(email).build());
//        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(loginResp);
//    }


    @PostMapping("/thing")
    public ResponseEntity<PostThingResp> addThing(@RequestBody PostThingReq thingReq){
        return ResponseEntity.ok().body(userService.addThing(thingReq));
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<ThingDetailResp> getContent(@PathVariable(name = "id") Long id){
        return  ResponseEntity.ok().body(userService.findThing(id));
    }

    @PostMapping("/category")
    public ResponseEntity<PostCategoryResp> addUserCategory(@RequestBody PostCategoryReq req){
        return ResponseEntity.ok().body(categoryService.addCategory(req));
    }
    @DeleteMapping("/category/{id}")
    public ResponseEntity<DeleteCategoryResp> deleteUserCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(DeleteCategoryResp.builder().id(id).build());
    }
    @PostMapping("/receipt")
    public ResponseEntity<PostReceiptResp> addReceipt(@RequestBody PostReceiptReq req){
        return ResponseEntity.ok().body(userService.addReceipt(req));
    }

    @GetMapping("/content/search")
    public ResponseEntity<GetSearchResp> search(String category , String sort){
        return ResponseEntity.ok().body(new GetSearchResp(userService.search(category,sort)));
    }
    @GetMapping("/api/user/sessionAuthFail")
    public ResponseEntity sessionAuthFail(){
        return ResponseEntity.ok().body("session-auth-failed");
    }
}
