package br.com.reciclagem.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_dispositivos")
public class Dispositivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do material reciclável é obrigatório.")
    private String dispositivo;

    private Double potencia;

    @Min(value = 1, message = "As horas de uso devem ser pelo menos 1.")
    @Min(value = 24, message = "As horas de uso não podem exceder 24.")
    private Integer horasPorDia;

    @Min(value = 1, message = "Os dias de uso devem ser pelo menos 1.")
    @Min(value = 31, message = "Os dias de uso não podem exceder 31.")
    private Integer diasPorMes;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Double calcularConsumoMensal() {
        return (potencia * horasPorDia * diasPorMes) / 1000.0; // Retorna em kWh
    }
}
