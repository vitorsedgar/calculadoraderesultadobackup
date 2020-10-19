package com.br.ages.calculadoraback.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  UserEntity {
	@Id
	@SequenceGenerator(allocationSize = 1, name = "USUARIO_SEQ", sequenceName = "USUARIO_SEQ")
	@GeneratedValue(generator = "USUARIO_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id_user;

	@Column
	private String role;

    @JoinColumn(name = "cod_coop")
    @ManyToOne(optional = false)
    private CooperativeEntity codCoop;

	@Column
	private String name;

	@Column
	private String document;

	@Column
	private String password;
}
