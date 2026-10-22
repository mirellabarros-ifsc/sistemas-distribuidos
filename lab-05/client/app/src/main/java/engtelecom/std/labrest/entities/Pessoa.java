package engtelecom.std.labrest.entities;

public record Pessoa(long id, String nome, String email) {
    @Override
    public String toString() {
        return String.format("Id: %d%nNome: %s%nEmail: %s", id, nome, email);
    }
}