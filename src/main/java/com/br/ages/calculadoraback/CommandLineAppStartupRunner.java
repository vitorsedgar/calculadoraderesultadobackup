package com.br.ages.calculadoraback;

import com.br.ages.calculadoraback.dto.Cooperative;
import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.entity.*;
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
		private UserService userService;

		public CommandLineAppStartupRunner(CooperativeService coopService, ProductService prodService, CooperativeProductService coopProdService, AnualResultService anualResultService, UserService userService) {
				this.coopService = coopService;
				this.prodService = prodService;
				this.coopProdService = coopProdService;
				this.anualResultService = anualResultService;
				this.userService = userService;
		}

		@Override
		public void run(String... args) throws Exception {
				log.info("Começou a popular");

				boolean dbAlreadyStarted = coopService.findAll().size() > 0;

				if (!dbAlreadyStarted) {
						CooperativeEntity coop1 = coopService.saveCoop(Cooperative.builder().codCoop("0101").name("Sicredi Pioneira").build());
						CooperativeEntity coop2 = coopService.saveCoop(Cooperative.builder().codCoop("0116").name("Sicredi União Metropolitana").build());

						ProductEntity prod1 = prodService.saveProduct(Product.builder().categoryName("Cartão").name("Cartão Débito").build());
						ProductEntity prod2 = prodService.saveProduct(Product.builder().categoryName("Cartão").name("Cartão Crédito").build());

						coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop1).idProd(prod1).build()).value(BigDecimal.valueOf(200)).weight(1.0).build());
						coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop1).idProd(prod2).build()).value(BigDecimal.valueOf(100)).weight(2.0).build());

						coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop2).idProd(prod1).build()).value(BigDecimal.valueOf(400)).weight(2.0).build());

						anualResultService.save(AnualResultEntity.builder().anualResultPK(AnualResultPK.builder().idCoop(coop1).year(2020L).build()).result(new BigDecimal(800)).build());


						UserEntity userEntity = UserEntity.builder().document("12").password(new MD5Crypt().encode("12")).role("coop").codCoop(coop2).name("Victor").build();
						userService.save(userEntity);

						log.info("Terminou de popular");
				} else {
						log.info("Banco ja startado");
				}
	}
}
