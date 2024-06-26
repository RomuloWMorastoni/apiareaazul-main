package bancocrudspringboot.controller;

import bancocrudspringboot.exception.ResourceNotFoundException;
import bancocrudspringboot.model.ConsultaPadrao;
import bancocrudspringboot.model.OperadoresConsulta;
import bancocrudspringboot.model.Perfil;
import bancocrudspringboot.repository.PerfilRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

    @CrossOrigin
    @RestController
    @RequestMapping("/api/v1")
    public class PerfilController {
    
        @Autowired
        private PerfilRepository perfilRepository;
    
        // Listar todos os perfis
        @GetMapping("/perfil")
        @ResponseStatus(HttpStatus.OK)
        public List<Perfil> getAllCadastros() {
            return this.perfilRepository.findAll();
        }
    
        // Listar um perfil
        @GetMapping("/perfil/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Perfil> getCadastroById(@PathVariable(value = "id") Long cadastroId)
        throws ResourceNotFoundException {
            Perfil cadastro = perfilRepository.findById(cadastroId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cadastro de perfil não encontrado para o ID : " + cadastroId));
            
            return ResponseEntity.ok().body(cadastro);
        }
            
        // Inserir perfil
        @PostMapping("/perfil")
        @ResponseStatus(HttpStatus.CREATED)
        public Perfil createCadastro(@RequestBody Perfil cadastro) {
            return this.perfilRepository.save(cadastro);
        }
    
        /// alterar perfil    
        @PutMapping("/perfil/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ResponseEntity<Perfil> updateCadastro(@PathVariable(value = "id") Long cadastroId,
                                                      @Validated 
                                                      @RequestBody Perfil cadastroCaracteristicas) throws ResourceNotFoundException {
            Perfil cadastro = perfilRepository.findById(cadastroId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado para o ID : " + cadastroId));
    
            cadastro.setNome(cadastroCaracteristicas.getNome());
            cadastro.setCpf(cadastroCaracteristicas.getCpf());
            cadastro.setEmail(cadastroCaracteristicas.getEmail());
            ;
    
            return ResponseEntity.ok(this.perfilRepository.save(cadastro));
        }
    
        // deletar perfil
        @DeleteMapping("/perfil/{id}")
        @ResponseStatus(HttpStatus.OK)
        public Map<String, Boolean> deleteCadastro(@PathVariable(value = "id") Long cadastroId)
                throws ResourceNotFoundException {
            Perfil cadastro = perfilRepository.findById(cadastroId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cadastro não encontrado para o ID : " + cadastroId));
    
            this.perfilRepository.delete(cadastro);
    
            Map<String, Boolean> resposta = new HashMap<>();
    
            resposta.put("cadastro deletado", Boolean.TRUE);
    
            return resposta;
        }
    
    
        // consulta por campo e operadores no insomnia em POST consulta
        @PostMapping("/consultaperfil")
        @ResponseStatus(HttpStatus.OK)
        public List<Perfil> consultaCadastro(@Validated @RequestBody ConsultaPadrao cadastro) throws ResourceNotFoundException {
    
            String campoConsulta = cadastro.getCampo();
            List<Perfil> listaperfil = new ArrayList<>();
    
            // conulta produto por valor1=id, onde pesquisa pelo id existente ou ""(vazio) onde retorna todos (findAll)
            if(cadastro.getValor1() == null){
                return this.perfilRepository.findAll();
            } else if(cadastro.getValor1().equals("")){
                return this.perfilRepository.findAll();
            }
    
    
            String operador = cadastro.getOperador();
            if(operador.equals(OperadoresConsulta.OPERADOR_TODOS)){
                return this.perfilRepository.findAll();
            }
    
    
    
        
            if(operador.equals(OperadoresConsulta.OPERADOR_IGUAL)){
                switch (campoConsulta) {
    
                    // valor1 = id
                    case "codigoConsulta":
                        Perfil perfil = perfilRepository.findPerfilById(Long.parseLong(cadastro.getValor1()))
                                .orElseThrow(() -> new ResourceNotFoundException("Registro não encontrado para o perfil de ID: " + cadastro.getValor1()));
                        listaperfil.add(perfil);
                        break;
    
                    // tipo(de perfil) ---  (1 = MOTO)   (2 = CARRO)  (3 = CAMINHÃO)
                    // no insomnia procurar por
    
                    // nome
                    case "nomeConsulta":
                        listaperfil = this.perfilRepository.findPerfilByNome(cadastro.getValor1());
                        break;
    
                    // cpf
                    case "cpfConsulta":
                        listaperfil = this.perfilRepository.findPerfilByCpf(cadastro.getValor1());
                        break;
    
    
                    case "emailConsulta":
                        listaperfil = this.perfilRepository.findPerfilByEmail(cadastro.getValor1());
                        break;		
            
            
                    default:
                        throw new ResourceNotFoundException("Campo inexistente na tabela do banco de dados!" + cadastro.getCampo());				
                }
    
            }
    
            return listaperfil;
        } 
    
    }