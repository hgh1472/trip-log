package toy.api.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import toy.triplog.api.SignUpRequest;
import toy.triplog.api.UserController;
import toy.triplog.domain.user.User;
import toy.triplog.domain.user.UserService;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    @DisplayName("유저가 회원가입한다.")
    @Test
    @WithMockUser
    void signUp() throws Exception {
        // given
        when(userService.createUser(any(), any())).thenReturn(
                User.builder()
                        .id(1L)
                        .email("email@email.com")
                        .password("password")
                        .nickname("nickname")
                        .build()
        );

        SignUpRequest signUpRequest = new SignUpRequest("email@email.com", "password", "nickname");

        // when then
        mockMvc.perform(
                        post("/sign-up")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(signUpRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(status().isOk());
    }

    @DisplayName("비밀번호를 기입하지 않을 경우, 회원가입에 실패한다.")
    @Test
    @WithMockUser
    void signUpWithNonEmail() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("email", "password", "nickname");

        // when then
        mockMvc.perform(
                        post("/sign-up")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(signUpRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("이메일 형식이어야 합니다."))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("패스워드를 입력하지 않을 경우, 회원가입에 실패한다.")
    @Test
    @WithMockUser
    void signUpWithEmptyPassword() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("email@email.com", "", "nickname");

        // when then
        mockMvc.perform(
                        post("/sign-up")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(signUpRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("패스워드를 입력해주세요."))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("닉네임을 입력하지 않을 경우, 회원가입에 실패한다.")
    @Test
    @WithMockUser
    void signUpWithEmptyNickname() throws Exception {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("email@email.com", "password", "");

        // when then
        mockMvc.perform(
                        post("/sign-up")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(signUpRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.message").value("닉네임을 입력해주세요."))
                .andExpect(status().isBadRequest());
    }

}