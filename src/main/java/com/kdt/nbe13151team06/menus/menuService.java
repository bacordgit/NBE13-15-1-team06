package com.kdt.nbe13151team06.menus;

import com.kdt.nbe13151team06.categories.CategoriesRepository;
import com.kdt.nbe13151team06.categories.domain.Categories;
import com.kdt.nbe13151team06.error.CategoryNotFoundException;
import com.kdt.nbe13151team06.error.MENU_IN_USE;
import com.kdt.nbe13151team06.error.MenuNotFoundException;
import com.kdt.nbe13151team06.orderItems.orderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class menuService {
    private final menuRepository menuRepository;
    private final CategoriesRepository categoriesRepository;
    private final orderItemsRepository orderItemsRepository;

    @Autowired
    public menuService(menuRepository menuRepository, CategoriesRepository categoriesRepository, orderItemsRepository orderItemsRepository) {
        this.menuRepository = menuRepository;
        this.categoriesRepository = categoriesRepository;
        this.orderItemsRepository = orderItemsRepository;
    }

    @Transactional
    public menuResponse create(menuCURequest menuCreateRequest, boolean available) {
        Categories category = categoriesRepository.findById(menuCreateRequest.categoryId()).orElseThrow(() -> new CategoryNotFoundException());
        menus menu = new menus(category, menuCreateRequest.name(), menuCreateRequest.price(), menuCreateRequest.description(), available);
        menus saved = menuRepository.save(menu);
        return toResponse(saved);

    }

    @Transactional
    public menuResponse update(Long menuId, menuCURequest menuUpdateRequest) {
        menus menu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());

        menu.setName(menuUpdateRequest.name());
        menu.setPrice(menuUpdateRequest.price());
        menu.setDescription(menuUpdateRequest.description());
        menu.setCategory(categoriesRepository.findById(menuUpdateRequest.categoryId()).orElseThrow(() -> new CategoryNotFoundException()));
        menuRepository.save(menu);
        return toResponse(menu);
    }

    @Transactional(readOnly = true)
    public menuResponse findOne(Long menuId) {
        menus menu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
        return toResponse(menu);
    }
    @Transactional(readOnly=true)
    public List<menuResponse> findByName(String name){
        List<menuResponse>list=new ArrayList<>();
         for(menus menu: menuRepository.findAllByOrderByIdAsc()){
             if(menu.getName().equals(name)){
                 list.add(toResponse(menu));
             }
         }
         if(list.isEmpty()){
             throw new MenuNotFoundException();
         }
         return list;
    }

    @Transactional(readOnly=true)
    public List<menuResponse> findAll() {
        List<menuResponse> list = new ArrayList<>();
        for (menus menu : menuRepository.findAllByOrderByIdAsc()) {
            list.add(toResponse(menu));
        }
        if(list.isEmpty()){
            throw new MenuNotFoundException();
        }
        return list;
    }
    @Transactional(readOnly=true)
    public List<menuResponse> findByAvailable() {
        List<menuResponse>list=new ArrayList<>();
        for (menus menu : menuRepository.findAllByOrderByIdAsc()) {
            if(menu.isAvailable()){
                list.add(toResponse(menu));
            }
        }
        if(list.isEmpty()){
            throw new MenuNotFoundException();
        }
        return list;
    }

    @Transactional
    public void delete(Long menuId) {
        menus menu = menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
        if (orderItemsRepository.existsByMenu(menu))
            throw new MENU_IN_USE();
        menuRepository.delete(menu);
    }
    @Transactional
    public menuResponse changeAvailable(Long menuId,menuavailableRequest request){
        menus menu=menuRepository.findById(menuId).orElseThrow(() -> new MenuNotFoundException());
        menu.setAvailable(request.availability());
        menuRepository.save(menu);
        return toResponse(menu);
    }

    private menuResponse toResponse(menus menu){
    return new menuResponse(menu.getId(),menu.getCategory().getId(),menu.getName(),menu.getPrice(),menu.getDescription(),menu.isAvailable());
    }

}
