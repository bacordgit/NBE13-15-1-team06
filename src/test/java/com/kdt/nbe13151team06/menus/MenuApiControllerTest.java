package com.kdt.nbe13151team06.menus;

import com.kdt.nbe13151team06.error.MENU_NOT_FOUND;
import com.kdt.nbe13151team06.error.exceptionhandler.ApiExceptionAdvice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MenuController.class)
@Import({ApiExceptionAdvice.class})
public class MenuApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MenuService menuService;

    @Test
    @DisplayName("A08 1000000 초과의 price 설정")
    void over_price_get_returns400() throws Exception{
       String body= """
               {
                 "categoryId": 1,
                 "name": "new drinks",
                 "price": 10000001,
                 "description": "새로운 커피"
               }
               """;
       mockMvc.perform(post("/api/v1/menus")
               .contentType(MediaType.APPLICATION_JSON)
               .content(body))
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
               .andExpect(jsonPath("$.message").exists())
               .andExpect(jsonPath("$.fieldErrors.length()").value(1))
               .andExpect(jsonPath("$.fieldErrors.price").exists());
        then(menuService).shouldHaveNoInteractions();

    }

    @Test
    @DisplayName("A07 없는 메뉴 탐색 MENU_NOT_FOUND(404)")
    void list_returns_404() throws Exception{
        //준비

        given(menuService.findOne(3L)).willThrow(new MENU_NOT_FOUND());
        mockMvc.perform(get("/api/v1/menus/3"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("MENU_NOT_FOUND"))
                .andExpect(jsonPath("$.message").exists());

    }


}
