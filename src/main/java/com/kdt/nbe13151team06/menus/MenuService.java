package com.kdt.nbe13151team06.menus;

import com.kdt.nbe13151team06.categories.CategoriesRepository;
import com.kdt.nbe13151team06.categories.domain.Categories;
import com.kdt.nbe13151team06.error.CATEGORY_NOT_FOUND;
import com.kdt.nbe13151team06.error.MENU_IN_USE;
import com.kdt.nbe13151team06.error.MENU_NOT_FOUND;
import com.kdt.nbe13151team06.orderItems.OrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final CategoriesRepository categoriesRepository;
    private final OrderItemsRepository orderItemsRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, CategoriesRepository categoriesRepository, OrderItemsRepository orderItemsRepository) {
        this.menuRepository = menuRepository;
        this.categoriesRepository = categoriesRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    //A08 메뉴 생성
    @Transactional
    public MenuResponse create(MenuCURequest menuCreateRequest, boolean available) {
        Categories category = categoriesRepository.findById(menuCreateRequest.categoryId()).orElseThrow(() -> new CATEGORY_NOT_FOUND());
        Menus menu = new Menus(category, menuCreateRequest.name(), menuCreateRequest.price(), menuCreateRequest.description(), available);
        Menus saved = menuRepository.save(menu);
        return toResponse(saved);

    }

    //A09 메뉴 수정
    @Transactional
    public MenuResponse update(Long menuId, MenuCURequest menuUpdateRequest) {
        Menus menu = menuRepository.findById(menuId).orElseThrow(() -> new MENU_NOT_FOUND());

        menu.setName(menuUpdateRequest.name());
        menu.setPrice(menuUpdateRequest.price());
        menu.setDescription(menuUpdateRequest.description());
        menu.setCategory(categoriesRepository.findById(menuUpdateRequest.categoryId()).orElseThrow(() -> new CATEGORY_NOT_FOUND()));
        menuRepository.save(menu);
        return toResponse(menu);
    }

    //A07 id로 메뉴 조회 찾는 메뉴가 없으면 throw MENU_NOT_FOUND()
    @Transactional(readOnly = true)
    public MenuResponse findOne(Long menuId) {
        Menus menu = menuRepository.findById(menuId).orElseThrow(() -> new MENU_NOT_FOUND());
        return toResponse(menu);
    }

    //A20 이름으로 메뉴 찾기-  모든 메뉴 조회 후 이름이 포함되는지 확인 포함되는 menu들을 반환
    @Transactional(readOnly=true)
    public List<MenuResponse> findByName(String name){
        List<MenuResponse>list=new ArrayList<>();
         for(Menus menu: menuRepository.findAllByOrderByIdAsc()){
             if(menu.getName().contains(name)){
                 list.add(toResponse(menu));
             }
         }
         return list;
    }
    //A06 전체 메뉴 조회- 메뉴들 전체 조회한 후 리스트로 반환
    @Transactional(readOnly=true)
    public List<MenuResponse> findAll() {
        List<MenuResponse> list = new ArrayList<>();
        for (Menus menu : menuRepository.findAllByOrderByIdAsc()) {
            list.add(toResponse(menu));
        }
        return list;
    }
    //A19 available이 true인 메뉴 조회 - 메뉴들 전체 조회한 후 available이 true인 menu 리스트로 반환
    @Transactional(readOnly=true)
    public List<MenuResponse> findByAvailable() {
        List<MenuResponse>list=new ArrayList<>();
        for (Menus menu : menuRepository.findAllByOrderByIdAsc()) {
            if(menu.isAvailable()){
                list.add(toResponse(menu));
            }
        }
        return list;
    }
    //A10 메뉴 삭제 - id로 메뉴를 찾고 없으면 MENU_NOT_FOUND 반환, 있으면 삭제
    @Transactional
    public void delete(Long menuId) {
        Menus menu = menuRepository.findById(menuId).orElseThrow(() -> new MENU_NOT_FOUND());
        if (orderItemsRepository.existsByMenu(menu))
            throw new MENU_IN_USE();
        menuRepository.delete(menu);
    }
    //A21 판매 여부 변경 -id로 메뉴 찾고 request에 따라 판매 여부 변경
    @Transactional
    public MenuResponse changeAvailable(Long menuId,MenuavailableRequest request){
        Menus menu=menuRepository.findById(menuId).orElseThrow(() -> new MENU_NOT_FOUND());
        menu.setAvailable(request.available());
        menuRepository.save(menu);
        return toResponse(menu);
    }

    private MenuResponse toResponse(Menus menu){
    return new MenuResponse(menu.getId(),menu.getCategory().getId(),menu.getName(),menu.getPrice(),menu.getDescription(),menu.isAvailable());
    }

}
