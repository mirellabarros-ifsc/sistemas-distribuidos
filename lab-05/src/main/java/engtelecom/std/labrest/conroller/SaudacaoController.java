package engtelecom.std.labrest.conroller;

import engtelecom.std.labrest.entities.Saudacao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController

public class SaudacaoController {
    private AtomicInteger contador = new AtomicInteger();

    @GetMapping("/saudacao")
    public Saudacao ola(@RequestParam(value="nome", defaultValue = "Mundo") String nome) {
        return new Saudacao(contador.incrementAndGet(), "Olá, " + nome);
    }
}
