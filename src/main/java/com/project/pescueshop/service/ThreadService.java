package com.project.pescueshop.service;

import com.project.pescueshop.model.entity.Product;
import com.project.pescueshop.model.entity.Variety;
import com.project.pescueshop.model.entity.VarietyAttribute;
import com.project.pescueshop.util.constant.EnumStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ThreadService extends BaseService {
    private final VarietyService varietyService;
    @Autowired
    public ThreadService(@Lazy VarietyService varietyService) {
        this.varietyService = varietyService;
    }

//    public List<Variety> addVarietyByAttribute(Product product, List<VarietyAttribute> existingAttributes, VarietyAttribute newAttribute) throws InterruptedException {
//        if (existingAttributes == null || existingAttributes.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        int numThreads = existingAttributes.size();
//        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
//        List<Future<Variety>> futureList = new ArrayList<>();
//
//        for (VarietyAttribute varietyAttribute : existingAttributes) {
//            Future<Variety> future = executor.submit(() -> processAddVarietyByAttribute(product, varietyAttribute, newAttribute));
//            futureList.add(future);
//        }
//
//        executor.shutdown();
//
//        List<Variety> resultVarieties = new ArrayList<>();
//
//        for (Future<Variety> future : futureList) {
//            try {
//                Variety variety = future.get();
//                if (variety != null) {
//                    resultVarieties.add(variety);
//                }
//            } catch (InterruptedException | ExecutionException e) {
//                log.trace("Error: " + e.getMessage());
//            }
//        }
//
//        return resultVarieties;
//    }
//
//    public List<Variety> addVarietyByAttribute(Product product, List<VarietyAttribute> colorAttributeList, List<VarietyAttribute> sizeAttributeList) throws InterruptedException {
//        if (colorAttributeList == null || colorAttributeList.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        int numThreads = colorAttributeList.size();
//        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
//        List<Future<List<Variety>>> futureList = new ArrayList<>();
//
//        for (VarietyAttribute colorAttribute : colorAttributeList) {
//            Future<List<Variety>> future = executor.submit(() -> addVarietyByAttribute(product, sizeAttributeList, colorAttribute));
//            futureList.add(future);
//        }
//
//        executor.shutdown();
//
//        List<Variety> resultVarieties = new ArrayList<>();
//
//        for (Future<List<Variety>> future : futureList) {
//            try {
//                List<Variety> variety = future.get();
//                if (variety != null) {
//                    resultVarieties.addAll(variety);
//                }
//            } catch (InterruptedException | ExecutionException e) {
//                log.trace("Error: " + e.getMessage());
//            }
//        }
//
//        return resultVarieties;
//    }

    public void addVarietyByAttribute(Product product, List<VarietyAttribute> existingAttributes, VarietyAttribute newAttribute) {
        if (existingAttributes == null || existingAttributes.isEmpty()) {
            return;
        }

        for (VarietyAttribute varietyAttribute : existingAttributes) {
            Thread thread = new Thread(() -> processAddVarietyByAttribute(product, varietyAttribute, newAttribute));
            thread.start();
        }
    }

    public void addVarietyByAttribute(Product product, List<VarietyAttribute> colorAttributeList, List<VarietyAttribute> sizeAttributeList) {
        if (colorAttributeList == null || colorAttributeList.isEmpty()) {
            return;
        }

        for (VarietyAttribute colorAttribute : colorAttributeList) {
            Thread thread = new Thread(() -> addVarietyByAttribute(product, sizeAttributeList, colorAttribute));
            thread.start();
        }
    }

    private void processAddVarietyByAttribute(Product product, VarietyAttribute existingAttribute, VarietyAttribute newAttribute) {
        Variety variety = new Variety();
        variety.addAttribute(newAttribute);
        variety.setName(product.getName());
        variety.addAttribute(existingAttribute);
        variety.setProductId(product.getProductId());
        variety.setStatus(EnumStatus.ACTIVE.getValue());
        variety.setPrice(product.getPrice());
        varietyService.addOrUpdateVariety(variety);
    }
}
