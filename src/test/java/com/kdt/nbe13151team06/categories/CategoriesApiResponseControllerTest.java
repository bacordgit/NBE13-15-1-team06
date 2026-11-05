package com.kdt.nbe13151team06.categories;

import com.kdt.nbe13151team06.categories.dto.CategoriesResponse;
import com.kdt.nbe13151team06.menus.MenuRepository;
import com.kdt.nbe13151team06.menus.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriesController.class)
//@Import(ApiExceptionAdvice.class)
public class CategoriesApiResponseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean private CategoriesService categoriesService;
    @MockitoBean private MenuRepository menuRepository;

    @Test
    @DisplayName("A01 - 카테고리 목록 조회, 200, Category[], ID 오름차순 정렬")
    void list_findAll_200() throws Exception {
        // Arrange(준비)
        List<CategoriesResponse> rows = new ArrayList<>();
        rows.add(new CategoriesResponse(1L, "싱글오리진"));
        rows.add(new CategoriesResponse(2L, "블렌드"));
        rows.add(new CategoriesResponse(3L, "디카페인"));

        // Stub
        given(this.categoriesService.findAll()).willReturn(rows);

        // Act(실행)
        this.mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))

                .andExpect(jsonPath("$").isArray()) // 배열 여부

                // 값 확인
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("싱글오리진"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("블렌드"))
                .andExpect(jsonPath("$[2].id").value(3))
                .andExpect(jsonPath("$[2].name").value("디카페인"))

                // id 1 의 필드 요소는 id와 name
                .andExpect(jsonPath("$[0].length()").value(2))
                // 배열 길이
                .andExpect(jsonPath("$.length()").value(3));

        // Assert(검증)
        verify(this.categoriesService).findAll();
    }

    @Test
    @DisplayName("A02 - 카테고리 상세 조회, 200, Category")
    void category_findOne_200() throws Exception {
        // Arrange
        CategoriesResponse row = new CategoriesResponse(3L, "디카페인");

        // Stub
        given(this.categoriesService.findById(3L)).willReturn(row);

        // Act
        this.mockMvc.perform(get("/api/v1/categories/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.name").value("디카페인"))
                .andExpect(jsonPath("$.length()").value(2));

        // Assert
        verify(this.categoriesService).findById(3L);

    }

    @Test
    @DisplayName("A05 - 카테고리 삭제, 204, 빈 본문")
    void category_delete_204() throws Exception {
        // Arrange
        CategoriesResponse row = new CategoriesResponse(3L, "디카페인");

        // Stub
        given(this.categoriesService.findById(3L)).willReturn(row);
        given(this.menuRepository.existsByCategoriesId(3L)).willReturn(false);

        // Act
        this.mockMvc.perform(delete("/api/v1/categories/3"))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        // Assert
        verify(this.categoriesService).delete(3L);
    }

}
