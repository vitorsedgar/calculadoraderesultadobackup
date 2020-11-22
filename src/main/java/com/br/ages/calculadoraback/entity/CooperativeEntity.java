package com.br.ages.calculadoraback.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CooperativeEntity {
    @Id
    @SequenceGenerator(allocationSize = 1, name = "id_coop_seq", sequenceName = "id_coop_seq")
    @GeneratedValue(generator = "id_coop_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "id_coop")
    private Long idCoop;

    @Column(name = "cod_coop")
    private String codCoop;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "codCoop")
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<UserEntity> users;

}
