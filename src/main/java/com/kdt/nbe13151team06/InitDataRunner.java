package com.kdt.nbe13151team06;

import com.kdt.nbe13151team06.categories.CategoriesRepository;
import com.kdt.nbe13151team06.categories.domain.Categories;
import com.kdt.nbe13151team06.menus.MenuRepository;
import com.kdt.nbe13151team06.menus.Menus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDataRunner implements ApplicationRunner {
    private final CategoriesRepository categoriesRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception{
        //카테고리 초기화(카테고리가 없으면 추가)
        List<String> CategoryInitial=List.of("싱글오리진","블렌드");
        for(String category:CategoryInitial){
            if(!categoriesRepository.existsByName(category)){
                categoriesRepository.save(new Categories(category));
            }
        }//메뉴 초기화(메뉴 레포지토리에 데이터가 없을 경우 추가)
        if(menuRepository.count()>0){
            return;
        }
        Categories category=categoriesRepository.findByName("싱글오리진");
        Menus menu1=new Menus(category,"Columbia Nariñó",5000,"콜롬비아 나리뇨 원두를 담은 커피빈 패키지입니다.",true);
        Menus menu2=new Menus(category,"Brazil Serra Do Caparaó",7000,"브라질 세하 두 카파라오 원두를 담은 커피빈 패키지입니다.",true);
        Menus menu3=new Menus(category,"Columbia Quindío (White Wine Extended Fermentation)",9000,"콜롬비아 킨디오 원두를 담은 커피빈 패키지입니다.",true);
        Menus menu4=new Menus(category,"Ethiopia Sidamo",11000,"에티오피아 시다모 원두를 담은 커피빈 패키지입니다.",true);
        menuRepository.save(menu1);
        menuRepository.save(menu2);
        menuRepository.save(menu3);
        menuRepository.save(menu4);

    }

}
