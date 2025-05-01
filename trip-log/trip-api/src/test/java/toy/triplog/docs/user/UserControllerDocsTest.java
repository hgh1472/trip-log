package toy.triplog.docs.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import toy.triplog.api.LoginRequest;
import toy.triplog.api.SignUpRequest;
import toy.triplog.docs.RestDocsSupport;
import toy.triplog.domain.auth.TokenInfo;
import toy.triplog.domain.user.User;
import toy.triplog.domain.user.UserService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerDocsTest extends RestDocsSupport {

    @MockitoBean
    private UserService userService;

    @DisplayName("유저가 회원가입한다.")
    @Test
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
                .andExpect(jsonPath("$.data.email").value("email@email.com"))
                .andExpect(jsonPath("$.data.nickname").value("nickname"))
                .andExpect(status().isOk())
                .andDo(document("user-signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),

                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
                                fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("유저 ID"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("유저 이메일"),
                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("유저 닉네임")
                        )
                ));
    }

    @DisplayName("유저가 로그인한다.")
    @Test
    void login() throws Exception {
        // given
        when(userService.login(any())).thenReturn(
                TokenInfo.of("accessToken", "refreshToken")
        );

        LoginRequest loginRequest = new LoginRequest("email@email.com", "password");

        // when then
        mockMvc.perform(
                        post("/login")
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(loginRequest))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(header().string("Authorization", "accessToken"))
                .andExpect(header().string("Set-Cookie", containsString("refreshToken")))
                .andExpect(status().isOk())
                .andDo(document("user-login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseHeaders(
                                headerWithName("Authorization").description("Access Token"),
                                headerWithName("Set-Cookie").description("Refresh Token")
                        )
                ));
    }
}
