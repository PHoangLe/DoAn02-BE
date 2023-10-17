package com.project.pescueshop.service;

import com.project.pescueshop.model.dto.ProductDTO;
import com.project.pescueshop.model.entity.Product;
import com.project.pescueshop.repository.ProductRepository;
import com.project.pescueshop.util.constant.EnumPetType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductDTO transformProductToDTO(Product product){
        return new ProductDTO(product);
    }
    public Product findById(String id){
        return productRepository.findByProductId(id).orElse(null);
    }

    @Transactional(rollbackOn = Exception.class)
    public ProductDTO addProduct(ProductDTO productDTO){
        EnumPetType petType = EnumPetType.getById(productDTO.getPetTypeId());

        productDTO.setPetType(petType.getValue());

        Product product = productRepository.save(new Product(productDTO));
        return transformProductToDTO(product);
    }

    public List<ProductDTO> findAllProduct(){
        List<Product> productList = productRepository.findAll();

        return productList.stream()
                .map(this::transformProductToDTO)
                .toList();
    }
}
