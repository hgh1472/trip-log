package toy.triplog.docs.nation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import toy.triplog.docs.RestDocsSupport;
import toy.triplog.domain.nation.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NationControllerDocsTest extends RestDocsSupport {

    @MockitoBean
    private NationService nationService;

    @DisplayName("나라 정보를 조회한다.")
    @Test
    void getNation() throws Exception {
        // given
        when(nationService.findNation(any())).thenReturn(
                Nation.builder()
                        .id(1L)
                        .name("대한민국")
                        .continent(Continent.ASIA)
                        .build()
        );

        // when then
        mockMvc.perform(
                        get("/nations/{nationId}", 1L)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.name").value("대한민국"))
                .andExpect(jsonPath("$.data.continent").value("ASIA"))
                .andExpect(status().isOk())
                .andDo(document("nation-find",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("nationId").description("나라 ID")),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),

                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("나라"),
                                fieldWithPath("data.name").type(JsonFieldType.STRING).description("나라 이름"),
                                fieldWithPath("data.continent").type(JsonFieldType.STRING).description("나라 대륙")
                        )
                ));
    }

    @DisplayName("나라 정보를 점수와 함께 조회한다.")
    @Test
    void getNationWithAverageScore() throws Exception {
        // given
        when(nationService.findNationWithScore(any())).thenReturn(
                NationWithScore.builder()
                        .nation(Nation.builder()
                                .id(1L)
                                .name("대한민국")
                                .continent(Continent.ASIA)
                                .build())
                        .averageScore(AverageScore.of(4.0, 5.0, 6.0, 7.0))
                        .build()
        );

        // when then
        mockMvc.perform(
                        get("/nations/{nationId}/score", 1L)
                                .with(csrf())
                )
                .andDo(print())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("OK"))
                .andExpect(jsonPath("$.data.nation.name").value("대한민국"))
                .andExpect(jsonPath("$.data.nation.continent").value("ASIA"))
                .andExpect(jsonPath("$.data.score.total").value(5.5))
                .andExpect(jsonPath("$.data.score.food").value(4.0))
                .andExpect(jsonPath("$.data.score.cleanliness").value(5.0))
                .andExpect(jsonPath("$.data.score.safety").value(6.0))
                .andExpect(jsonPath("$.data.score.attraction").value(7.0))
                .andExpect(status().isOk())
                .andDo(document("nation-find-average",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(parameterWithName("nationId").description("나라 ID")),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),

                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("나라"),
                                fieldWithPath("data.nation").type(JsonFieldType.OBJECT).description("나라 정보"),
                                fieldWithPath("data.nation.name").type(JsonFieldType.STRING).description("나라 이름"),
                                fieldWithPath("data.nation.continent").type(JsonFieldType.STRING).description("나라 대륙"),
                                fieldWithPath("data.score").type(JsonFieldType.OBJECT).description("나라 점수"),
                                fieldWithPath("data.score.total").type(JsonFieldType.NUMBER).description("총 평균 점수"),
                                fieldWithPath("data.score.food").type(JsonFieldType.NUMBER).description("음식 점수"),
                                fieldWithPath("data.score.cleanliness").type(JsonFieldType.NUMBER).description("청결 점수"),
                                fieldWithPath("data.score.safety").type(JsonFieldType.NUMBER).description("안전 점수"),
                                fieldWithPath("data.score.attraction").type(JsonFieldType.NUMBER).description("놀거리 점수")
                        )
                ));
    }
}
