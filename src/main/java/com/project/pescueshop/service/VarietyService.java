package com.project.pescueshop.service;


import com.project.pescueshop.model.dto.VarietyDTO;
import com.project.pescueshop.model.entity.Variety;
import com.project.pescueshop.model.entity.VarietyAttribute;
import com.project.pescueshop.repository.VarietyAttributeRepository;
import com.project.pescueshop.repository.VarietyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VarietyService {
    private final VarietyRepository varietyRepository;
    private final VarietyAttributeRepository varietyAttributeRepository;
    private final FileUploadService fileUploadService;

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
}
