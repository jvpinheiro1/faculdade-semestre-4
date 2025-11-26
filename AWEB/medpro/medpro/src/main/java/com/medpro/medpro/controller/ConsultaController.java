package com.medpro.medpro.controller;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.medpro.medpro.enums.MotivoCancelamento;
import com.medpro.medpro.model.dto.*;
import com.medpro.medpro.model.entity.Consulta;
import com.medpro.medpro.model.entity.Medico;
import com.medpro.medpro.repository.ConsultaRepository;
import com.medpro.medpro.repository.MedicoRepository;
import com.medpro.medpro.repository.PacienteRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DadosAgendamentoConsulta dados) {
        
        if (!pacienteRepository.existsById(dados.idPaciente())) {
            return ResponseEntity.badRequest().body("Id do paciente informado não existe!");
        }
        Medico medico;
        if (dados.idMedico() != null) {
            medico = medicoRepository.findById(dados.idMedico()).orElse(null);
            if (medico == null) {
                return ResponseEntity.badRequest().body("Id do médico informado não existe!");
            }
        } else {
            if (dados.especialidade() == null) {
                return ResponseEntity.badRequest().body("Especialidade é obrigatória quando médico não é escolhido!");
            }
            List<Medico> medicosDaEspecialidade = medicoRepository.findAllByAtivoTrueAndEspecialidade(dados.especialidade());

            List<Medico> medicosLivres = medicosDaEspecialidade.stream()
                .filter(m -> !consultaRepository.existsByMedicoIdAndDataConsulta(m.getId(), dados.data())).toList();

            if (medicosLivres.isEmpty()) {
                return ResponseEntity.badRequest().body("Não existe médico disponível nessa data!");
            }
            medico = medicosLivres.get(new Random().nextInt(medicosLivres.size()));
        }

        var dataConsulta = dados.data();
        var agora = LocalDateTime.now();

        if (Duration.between(agora, dataConsulta).toMinutes() < 30) {
            return ResponseEntity.badRequest().body("Consulta deve ser agendada com antecedência mínima de 30 minutos");
        }

        if (dataConsulta.getDayOfWeek() == DayOfWeek.SUNDAY || dataConsulta.getHour() < 7 || dataConsulta.getHour() > 18) {
            return ResponseEntity.badRequest().body("Consulta fora do horário de funcionamento!");
        }

        var paciente = pacienteRepository.getReferenceById(dados.idPaciente());
        if (!paciente.getAtivo()) {
            return ResponseEntity.badRequest().body("Consulta não pode ser agendada com paciente excluído");
        }
        
        if (!medico.getAtivo()) {
            return ResponseEntity.badRequest().body("Consulta não pode ser agendada com médico excluído");
        }

        if (consultaRepository.existsByMedicoIdAndDataConsulta(medico.getId(), dataConsulta)) {
             return ResponseEntity.badRequest().body("Médico já possui outra consulta agendada nesse mesmo horário");
        }

        var primeiroHorario = dataConsulta.withHour(7);
        var ultimoHorario = dataConsulta.withHour(18);
        
        if (consultaRepository.existsByPacienteIdAndDataConsultaBetween(paciente.getId(), primeiroHorario, ultimoHorario)) {
            return ResponseEntity.badRequest().body("Paciente já possui uma consulta agendada nesse dia");
        }

        var consulta = new Consulta(null, paciente, medico, dataConsulta, null, null);
        consultaRepository.save(consulta);

        return ResponseEntity.ok(new DadosDetalhamentoConsulta(consulta));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancelar(@RequestBody @Valid DadosCancelamentoConsulta dados) {
        if (!consultaRepository.existsById(dados.idConsulta())) {
             return ResponseEntity.badRequest().body("Id da consulta não existe!");
        }

        var consulta = consultaRepository.getReferenceById(dados.idConsulta());

        if (Duration.between(LocalDateTime.now(), consulta.getData_consulta()).toHours() < 24) {
             return ResponseEntity.badRequest().body("Consulta somente pode ser cancelada com antecedência mínima de 24h!");
        }

        consulta.setMotivoCancelamento(dados.motivo()); 

        return ResponseEntity.noContent().build();
    }
}