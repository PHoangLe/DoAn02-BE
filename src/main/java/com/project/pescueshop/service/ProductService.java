package com.project.pescueshop.service;

import com.project.pescueshop.model.dto.ProductDTO;
import com.project.pescueshop.model.dto.VarietyDTO;
import com.project.pescueshop.model.entity.Product;
import com.project.pescueshop.model.entity.Variety;
import com.project.pescueshop.model.entity.VarietyAttribute;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.repository.ProductRepository;
import com.project.pescueshop.repository.VarietyAttributeRepository;
import com.project.pescueshop.repository.dao.VarietyAttributeDAO;
import com.project.pescueshop.util.constant.EnumPetType;
import com.project.pescueshop.util.constant.EnumResponseCode;
import com.project.pescueshop.util.constant.EnumStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
    private final VarietyAttributeDAO varietyAttributeRepository;

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
        productRepository.save(product);

        List<String> imagesUrl = uploadProductImages(product.getProductId(), imagesList);

        addDefaultVariety(product);

        product.setImages(imagesUrl);
        productRepository.saveAndFlush(product);

        return transformProductToDTO(product);
    }

    private void addDefaultVariety(Product product) {
        if (product == null)
            return;

        Variety variety = new Variety(product.getProductId(), product.getName(), product.getPrice(), product.getStatus());
        variety = varietyService.addOrUpdateVariety(variety);

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
        images.forEach(image -> imagesUrl.add(fileUploadService.uploadFile(image, "product/", productId + "_" + System.currentTimeMillis())));

        return imagesUrl;
    }

    public ProductDTO addVariety(VarietyDTO varietyDTO) {
        varietyDTO = varietyService.addOrUpdateVariety(varietyDTO);
        Product product = findById(varietyDTO.getProductId());

        return transformProductToDTO(product);
    }

    public List<VarietyAttribute> getAllExistedAttributeByProductId(String productId, String type){
        return varietyAttributeRepository.getAllExistedAttributeByProductId(productId, type);
    }

    public ProductDTO addVarietyAttribute(VarietyAttribute newAttribute, String productId) throws FriendlyException {
        Product product = findById(productId);
        if (product == null)
        {
            throw new FriendlyException(EnumResponseCode.PRODUCT_NOT_FOUND);
        }

        boolean isExisted = product.getVarieties().stream()
                .anyMatch(variety -> variety.getVarietyId() == newAttribute.getAttributeId());

        if (isExisted){
            throw new FriendlyException(EnumResponseCode.ATTRIBUTE_EXISTED);
        }

        List<VarietyAttribute> varietyAttributeList = getAllExistedAttributeByProductId(productId, newAttribute.getType());
        List<Variety> varietyList = product.getVarieties() == null ? new ArrayList<>() : product.getVarieties();
        if (CollectionUtils.isEmpty(varietyAttributeList))
        {
            Variety variety = new Variety();
            variety.addAttribute(newAttribute);
            variety.setProductId(productId);
            variety.setStatus(EnumStatus.ACTIVE.getValue());
            variety.setPrice(product.getPrice());

            variety = varietyService.addOrUpdateVariety(variety);
            varietyList.add(variety);
        }
        else {
            for (VarietyAttribute varietyAttribute: varietyAttributeList){
                Variety variety = new Variety();
                variety.addAttribute(newAttribute);
                variety.addAttribute(varietyAttribute);
                variety.setProductId(productId);
                variety.setStatus(EnumStatus.ACTIVE.getValue());
                variety.setPrice(product.getPrice());

                variety = varietyService.addOrUpdateVariety(variety);
                varietyList.add(variety);
            }
        }

        product.setVarieties(varietyList);

        return transformProductToDTO(product);
    }
}
