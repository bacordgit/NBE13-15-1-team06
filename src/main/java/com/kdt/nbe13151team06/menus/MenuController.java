package com.kdt.nbe13151team06.menus;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {
    private final MenuService menuService;
    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    //A06 전체 메뉴 조회
    @GetMapping
    public List<MenuResponse> getMenus(){
        return this.menuService.findAll();
    }

    //A07 id로 메뉴 조회
    @GetMapping("/{menuId}")
    public MenuResponse getMenuById(@PathVariable("menuId") Long id){
      return menuService.findOne(id);
    }

    //A08 메뉴 생성
    @PostMapping
    public ResponseEntity<MenuResponse> create(@Valid @RequestBody MenuCURequest menuCreateRequest){
        MenuResponse res=menuService.create(menuCreateRequest,true);
        URI location=URI.create("/api/v1/menus/"+res.id());
        return ResponseEntity.created(location).body(res);
    }
    //A09 메뉴 수정
    @PutMapping("/{menuId}")
    public MenuResponse update(@PathVariable("menuId") Long id,@Valid @RequestBody MenuCURequest menuUpdateRequest){
        return this.menuService.update(id,menuUpdateRequest);
    }
    //A10 메뉴 삭제
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> delete(@PathVariable("menuId") Long id){
        this.menuService.delete(id);
        return ResponseEntity.noContent().build();
    }
    //A19 available이 true인 메뉴 조회
    @GetMapping("/available")
    public List<MenuResponse> getAvailable(){
        return this.menuService.findByAvailable();
    }

    //A20 이름으로 메뉴 찾기
    @GetMapping("/search")
    public List<MenuResponse> getName(@RequestParam String keyword){
        return this.menuService.findByName(keyword);
    }

    //A21 판매 여부 변경
    @PutMapping("/{menuId}/availability")
    public MenuResponse changeAvailable(@PathVariable("menuId") Long id, @Valid @RequestBody MenuavailableRequest menuavailableRequest){
       return this.menuService.changeAvailable(id,menuavailableRequest);
    }



}
