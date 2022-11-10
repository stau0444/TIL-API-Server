package com.app.thingsilove.web.common.security;

import com.app.thingsilove.core.user.NotExistUserException;
import com.app.thingsilove.core.user.User;
import com.app.thingsilove.core.user.UserRepository;
import com.app.thingsilove.core.user.UserService;
import com.app.thingsilove.web.user.LoginReq;
import com.app.thingsilove.web.user.LoginResp;
import com.app.thingsilove.web.user.category.CategoryDto;
import com.app.thingsilove.web.user.thing.ThingDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final UserRepository userRepository;
    private final ObjectMapper objMapper;

    @Transactional
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        User userDetail = (User) authentication.getPrincipal();
        Optional<User> byEmail = userRepository.findByEmail(userDetail.getEmail());
        response.getOutputStream().write(objMapper.writeValueAsBytes(buildLoginResp(byEmail.orElseThrow(NotExistUserException::new))));
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        if (savedRequest == null) {
            clearAuthenticationAttributes(request);
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() ||
                (targetUrlParam != null &&
                        StringUtils.hasText(request.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            return;
        }
        clearAuthenticationAttributes(request);
    }

    private LoginResp buildLoginResp(User user){
        LoginResp loginResp = new LoginResp();
        loginResp.setEmail(user.getEmail());
        loginResp.setCreatedAt(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm").format(user.getUserDateTime().getCreatedAt()));
        loginResp.setThings(
                user.getThingList().stream().map(t->
                        ThingDto.builder()
                                .id(t.getId())
                                .categoryId(t.getCategory().getId())
                                .comment(t.getMyComment())
                                .name(t.getName())
                                .build()
                ).collect(Collectors.toList())
        );
        loginResp.setCategories(
                user.getCategoryList().stream().map(c ->
                        CategoryDto.builder()
                                .id(c.getId())
                                .name(c.getName())
                                .build()
                ).collect(Collectors.toList())
        );
        return loginResp;
    }
}
