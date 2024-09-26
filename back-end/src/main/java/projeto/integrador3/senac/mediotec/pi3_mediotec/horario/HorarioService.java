package projeto.integrador3.senac.mediotec.pi3_mediotec.horario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.Coordenacao;
import projeto.integrador3.senac.mediotec.pi3_mediotec.coordenacao.CoordenacaoRepository;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessor;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorId;
import projeto.integrador3.senac.mediotec.pi3_mediotec.turmaDisciplinaProfessor.TurmaDisciplinaProfessorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private CoordenacaoRepository coordenacaoRepository;

    @Autowired
    private TurmaDisciplinaProfessorRepository turmaDisciplinaProfessorRepository;

    @Transactional
    public Horario salvarHorario(Horario horario) {
        validarHorario(horario);
        return horarioRepository.save(horario);
    }

    @Transactional
    public HorarioDTO salvarHorarioDTO(HorarioDTO horarioDTO) {
        Horario horario = convertToEntity(horarioDTO);
        validarHorario(horario);
        Horario savedHorario = horarioRepository.save(horario);
        return convertToDTO(savedHorario);
    }

    @Transactional
    public HorarioDTO atualizarHorario(Long id, HorarioDTO horarioDTO) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado"));

        horario.setDiaSemana(DiaSemana.valueOf(horarioDTO.getDiaSemana()));
        horario.setHoraInicio(horarioDTO.getHoraInicio());
        horario.setHoraFim(horarioDTO.getHoraFim());
        horario.setTurmaDisciplinaProfessor(buscarTurmaDisciplinaProfessorPorId(horarioDTO.getTurmaDisciplinaProfessorId()));

        validarHorario(horario);

        Horario updatedHorario = horarioRepository.save(horario);
        return convertToDTO(updatedHorario);
    }

    public List<HorarioDTO> listarHorariosPorTurma(Long turmaId) {
        return horarioRepository.findAll().stream()
                .filter(horario -> horario.getTurmaDisciplinaProfessor().getTurma().getId().equals(turmaId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<HorarioDTO> buscarHorarioPorId(Long id) {
        return horarioRepository.findById(id)
                .map(this::convertToDTO);
    }

    public void deletarHorario(Long id) {
        Horario horario = horarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horário não encontrado"));
        horarioRepository.delete(horario);
    }

    private void validarHorario(Horario horario) {
        if (horario.getHoraFim().isBefore(horario.getHoraInicio())) {
            throw new RuntimeException("Hora de término deve ser após a hora de início.");
        }
    }

    private HorarioDTO convertToDTO(Horario horario) {
        return HorarioDTO.builder()
                .idHorario(horario.getId_horario())
                .diaSemana(horario.getDiaSemana().name())
                .horaInicio(horario.getHoraInicio())
                .horaFim(horario.getHoraFim())
                .turmaDisciplinaProfessorId(horario.getTurmaDisciplinaProfessor().getId())
                .build();
    }

    private Horario convertToEntity(HorarioDTO horarioDTO) {
        TurmaDisciplinaProfessorId turmaDisciplinaProfessorId = horarioDTO.getTurmaDisciplinaProfessorId();

        return Horario.builder()
                .id_horario(horarioDTO.getIdHorario())
                .diaSemana(DiaSemana.valueOf(horarioDTO.getDiaSemana()))
                .horaInicio(horarioDTO.getHoraInicio())
                .horaFim(horarioDTO.getHoraFim())
                .turmaDisciplinaProfessor(buscarTurmaDisciplinaProfessorPorId(turmaDisciplinaProfessorId))
                .build();
    }


    private TurmaDisciplinaProfessor buscarTurmaDisciplinaProfessorPorId(TurmaDisciplinaProfessorId turmaDisciplinaProfessorId) {
        return turmaDisciplinaProfessorRepository.findById(turmaDisciplinaProfessorId)
                .orElseThrow(() -> new RuntimeException("TurmaDisciplinaProfessor não encontrado"));
    }
}
