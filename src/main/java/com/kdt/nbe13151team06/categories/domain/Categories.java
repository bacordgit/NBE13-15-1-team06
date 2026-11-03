package com.kdt.nbe13151team06.categories.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Categories {

    // categories_id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id AUTO_INCREMENT
    private Long id;

    // categories_name - 싱글오리진, 블렌드
    @Enumerated(EnumType.STRING)
    private CategoriesName name;

}
