package com.project.pescueshop.service;


import com.project.pescueshop.model.dto.ProductDTO;
import com.project.pescueshop.model.dto.VarietyDTO;
import com.project.pescueshop.model.entity.Product;
import com.project.pescueshop.model.entity.Variety;
import com.project.pescueshop.model.entity.VarietyAttribute;
import com.project.pescueshop.repository.VarietyAttributeRepository;
import com.project.pescueshop.repository.VarietyRepository;
import com.project.pescueshop.util.constant.EnumStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VarietyService extends BaseService{
    private final VarietyRepository varietyRepository;
    private final VarietyAttributeRepository varietyAttributeRepository;
    private final FileUploadService fileUploadService;
    @Lazy
    private final ThreadService threadService;

    public VarietyDTO transformVarietyToDTO(Variety variety){
        return new VarietyDTO(variety);
    }

    public Variety findById(String id){
        return varietyRepository.findById(id).orElse(null);
    }

    @Transactional(rollbackOn = Exception.class)
    public VarietyDTO addOrUpdateVariety(VarietyDTO dto){
        Variety result = addOrUpdateVariety(new Variety(dto));

        result = addOrUpdateVariety(result);

        return transformVarietyToDTO(result);
    }

    @Transactional(rollbackOn = Exception.class)
    public Variety addOrUpdateVariety(Variety variety){
        varietyRepository.saveAndFlush(variety);
        return variety;
    }

    public Map<String, List<VarietyAttribute>> findAllVarietyAttribute(){
        List<VarietyAttribute> varietyAttributeList = varietyAttributeRepository.findAll();

        Map<String, List<VarietyAttribute>> varietyAttributeMap = varietyAttributeList.stream()
                .collect(Collectors.groupingBy(VarietyAttribute::getType));

        return varietyAttributeMap;
    }

    public void addVarietyByListAttribute(Product product, List<VarietyAttribute> varietyAttributeList){
        List<Variety> varietyList = new ArrayList<>();
        for (VarietyAttribute attribute: varietyAttributeList){
            Variety variety = new Variety();
            variety.addAttribute(attribute);
            variety.setProductId(product.getProductId());
            variety.setStatus(EnumStatus.ACTIVE.getValue());
            variety.setPrice(product.getPrice());
            varietyList.add(addOrUpdateVariety(variety));
        }
    }

    public void addVarietyByListAttribute(Product product, List<VarietyAttribute> sizeAttributesList, List<VarietyAttribute> colorAttributeList) throws InterruptedException {
        if (!CollectionUtils.isEmpty(sizeAttributesList)) {
            if (!CollectionUtils.isEmpty(colorAttributeList)) {
                threadService.addVarietyByAttribute(product, sizeAttributesList, colorAttributeList);
            } else {
                addVarietyByListAttribute(product, sizeAttributesList);
            }
        }
        else {
            addVarietyByListAttribute(product, colorAttributeList);
        }
    }
}
