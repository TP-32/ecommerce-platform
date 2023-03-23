package com.tp32.ecommerceplatform.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tp32.ecommerceplatform.dto.ProductDto;
import com.tp32.ecommerceplatform.model.Inventory;
import com.tp32.ecommerceplatform.model.Product;
import com.tp32.ecommerceplatform.repository.CategoryRepository;
import com.tp32.ecommerceplatform.repository.InventoryRepository;
import com.tp32.ecommerceplatform.repository.ProductRepository;
import com.tp32.ecommerceplatform.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private InventoryRepository inventoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, InventoryRepository inventoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public Product getProduct(Long id) {
        if (productRepository.existsById(id)) {
            return productRepository.findById(id).get();
        }
        return null;
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryRepository.findByName(productDto.getCategory()).get());

        Inventory inventory = new Inventory();
        inventory.setStock(productDto.getStock());
        
        product.setInventory(inventory);
        inventoryRepository.save(inventory);
        productRepository.save(product);

        return product;
    }

    @Override
    public Product updateProduct(Long id, ProductDto productDto) {
        Product updateProduct = productRepository.findById(id).get();
        updateProduct.setName(productDto.getName());
        updateProduct.setDescription(productDto.getDescription());
        updateProduct.setImage(productDto.getImage());
        updateProduct.setPrice(productDto.getPrice());
        updateProduct.setCategory(categoryRepository.findByName(productDto.getCategory()).get());

        Inventory inventory = updateProduct.getInventory();
        inventory.setStock(productDto.getStock());

        inventoryRepository.save(inventory);
        productRepository.save(updateProduct);
        return updateProduct;
    }

    @Override
    public Product deleteProduct(Long id) {
        Product product = productRepository.findById(id).get();
        productRepository.deleteById(id);
        return product;
    }

    @Override
    public List<Product> getProducts() {
        // Populate the products list with data from the database
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsWithSort(String field, String direction) {
        Sort sort = direction.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
        Sort.by(field).ascending() :
        Sort.by(field).descending();

        return productRepository.findAll(sort);
    }
}
