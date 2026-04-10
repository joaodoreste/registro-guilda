package br.com.infnet.registroguilda.operacoes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vw_painel_tatico_missao", schema = "operacoes")
public class PainelTaticoMissao {

    @Id
    @Column(name = "missao_id")
    private Long missaoId;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "status")
    private String status;

    @Column(name = "nivel_perigo")
    private String nivelPerigo;

    @Column(name = "organizacao_id")
    private Long organizacaoId;

    @Column(name = "total_participantes")
    private Long totalParticipantes;

    @Column(name = "nivel_medio_equipe")
    private BigDecimal nivelMedioEquipe;

    @Column(name = "total_recompensa")
    private BigDecimal totalRecompensa;

    @Column(name = "total_mvps")
    private Long totalMvps;

    @Column(name = "participantes_com_companheiro")
    private Long participantesComCompanheiro;

    @Column(name = "ultima_atualizacao")
    private LocalDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    private BigDecimal indiceProntidao;

    public Long getMissaoId() {
        return missaoId;
    }

    public void setMissaoId(Long missaoId) {
        this.missaoId = missaoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNivelPerigo() {
        return nivelPerigo;
    }

    public void setNivelPerigo(String nivelPerigo) {
        this.nivelPerigo = nivelPerigo;
    }

    public Long getOrganizacaoId() {
        return organizacaoId;
    }

    public void setOrganizacaoId(Long organizacaoId) {
        this.organizacaoId = organizacaoId;
    }

    public Long getTotalParticipantes() {
        return totalParticipantes;
    }

    public void setTotalParticipantes(Long totalParticipantes) {
        this.totalParticipantes = totalParticipantes;
    }

    public BigDecimal getNivelMedioEquipe() {
        return nivelMedioEquipe;
    }

    public void setNivelMedioEquipe(BigDecimal nivelMedioEquipe) {
        this.nivelMedioEquipe = nivelMedioEquipe;
    }

    public BigDecimal getTotalRecompensa() {
        return totalRecompensa;
    }

    public void setTotalRecompensa(BigDecimal totalRecompensa) {
        this.totalRecompensa = totalRecompensa;
    }

    public Long getTotalMvps() {
        return totalMvps;
    }

    public void setTotalMvps(Long totalMvps) {
        this.totalMvps = totalMvps;
    }

    public Long getParticipantesComCompanheiro() {
        return participantesComCompanheiro;
    }

    public void setParticipantesComCompanheiro(Long participantesComCompanheiro) {
        this.participantesComCompanheiro = participantesComCompanheiro;
    }

    public LocalDateTime getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(LocalDateTime ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public BigDecimal getIndiceProntidao() {
        return indiceProntidao;
    }

    public void setIndiceProntidao(BigDecimal indiceProntidao) {
        this.indiceProntidao = indiceProntidao;
    }
}
