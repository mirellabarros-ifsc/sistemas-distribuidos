package engtelecom.std.labrest.controller;

import engtelecom.std.labrest.dto.PessoaEmailDTO;
import engtelecom.std.labrest.entities.Pessoa;
import engtelecom.std.labrest.exceptions.PessoaNaoEncontradaException;
import engtelecom.std.labrest.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
// Mapeia as URLs /pessoas e /pessoas/ para esse controller
// Se desejar usar versionamento de API, basta adicionar a versão. Ex: /v1/pessoas
@RequestMapping({"/pessoas", "/pessoas/"})
public class AgendaController {
    @Autowired // injeta uma instância de PessoaService que está anotada com @Component
    private PessoaService pessoaService;
    @GetMapping
    public List<Pessoa> obterTodasPessoas() {
        return this.pessoaService.buscarTodos();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Pessoa obterPessoa(@PathVariable long id) {
        Pessoa p = this.pessoaService.buscarPorId(id);
        if (p != null) {
            return p;
        }
        throw new PessoaNaoEncontradaException(id);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<Pessoa> adicionarPessoa(@RequestBody Pessoa p) {
        Pessoa novaPessoa = this.pessoaService.cadastrar(p);
        // Retorna o status 201 e os links no corpo (HATEOAS) que indicam quais ações o cliente pode realizar a seguir
        return EntityModel.of(novaPessoa,
                linkTo(methodOn(AgendaController.class).obterPessoa(novaPessoa.getId())).withSelfRel(),
                linkTo(methodOn(AgendaController.class).obterTodasPessoas()).withRel("listar-todos"));
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Pessoa atualizarPessoa(@PathVariable long id, @RequestBody Pessoa pessoa) {
        // Garante que o ID do objeto informado no corpo seja o mesmo da URL
        pessoa.setId(id);
        Pessoa p = this.pessoaService.atualizar(pessoa);
        if (p != null) {
            return p;
        }
        throw new PessoaNaoEncontradaException(id);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizarParcial(@PathVariable long id, @Valid @RequestBody PessoaEmailDTO dto) {
        Pessoa pessoa = this.pessoaService.buscarPorId(id);
        if (pessoa != null) {
            pessoa.setEmail(dto.email());
            this.pessoaService.atualizar(pessoa);
        } else {
            throw new PessoaNaoEncontradaException(id);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPessoa(@PathVariable long id) {
        if (!this.pessoaService.excluir(id)) {
            throw new PessoaNaoEncontradaException(id);
        }
    }
    @ControllerAdvice
    static class PessoaNaoEncontrada {
        @ResponseBody
        @ExceptionHandler(PessoaNaoEncontradaException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String pessoaNaoEncontrada(PessoaNaoEncontradaException p) {
            return p.getMessage();
        }
    }
}
