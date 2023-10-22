package com.project.pescueshop.service;

import com.project.pescueshop.model.dto.ProductDTO;
import com.project.pescueshop.model.entity.Product;
import com.project.pescueshop.model.entity.Variety;
import com.project.pescueshop.repository.ProductRepository;
import com.project.pescueshop.util.constant.EnumPetType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final VarietyService varietyService;
    private final FileUploadService fileUploadService;

    public ProductDTO transformProductToDTO(Product product){
        return new ProductDTO(product);
    }
    public Product findById(String id){
        return productRepository.findByProductId(id).orElse(null);
    }

    @Transactional(rollbackOn = Exception.class)
    public ProductDTO addProduct(ProductDTO productDTO, MultipartFile[] images){
        List<MultipartFile> imagesList = Arrays.stream(images).toList();
        EnumPetType petType = EnumPetType.getById(productDTO.getPetTypeId());
        productDTO.setPetType(petType.getValue());

        Product product = new Product(productDTO);
        productRepository.saveAndFlush(product);

        List<String> imagesUrl = uploadProductImages(product.getProductId(), imagesList);
        addDefaultVariety(product, productDTO, imagesUrl);

        return transformProductToDTO(product);
    }

    private void addDefaultVariety(Product product, ProductDTO dto, List<String> imagesUrl) {
        if (product == null)
            return;

        Variety variety = new Variety(product.getProductId(), product.getName(), dto.getCoverImages(), product.getPrice(), product.getStatus());
        variety.setImages(imagesUrl);
        variety = varietyService.addVariety(variety);

        product.addVariety(variety);
    }

    public List<ProductDTO> findAllProduct(){
        List<Product> productList = productRepository.findAll();

        return productList.stream()
                .map(this::transformProductToDTO)
                .toList();
    }

    public List<String> uploadProductImages(String productId, final List<MultipartFile> images){
        List<String> imagesUrl = new ArrayList<>();
        images.forEach(image -> imagesUrl.add(fileUploadService.uploadFile(image, "product/", productId +  System.currentTimeMillis())));

        return imagesUrl;
    }
}
