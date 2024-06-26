package bancocrudspringboot.repository;

import bancocrudspringboot.model.Estacionamento;
import bancocrudspringboot.model.Veiculo;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EstacionamentoRepository extends JpaRepository<Estacionamento, Long>{
    
    @Query(value = "select * from estacionamento where endereco ilike concat('%', :endereco, '%')", nativeQuery = true)
    List<Estacionamento> findEstacionamentoByEndereco(@Param("endereco")String endereco);

    Optional<Estacionamento> findEstacionamentoById(long id);
    
    List<Estacionamento> findEstacionamentoByTempo(int tempo);

    // List<Veiculo> findEstacionamentoByVeiculoId(String veiculo_id);

    @Query(value = "select * from estacionamento where regra ilike concat('%', :regra, '%')", nativeQuery = true)
    List<Estacionamento> findEstacionamentoByRegra(@Param("regra")String regra);

}
