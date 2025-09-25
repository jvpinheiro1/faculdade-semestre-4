package br.com.aweb.sistema_vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aweb.sistema_vendas.model.Cliente;
import br.com.aweb.sistema_vendas.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        if (clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        if (clienteRepository.existsByEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return clienteSalvo;
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Transactional
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        var optionalCliente = buscarPorId(id);
        if (!optionalCliente.isPresent())
            throw new IllegalArgumentException("Cliente não encontrado.");

        var clienteExistente = optionalCliente.get();

        if (!clienteExistente.getCpf().equals(clienteAtualizado.getCpf()) &&
                clienteRepository.existsByCpf(clienteAtualizado.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        if (!clienteExistente.getEmail().equals(clienteAtualizado.getEmail()) &&
                clienteRepository.existsByEmail(clienteAtualizado.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        clienteExistente.setNome(clienteAtualizado.getNome());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setLogradouro(clienteAtualizado.getLogradouro());
        clienteExistente.setNumero(clienteAtualizado.getNumero());
        clienteExistente.setComplemento(clienteAtualizado.getComplemento());
        clienteExistente.setCidade(clienteAtualizado.getCidade());
        clienteExistente.setBairro(clienteAtualizado.getBairro());
        clienteExistente.setUf(clienteAtualizado.getUf());
        clienteExistente.setCep(clienteAtualizado.getCep());
        var clienteSalvo = clienteRepository.save(clienteExistente);
        return clienteSalvo;
    }
    @Transactional
    public void excluir(Long id) {
        var optionalCliente = buscarPorId(id);
        if (!optionalCliente.isPresent())
            throw new IllegalArgumentException("Cliente não encontrado.");

        clienteRepository.deleteById(id);
    }

}
