package com.kdt.nbe13151team06.categories;

import com.kdt.nbe13151team06.JpaAuditingConfig;
import com.kdt.nbe13151team06.categories.domain.Categories;
import com.kdt.nbe13151team06.error.exceptionhandler.ApiExceptionAdvice;
import com.kdt.nbe13151team06.menus.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriesController.class)
@Import({CategoriesService.class, JpaAuditingConfig.class, ApiExceptionAdvice.class})
public class CategoriesApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean private CategoriesRepository categoriesRepository;
    @MockitoBean private MenuRepository menuRepository;

    // data 추가
    private Categories category(Long id, String name) {
        Categories categories = new Categories(name);
        // ReflectionTestUtils - 단위/통합 테스트 시 사용, private 메서드, 생성자 등에 강제 접근하여 설정함.
        ReflectionTestUtils.setField(categories, "id", id);
        return categories;
    }

    @Test
    @DisplayName("A01 - 카테고리 목록 조회, 200, ID 오름차순 정렬")
    void findAll_200() throws Exception {
        given(this.categoriesRepository.findAllByOrderByIdAsc())
                .willReturn(List.of(category(1L, "싱글오리진"), category(2L, "블렌드")));

        this.mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("싱글오리진"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("블렌드"));
    }

    // 여기부터 시작하기
    @Test
    @DisplayName("A05 - 카테고리 삭제, 204, 빈 본문")
    void delete_204() throws Exception {
        given(this.categoriesRepository.findById(3L)).willReturn(Optional.of(category(3L, "디카페인")));
//        given(this.menuRepository.existsByCategoriesId(1L)).willReturn(false);

        mockMvc.perform(delete("/api/v1/categories/3"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

    }


    @Test
    @DisplayName("A02 - 카테고리 상세 조회, 200 OK")
    void findById_200_OK(Long id) throws Exception {

    }


}
