package com.kdt.nbe13151team06.categories;

import com.kdt.nbe13151team06.categories.domain.Categories;
import com.kdt.nbe13151team06.categories.dto.CategoriesCreateRequest;
import com.kdt.nbe13151team06.categories.dto.CategoriesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    // A01 - 카테고리 목록(GET) - /api/v1/categories
    // DB에서 엔티티 목록을 꺼낸 뒤, 하나씩 응답 DTO로 변환 후 다시 리스트로 모음
    @Transactional(readOnly = true)
    public List<CategoriesResponse> findAll() {
        return categoriesRepository.findAllByOrderByIdAsc() // id 오름차순 조회후 List<Categories>로 반환
                // 리스트 -> 스트림 변경. 스트림은 요소들을 하나씩 흘려보내면서 가공할 수 있게 해 주는 "처리 파이프라인"
                // 이라고 생각하기. 원본 리스트는 바뀌지 않음.
                .stream()
                // map 은 흘러가는 요소 하나하나에 같은 변환 적용. 여기서는 각 Categories 엔티티를 from에 넣어 DTO로 변환함.
                .map(CategoriesResponse::from)
                .toList();

        /*
        List<Categories> categories = categoriesRepository.findAllByOrderByIdAsc();
        List<CategoriesResponse> result = new ArrayList<>();

        for (Categories category : categories) {
            result.add(CategoriesResponse.from(category));
        }
        return result;
        */
    }

    // A02 - 카테고리 상세(GET) - /api/v1/categories/{categoriesId}
    @Transactional(readOnly = true)
    public CategoriesResponse findById(Long id) {
        return CategoriesResponse.from(getCategoryOrThrow(id));
    }

    // 여기부터 하기
    // A03 - 카테고리 등록(POST) - /api/v1/categories
    @Transactional
    public Categories save(CategoriesCreateRequest request) {

        Categories category = this.categoriesRepository.findById(1L).orElseThrow();



        return category;

    }

    // A04 - 카테고리 수정(PUT) - /api/v1/categories/{categoriesId}

    // A05 - 카테고리 삭제(DELETE) - /api/v1/categories/{categoriesId}

    // 공통 조회 메서드
    // id가 없는 경우 -> CATEGORY_NOT_FOUND 커스텀 예외 던짐.
    private Categories getCategoryOrThrow(Long id) {
        // or.ElseThrow(() -> CATEGORY_NOT_FOUND)
        return categoriesRepository.findById(id).orElseThrow();
    }


}
