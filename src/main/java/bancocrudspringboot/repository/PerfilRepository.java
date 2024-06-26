package bancocrudspringboot.repository;

import java.util.*;
import bancocrudspringboot.model.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


    @Repository
public interface PerfilRepository extends JpaRepository<Perfil , Long>{
    
    @Query(value = "select * from perfil  where nome ilike concat('%', :nome, '%')", nativeQuery = true)
    List<Perfil > findPerfilByNome(@Param("nome")String nome);


    Optional<Perfil > findPerfilById(long id);
    
    List<Perfil > findPerfilByCpf(String string);

    @Query(value = "select * from perfil  where email ilike concat('%', :email, '%')", nativeQuery = true)
    List<Perfil > findPerfilByEmail(@Param("email")String email);
    
}