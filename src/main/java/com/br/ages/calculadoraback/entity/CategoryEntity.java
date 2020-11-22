package com.br.ages.calculadoraback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryEntity {
    @Id
    @SequenceGenerator(allocationSize = 1, name = "id_category_seq", sequenceName = "id_category_seq")
    @GeneratedValue(generator = "id_category_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_category")
    private Long idCategory;

    @Column
    private String name;

}
