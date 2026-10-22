package engtelecom.std.labrest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PessoaEmailDTO(
        @NotBlank(message = "email não pode estar em branco")
        @Email(message = "endereço de email inválido")
        String email
) {}
