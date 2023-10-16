package com.project.pescueshop.service;

import com.project.pescueshop.model.entity.Brand;
import com.project.pescueshop.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrandService {
    private final BrandRepository brandRepository;

    public Brand findById(String id){
        return brandRepository.findById(id).orElse(null);
    }
    @Transactional(rollbackOn = Exception.class)
    public Brand addBrand(Brand brand){
        return brandRepository.save(brand);
    }

    public List<Brand> findAllBrand() {
        return brandRepository.findAll();
    }
}
