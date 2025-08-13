package br.com.aweb.crud_no_db.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aweb.crud_no_db.dto.ProductDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/products")
public class ProductController {
    private Map<Long, ProductDTO> products = new HashMap<>();
    private Long nextId = 1L;


    // listar todos
    @GetMapping
    public List<ProductDTO> allProducts() {
        return new ArrayList<>(products.values());
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return products.get(id);
    }
    //criarprodutos
    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO product) {
        product.setId(nextId++);
        products.put(product.getId(), product);
        return product;
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id){
        if(products.remove(id) != null){
            return "Produto removido!";
        } else{
            return "Produto não encontrado!";
        }
    }

    @PutMapping("/{id}")
    public String updateProduct(@PathVariable Long id, @RequestBody ProductDTO product) {
        if(products.containsKey(id)) {
            products.get(id).setName(product.getName());
            products.get(id).setPrice(product.getPrice());
            return "200";
        }
        return "erro"; 
    }
    
    
    
}
