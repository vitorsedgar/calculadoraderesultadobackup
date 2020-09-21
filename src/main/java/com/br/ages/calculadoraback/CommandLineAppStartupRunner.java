package com.br.ages.calculadoraback;

import com.br.ages.calculadoraback.dto.Cooperative;
import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.entity.*;
import com.br.ages.calculadoraback.service.AnualResultService;
import com.br.ages.calculadoraback.service.CooperativeProductService;
import com.br.ages.calculadoraback.service.CooperativeService;
import com.br.ages.calculadoraback.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {

		private final CooperativeService coopService;
		private final ProductService prodService;
		private final CooperativeProductService coopProdService;
		private final AnualResultService anualResultService;

		public CommandLineAppStartupRunner(CooperativeService coopService, ProductService prodService, CooperativeProductService coopProdService, AnualResultService anualResultService) {
				this.coopService = coopService;
				this.prodService = prodService;
				this.coopProdService = coopProdService;
				this.anualResultService = anualResultService;
		}

		@Override
		public void run(String... args) throws Exception {
				log.info("Começou a popular");
				CooperativeEntity coop1 = coopService.saveCoop(Cooperative.builder().codCoop("0101").name("Sicredi Pioneira").build());
				CooperativeEntity coop2 = coopService.saveCoop(Cooperative.builder().codCoop("0116").name("Sicredi União Metropolitana").build());

				ProductEntity prod1 = prodService.saveProduct(Product.builder().categoryName("Cartão").name("Cartão Débito").build());
				ProductEntity prod2 = prodService.saveProduct(Product.builder().categoryName("Cartão").name("Cartão Crédito").build());

				coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop1).idProd(prod1).build()).value(BigDecimal.valueOf(2000)).weight(1.0).build());
				coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop1).idProd(prod2).build()).value(BigDecimal.valueOf(1000)).weight(2.0).build());

				coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop2).idProd(prod1).build()).value(BigDecimal.valueOf(4000)).weight(2.0).build());

				anualResultService.save(AnualResultEntity.builder().anualResultPK(AnualResultPK.builder().idCoop(coop1).year(2020L).build()).result(new BigDecimal(10000)).build());

				ProductEntity prod3 = prodService.saveProduct(Product.builder().categoryName("Cartão").name("Cartão Beneficio").build());
				coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop1).idProd(prod3).build()).value(BigDecimal.valueOf(3000)).weight(3.0).build());
				ProductEntity prod4 = prodService.saveProduct(Product.builder().categoryName("Cartão").name("Cartão Conjunto").build());
				coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop1).idProd(prod4).build()).value(BigDecimal.valueOf(2000)).weight(2.0).build());
				ProductEntity prod5 = prodService.saveProduct(Product.builder().categoryName("Cartão").name("Cartão Estudantil").build());
				coopProdService.save(CooperativeProductEntity.builder().coopProdPK(CoopProdPK.builder().idCoop(coop1).idProd(prod5).build()).value(BigDecimal.valueOf(2000)).weight(1.0).build());
				log.info("Terminou de popular");
		}
}
