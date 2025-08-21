package br.com.aweb.sistema_produto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.aweb.sistema_produto.model.Product;
import br.com.aweb.sistema_produto.service.ProductService;

@Con troller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // LISTAR PRODUTOS
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

    @GetMapping()
    public String list(Model model) {
        // passa como vai buscar no html, que seria products nesse caso
        model.addAttribute("products", productService.listAll());
        return "list";
    }

    // Retorna a view do formulario de cadastro/edição de produto
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("product", new Product());

        return "product/form";
    }

}