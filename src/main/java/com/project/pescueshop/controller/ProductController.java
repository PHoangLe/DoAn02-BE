package com.project.pescueshop.controller;

import com.project.pescueshop.model.dto.*;
import com.project.pescueshop.model.dto.general.ResponseDTO;
import com.project.pescueshop.model.entity.Brand;
import com.project.pescueshop.model.entity.Category;
import com.project.pescueshop.model.entity.SubCategory;
import com.project.pescueshop.model.exception.FriendlyException;
import com.project.pescueshop.service.BrandService;
import com.project.pescueshop.service.CategoryService;
import com.project.pescueshop.service.ProductService;
import com.project.pescueshop.util.constant.EnumResponseCode;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@CrossOrigin
@Api
public class ProductController {
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductService productService;

    //<editor-fold desc="Category">
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/category")
    public ResponseEntity<ResponseDTO<Category>> addCategory(@RequestBody Category category) throws FriendlyException {
        category = categoryService.addCategory(category);

        ResponseDTO<Category> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, category);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/category")
    public ResponseEntity<ResponseDTO<List<Category>>> findAllCategory() throws FriendlyException {
        List<Category> categoryList = categoryService.findAllCategory();

        ResponseDTO<List<Category>> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, categoryList, "categoryList");

        return ResponseEntity.ok(result);
    }
    //</editor-fold>

    //<editor-fold desc="SubCategory">
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/sub-category")
    public ResponseEntity<ResponseDTO<SubCategory>> addSubCategory(@RequestBody SubCategory subCategory) throws FriendlyException {
        subCategory = categoryService.addSubCategory(subCategory);

        ResponseDTO<SubCategory> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, subCategory);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/sub-category")
    public ResponseEntity<ResponseDTO<List<SubCategory>>> findAllSubCategory() throws FriendlyException {
        List<SubCategory> categoryList = categoryService.findAllSubCategory();

        ResponseDTO<List<SubCategory>> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, categoryList, "subCategoryList");

        return ResponseEntity.ok(result);
    }
    //</editor-fold>

    //<editor-fold desc="Brand">
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/brand")
    public ResponseEntity<ResponseDTO<Brand>> addBrand(@RequestBody Brand brand) throws FriendlyException {
        brand = brandService.addBrand(brand);

        ResponseDTO<Brand> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, brand);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/brand")
    public ResponseEntity<ResponseDTO<List<Brand>>> findAllBrand() throws FriendlyException {
        List<Brand> categoryList = brandService.findAllBrand();

        ResponseDTO<List<Brand>> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, categoryList, "brandList");

        return ResponseEntity.ok(result);
    }
    //</editor-fold>

    //<editor-fold desc="Product">
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("")
    public ResponseEntity<ResponseDTO<ProductDTO>> addProduct(@RequestPart ProductDTO productDTO, @RequestPart("images") MultipartFile[] images) throws FriendlyException {
        productDTO = productService.addProduct(productDTO, images);

        ResponseDTO<ProductDTO> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, productDTO);

        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<ResponseDTO<List<ProductDTO>>> findAllProduct() throws FriendlyException {
        List<ProductDTO> productList = productService.findAllProduct();

        ResponseDTO<List<ProductDTO>> result = new ResponseDTO<>(EnumResponseCode.SUCCESS, productList, "productList");

        return ResponseEntity.ok(result);
    }
    //</editor-fold>
}
