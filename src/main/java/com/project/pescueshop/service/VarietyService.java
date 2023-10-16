package com.project.pescueshop.service;


import com.project.pescueshop.model.dto.VarietyDTO;
import com.project.pescueshop.model.entity.Variety;
import com.project.pescueshop.repository.VarietyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VarietyService {
    private final VarietyRepository varietyRepository;

    public VarietyDTO transformVarietyToDTO(Variety variety){
        return new VarietyDTO(variety);
    }

    public Variety findById(String id){
        return varietyRepository.findById(id).orElse(null);
    }

    public VarietyDTO addVariety(VarietyDTO dto){
        Variety result = varietyRepository.save(new Variety(dto));
        return transformVarietyToDTO(result);
    }
}
