package com.br.ages.calculadoraback;

import com.br.ages.calculadoraback.dto.Cooperative;
import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.entity.*;
import com.br.ages.calculadoraback.repository.UserRepository;
import com.br.ages.calculadoraback.security.MD5Crypt;
import com.br.ages.calculadoraback.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
@Profile("!unit-test")
public class CommandLineAppStartupRunner implements CommandLineRunner {

	private final CooperativeService coopService;
	private final ProductService prodService;
	private final CooperativeProductService coopProdService;
	private final AnualResultService anualResultService;
	private final CategoryService categoryService;
	private UserRepository userRepository;

	public CommandLineAppStartupRunner(CooperativeService coopService, ProductService prodService, CooperativeProductService coopProdService, AnualResultService anualResultService, CategoryService categoryService, UserRepository userRepository) {
		this.coopService = coopService;
		this.prodService = prodService;
		this.coopProdService = coopProdService;
		this.anualResultService = anualResultService;
		this.categoryService = categoryService;
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Começou a popular");

		CategoryEntity cat1 = categoryService.saveCategory("Seguro Auto");
		CategoryEntity cat2 = categoryService.saveCategory("Previdência Privada");
		CategoryEntity cat3 = categoryService.saveCategory("Cartão");

		CooperativeEntity coop1 = coopService.saveCoop(Cooperative.builder().codCoop("0101").name("Sicredi Pioneira").build());
		CooperativeEntity coop2 = coopService.saveCoop(Cooperative.builder().codCoop("0116").name("Sicredi União Metropolitana").build());

		ProductEntity prod1 = prodService.save(ProductEntity.builder().name("Mastercard").category(cat3).build());
		ProductEntity prod2 = prodService.save(ProductEntity.builder().name("Visa").category(cat3).build());
		ProductEntity prod3 = prodService.save(ProductEntity.builder().name("Full").category(cat1).build());
		ProductEntity prod4 = prodService.save(ProductEntity.builder().name("Cobetura Total").category(cat2).build());

		coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop1).idProd(prod1).build()).value(BigDecimal.valueOf(200)).weight(1.0).build());
		coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop1).idProd(prod2).build()).value(BigDecimal.valueOf(100)).weight(2.0).build());
		coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop1).idProd(prod3).build()).value(BigDecimal.valueOf(100)).weight(3.0).build());

		coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop2).idProd(prod1).build()).value(BigDecimal.valueOf(400)).weight(2.0).build());

		anualResultService.save(AnualResultEntity.builder().anualResultPK(AnualResultPK.builder().idCoop(coop1).year(2020).build()).result(new BigDecimal(800)).build());

		UserEntity userEntity = UserEntity.builder().document("0101-01").password(new MD5Crypt().encode("senha")).role("coop").codCoop(coop1).name("Manager 0101").build();
		userRepository.save(userEntity);

		log.info("Terminou de popular");
	}
}
