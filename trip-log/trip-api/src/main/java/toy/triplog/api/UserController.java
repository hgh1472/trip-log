package toy.triplog.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toy.triplog.domain.user.User;
import toy.triplog.domain.user.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ApiResponse<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        User user = userService.createUser(signUpRequest.toUserSignInfo(), signUpRequest.toUserDetailInfo());
        return ApiResponse.ok(SignUpResponse.from(user));
    }

}
