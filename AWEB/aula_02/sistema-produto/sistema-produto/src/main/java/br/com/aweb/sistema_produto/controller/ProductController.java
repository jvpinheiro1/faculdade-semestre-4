package br.com.aweb.sistema_produto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.aweb.sistema_produto.model.Product;
import br.com.aweb.sistema_produto.service.ProductService;




@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    //LISTAR PRODUTOS
    public String list(Model model) {
        model.addAttribute("products", productService.listAll());
        return "product/list";
    }
    
    //Retorna a view do formulario de cadastro/edição de produto
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());

        return "product/form";
    }
    
