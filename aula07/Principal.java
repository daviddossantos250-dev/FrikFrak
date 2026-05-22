public class Principal {

    public static void main(String[] args) {

        Lista lista = new Lista();

        // Inserir alunos
        lista.inserir(new Aluno(1, "David Santos", "9910000", 15.0));
        lista.inserir(new Aluno(2, "Maria Silva", "9920000", 12.0));
        lista.inserir(new Aluno(3, "João Tavares", "9930000", 15.0));

        // Tentar inserir duplicado
        lista.inserir(new Aluno(1, "Carlos Lima", "9940000", 10.0));

        // Listar todos
        lista.listarTodos();

        // Pesquisar por nome
        System.out.println("\n--- Pesquisa por nome: Maria Silva ---");
        lista.pesquisar(2, "Maria Silva");

        // Mostrar por nota
        System.out.println("\n--- Alunos com nota 15.0 ---");
        lista.mostrarPorNota(15.0);

        // Remover por ID
        System.out.println("\n--- Remover aluno com ID 2 ---");
        lista.remover(1, "2");

        // Listar depois de remover
        lista.listarTodos();
    }
}
