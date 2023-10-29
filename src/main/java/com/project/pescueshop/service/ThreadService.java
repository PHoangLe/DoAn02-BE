package com.project.pescueshop.service;

import com.project.pescueshop.model.entity.Product;
import com.project.pescueshop.model.entity.Variety;
import com.project.pescueshop.model.entity.VarietyAttribute;
import com.project.pescueshop.util.constant.EnumStatus;
import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

@Service
@Slf4j
public class ThreadService extends BaseService {
    private final VarietyService varietyService;
    @Autowired
    public ThreadService(@Lazy VarietyService varietyService) {
        this.varietyService = varietyService;
    }

    public List<Variety> addVarietyByAttribute(Product product, List<VarietyAttribute> existingAttributes, VarietyAttribute newAttribute) {
        if (existingAttributes == null || existingAttributes.isEmpty()) {
            return Collections.emptyList();
        }

        int numThreads = existingAttributes.size();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Variety>> futureList = new ArrayList<>();

        for (VarietyAttribute varietyAttribute : existingAttributes) {
            Future<Variety> future = executor.submit(() -> processAddVarietyByAttribute(product, varietyAttribute, newAttribute));
            futureList.add(future);
        }

        executor.shutdown();

        List<Variety> resultVarieties = new ArrayList<>();

        for (Future<Variety> future : futureList) {
            try {
                Variety variety = future.get();
                if (variety != null) {
                    resultVarieties.add(variety);
                }
            } catch (InterruptedException | ExecutionException e) {
                log.trace("Error: " + e.getMessage());
            }
        }

        return resultVarieties;
    }

    private Variety processAddVarietyByAttribute(Product product, VarietyAttribute existingAttribute, VarietyAttribute newAttribute) {
        Variety variety = new Variety();
        variety.addAttribute(newAttribute);
        variety.addAttribute(existingAttribute);
        variety.setProductId(product.getProductId());
        variety.setStatus(EnumStatus.ACTIVE.getValue());
        variety.setPrice(product.getPrice());

        return varietyService.addOrUpdateVariety(variety);
    }
}
