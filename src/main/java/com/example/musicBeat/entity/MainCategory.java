package com.example.musicBeat.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MainCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long mainCategoryId;
    private String mainCategoryName;
    @OneToMany(mappedBy = "mainCategory",fetch = FetchType.LAZY)
    private List<Category> categoryList;
}
