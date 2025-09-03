package com.design_patterns.design_patterns.service.impl;

import com.design_patterns.design_patterns.model.Cliente;
import com.design_patterns.design_patterns.model.ClienteRepository;
import com.design_patterns.design_patterns.model.Endereco;
import com.design_patterns.design_patterns.model.EnderecoRepository;
import com.design_patterns.design_patterns.service.ClienteService;
import com.design_patterns.design_patterns.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        salvarClienteComCep(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if(clienteBd.isPresent()){
            salvarClienteComCep(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }


    private void salvarClienteComCep(Cliente cliente) {
        Endereco endereco = enderecoRepository.findById(cliente.getEndereco().getCep())
                .orElseGet(() -> {
                    Endereco noveEndereco = viaCepService.consultarCep(
                            cliente.getEndereco().getCep());
                    enderecoRepository.save(noveEndereco);
                    return noveEndereco;
                }); //optional - Type - Optional
        cliente.setEndereco(endereco);
        clienteRepository.save(cliente);
    }
}
