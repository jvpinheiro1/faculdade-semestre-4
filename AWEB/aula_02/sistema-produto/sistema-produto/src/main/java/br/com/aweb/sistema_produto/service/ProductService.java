package br.com.aweb.sistema_produto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aweb.sistema_produto.model.Product;
import br.com.aweb.sistema_produto.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    //Buscar todos os produtos
    public List<Product> listAll(){
        return productRepository.findAll();
    }

    //Buscar produto por id
    public Product findProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()) 
            return optionalProduct.get();
        throw new RuntimeException( "Produto não encontrado");  
    }
    // criar produto
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    //excluir produto
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id))
            throw new RuntimeException("Produto não encontrado!");
        productRepository.deleteById(id);
    }

    public List<Product> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
