package com.br.ages.calculadoraback.service;

import com.br.ages.calculadoraback.dto.Product;
import com.br.ages.calculadoraback.entity.ProductEntity;
import com.br.ages.calculadoraback.repository.CooperativeProductRepository;
import com.br.ages.calculadoraback.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
		private final ProductRepository productRepository;
		private final CooperativeProductRepository coopProductRepository;

		public ProductService(ProductRepository productRepository, CooperativeProductRepository coopProductRepository) {
				this.productRepository = productRepository;
				this.coopProductRepository = coopProductRepository;
		}

		public ProductEntity saveProduct(Product product) {
				ProductEntity entity = ProductEntity.builder()
						.name(product.getName())
						.category(null)
						.build();
				return productRepository.save(entity);
		}

		public List<Product> findProductsByCodCoop(String codCoop) {
				return coopProductRepository.findByCoopProdPK_IdCoop_CodCoop(codCoop)
						.stream()
						.map(it -> Product.builder()
								.name(it.getCoopProdPK().getIdProd().getName())
								.idProd(it.getCoopProdPK().getIdProd().getIdProd())
								//TODO descomentar quando houver categorias
//								.categoryName(it.getCoopProdPK().getIdProd().getCategory().getName())
								.weight(it.getWeight())
								.categoryName("Cartão")
								.build())
						.collect(Collectors.toList());
		}
}
