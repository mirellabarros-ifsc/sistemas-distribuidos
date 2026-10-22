package engtelecom.std.labrest.entities;

import lombok.*;

@Data
public class Pessoa {

    private Long id;

    @NonNull
    private String nome;

    @NonNull
    private String email;

}
