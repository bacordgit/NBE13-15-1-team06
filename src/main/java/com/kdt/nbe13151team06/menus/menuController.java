package com.kdt.nbe13151team06.menus;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
public class menuController {
    private final menuService menuService;
    @Autowired
    public menuController(menuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<menuResponse> getMenus(){
        return this.menuService.findAll();
    }

    @GetMapping("/{menuId}")
    public menuResponse getMenuById(@PathVariable("menuId") Long id){
      return menuService.findOne(id);
    }

    @PostMapping
    public ResponseEntity<menuResponse> create(@Valid @RequestBody menuCURequest menuCreateRequest){
        menuResponse res=menuService.create(menuCreateRequest,true);
        URI location=URI.create("/api/v1/menus/"+res.id());
        return ResponseEntity.created(location).body(res);
    }
    @PutMapping("/{menuId}")
    public menuResponse update(@PathVariable("menuId") Long id,@Valid @RequestBody menuCURequest menuUpdateRequest){
        return this.menuService.update(id,menuUpdateRequest);
    }
    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> delete(@PathVariable("menuId") Long id){
        this.menuService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public List<menuResponse> getAvailable(){
        return this.menuService.findByAvailable();
    }

    @GetMapping("/search")
    public List<menuResponse> getName(@RequestParam String keyword){
        return this.menuService.findByName(keyword);
    }

    @PutMapping("/{menuId}/availability")
    public menuResponse changeAvailable(@PathVariable("menuId") Long id, @Valid @RequestBody menuavailableRequest menuavailableRequest){
       return this.menuService.changeAvailable(id,menuavailableRequest);
    }



}
