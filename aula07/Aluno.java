public class Aluno {
    int id;
    String nome;
    String telefone;
    double nota;

    public Aluno(int id, String nome, String telefone, double nota) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.nota = nota;
    }

    public void mostrar() {
        System.out.println("ID: " + id + " | Nome: " + nome +
                           " | Telefone: " + telefone + " | Nota: " + nota);
    }
}
