package toy.triplog.docs.nation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import toy.triplog.WithCustomMockUser;
import toy.triplog.api.ReviewRequest;
import toy.triplog.docs.RestDocsSupport;
import toy.triplog.domain.auth.ApiUser;
import toy.triplog.domain.nation.PageData;
import toy.triplog.domain.nation.Review;
import toy.triplog.domain.nation.ReviewService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewControllerDocsTest extends RestDocsSupport {

    @MockitoBean
    private ReviewService reviewService;

    @DisplayName("나라의 리뷰를 작성한다.")
    @Test
    @WithCustomMockUser
    void writeReview() throws Exception {
        ReviewRequest reviewRequest = new ReviewRequest(3, 4, 5, 6);
        ApiUser apiUser = new ApiUser(1);
        Long nationId = 1L;

        when(reviewService.addReview(any()))
                .thenReturn(Review.from(reviewRequest.toWrittenReview(nationId, apiUser)));

        mockMvc.perform(
                        post("/nations/{nationId}/reviews", 1L)
                                .with(csrf())
                                .content(objectMapper.writeValueAsString(reviewRequest))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))

                .andExpect(jsonPath("$.data.food").value(reviewRequest.food()))
                .andExpect(jsonPath("$.data.cleanliness").value(reviewRequest.cleanliness()))
                .andExpect(jsonPath("$.data.safety").value(reviewRequest.safety()))
                .andExpect(jsonPath("$.data.attraction").value(reviewRequest.attraction()))
                .andExpect(status().isOk())
                .andDo(document("review-write",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(parameterWithName("nationId").description("나라 ID")),
                                requestFields(
                                        fieldWithPath("food").type(JsonFieldType.NUMBER).description("음식 점수"),
                                        fieldWithPath("cleanliness").type(JsonFieldType.NUMBER).description("청결 점수"),
                                        fieldWithPath("safety").type(JsonFieldType.NUMBER).description("안전 점수"),
                                        fieldWithPath("attraction").type(JsonFieldType.NUMBER).description("놀거리 점수")
                                ),
                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),

                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("리뷰 정보"),
                                        fieldWithPath("data.food").type(JsonFieldType.NUMBER).description("음식 점수"),
                                        fieldWithPath("data.cleanliness").type(JsonFieldType.NUMBER).description("청결 점수"),
                                        fieldWithPath("data.safety").type(JsonFieldType.NUMBER).description("안전 점수"),
                                        fieldWithPath("data.attraction").type(JsonFieldType.NUMBER).description("놀거리 점수")
                                )
                        )
                );
    }

    @DisplayName("나라의 리뷰를 조회한다.")
    @Test
    void getReviews() throws Exception {
        long nationId = 1;
        int page = 1;
        List<Review> reviews = List.of(createReview(nationId, 1, 1, 2, 3, 4),
                createReview(nationId, 2, 3, 4, 5, 6));

        PageData<Review> result = new PageData<>(reviews, page, 1, reviews.size());
        when(reviewService.findReviewsAtPage(nationId, page))
                .thenReturn(result);

        mockMvc.perform(
                        get("/nations/{nationId}/reviews", nationId)
                                .with(csrf().asHeader())
                                .param("page", String.valueOf(page))
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))

                .andExpect(jsonPath("$.data.page").value(result.getPage()))
                .andExpect(jsonPath("$.data.totalPages").value(result.getTotalPages()))
                .andExpect(jsonPath("$.data.totalElements").value(result.getTotalElements()))

                .andExpect(status().isOk())
                .andDo(document("reviews-page",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                pathParameters(parameterWithName("nationId").description("나라 ID")),
                                queryParameters(parameterWithName("page").description("페이지")),

                                responseFields(
                                        fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                        fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),

                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("리뷰 검색 결과"),
                                        fieldWithPath("data.page").type(JsonFieldType.NUMBER).description("현재 페이지"),
                                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지"),
                                        fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("전체 리뷰 수"),
                                        fieldWithPath("data.data").type(JsonFieldType.ARRAY).description("페이지 내 리뷰 목록"),
                                        fieldWithPath("data.data[].food").type(JsonFieldType.NUMBER).description("음식 점수"),
                                        fieldWithPath("data.data[].cleanliness").type(JsonFieldType.NUMBER).description("청결 점수"),
                                        fieldWithPath("data.data[].safety").type(JsonFieldType.NUMBER).description("안전 점수"),
                                        fieldWithPath("data.data[].attraction").type(JsonFieldType.NUMBER).description("놀거리 점수")
                                )
                        )
                );
    }

    private Review createReview(long nationId, long userId, int food, int cleanliness, int safety, int attraction) {
        return Review.builder()
                .id(1L)
                .nationId(nationId)
                .userId(userId)
                .food(food)
                .cleanliness(cleanliness)
                .safety(safety)
                .attraction(attraction)
                .build();
    }

}
