package com.kdt.nbe13151team06.menus;


//메뉴 응답 dto
public record MenuResponse(Long id, Long categoryId, String name, int price, String description, boolean available) {

    public static MenuResponse from(Menus menus){
        return new MenuResponse(menus.getId(), menus.getCategory().getId(), menus.getName(), menus.getPrice(), menus.getDescription(), menus.available);
    }

}
