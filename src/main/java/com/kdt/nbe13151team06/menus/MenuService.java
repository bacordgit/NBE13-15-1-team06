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

    @Transactional
    public MenuResponse create(MenuCURequest menuCreateRequest, boolean available) {
        Categories category = categoriesRepository.findById(menuCreateRequest.categoryId()).orElseThrow(() -> new CATEGORY_NOT_FOUND());
        Menus menu = new Menus(category, menuCreateRequest.name(), menuCreateRequest.price(), menuCreateRequest.description(), available);
        Menus saved = menuRepository.save(menu);
        return toResponse(saved);

    }

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

    @Transactional(readOnly = true)
    public MenuResponse findOne(Long menuId) {
        Menus menu = menuRepository.findById(menuId).orElseThrow(() -> new MENU_NOT_FOUND());
        return toResponse(menu);
    }
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

    @Transactional(readOnly=true)
    public List<MenuResponse> findAll() {
        List<MenuResponse> list = new ArrayList<>();
        for (Menus menu : menuRepository.findAllByOrderByIdAsc()) {
            list.add(toResponse(menu));
        }
        return list;
    }
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

    @Transactional
    public void delete(Long menuId) {
        Menus menu = menuRepository.findById(menuId).orElseThrow(() -> new MENU_NOT_FOUND());
        if (orderItemsRepository.existsByMenu(menu))
            throw new MENU_IN_USE();
        menuRepository.delete(menu);
    }
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
