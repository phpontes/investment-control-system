package com.itau.investments.investmentcontrolsystem.repository;

import com.itau.investments.investmentcontrolsystem.enums.TipoOperacao;
import com.itau.investments.investmentcontrolsystem.model.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OperacaoRepository extends JpaRepository<Operacao, Long> {

    List<Operacao> findByUsuario(Usuario usuario);

    List<Operacao> findByUsuarioAndAtivo(Usuario usuario, Ativo ativo);

    List<Operacao> findByUsuarioOrderByDataHoraDesc(Usuario usuario);

    List<Operacao> findByUsuarioAndTipoOperacao(Usuario usuario, TipoOperacao tipo);

    @Query("SELECT o FROM Operacao o WHERE o.usuario.id = :usuarioId AND o.ativo.id = :ativoId AND o.dataHora >= :dataInicio ORDER BY o.dataHora DESC")
    List<Operacao> findByUsuarioIdAndAtivoIdAndDataHoraAfter(
        @Param("usuarioId") Long usuarioId,
        @Param("ativoId") Long ativoId,
        @Param("dataInicio") LocalDateTime dataInicio
    );

    List<Operacao> findByUsuarioIdAndAtivoIdAndTipoOperacaoOrderByDataHoraAsc(
        Long usuarioId,
        Long ativoId,
        TipoOperacao tipoOperacao
    );

    @Query("SELECT SUM(o.corretagem) FROM Operacao o WHERE o.usuario = :usuario")
    BigDecimal totalCorretagemPorUsuario(@Param("usuario") Usuario usuario);

    @Query("SELECT o.usuario.id, SUM(o.corretagem) FROM Operacao o GROUP BY o.usuario.id")
    List<Object[]> findTotalCorretagemByUsuario();
}
