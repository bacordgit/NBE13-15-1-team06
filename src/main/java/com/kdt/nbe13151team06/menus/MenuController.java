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

    @GetMapping
    public List<MenuResponse> getMenus(){
        return this.menuService.findAll();
    }

    @GetMapping("/{menuId}")
    public MenuResponse getMenuById(@PathVariable("menuId") Long id){
      return menuService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<MenuResponse> create(@Valid @RequestBody MenuCURequest menuCreateRequest){
        MenuResponse res=menuService.create(menuCreateRequest,true);
        URI location=URI.create("/api/v1/menus/"+res.id());
        return ResponseEntity.created(location).body(res);
    }
    @PutMapping("/{menuId}")
    public MenuResponse update(@PathVariable("menuId") Long id,@Valid @RequestBody MenuCURequest menuUpdateRequest){
        return this.menuService.update(id,menuUpdateRequest);
    }
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> delete(@PathVariable("menuId") Long id){
        this.menuService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public List<MenuResponse> getAvailable(){
        return this.menuService.findByAvailable();
    }

    @GetMapping("/search")
    public List<MenuResponse> getName(@RequestParam String keyword){
        return this.menuService.findByName(keyword);
    }

    @PutMapping("/{menuId}/availability")
    public MenuResponse changeAvailable(@PathVariable("menuId") Long id, @Valid @RequestBody MenuavailableRequest menuavailableRequest){
       return this.menuService.changeAvailable(id,menuavailableRequest);
    }



}
