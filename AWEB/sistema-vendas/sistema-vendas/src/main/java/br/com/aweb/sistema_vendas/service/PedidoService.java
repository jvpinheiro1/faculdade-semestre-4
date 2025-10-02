package br.com.aweb.sistema_vendas.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.aweb.sistema_vendas.model.Cliente;
import br.com.aweb.sistema_vendas.model.Pedido;
import br.com.aweb.sistema_vendas.model.StatusPedido;
import br.com.aweb.sistema_vendas.repository.PedidoRepository;
import br.com.aweb.sistema_vendas.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;
    ProdutoRepository produtoRepository;

    @Transactional
    // método para criar um pedido para um cliente
    public Pedido novoPedido (Cliente cliente) {
        // ao criar um pedido, o cliente deve ser obrigatório (metodo construtor personalizado em Pedido.java)
        return pedidoRepository.save(new Pedido(cliente));
    }

    @Transactional
    public void adicionarItem(Long pedidoId, Long produtoId, Integer quantidade) {
        var optionalPedido = pedidoRepository.findById(pedidoId);
        var optionalProduto = produtoRepository.findById(produtoId);

        if (!optionalPedido.isPresent() || !optionalProduto.isPresent()) {
            throw new IllegalArgumentException("Produto/pedido não encontrado");
        }
        // Obtém o pedido e o produto
        var pedido = optionalPedido.get();
        // Obtém o produto
        var produto = optionalProduto.get();

        if (pedido.getStatus() != StatusPedido.ATIVO) {
            throw new IllegalArgumentException("Pedido não está ativo");
        }

        if (produto.getQuantidadeEmEstoque() < quantidade) {
            throw new IllegalArgumentException("Quantidade em estoque insuficiente");
        }

        ItemPedido item = new ItemPedido(produto, quantidade)
        item.setPedido(pedido);
        
    }
}
