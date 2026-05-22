public class Lista {

    No cabeca;

    public Lista() {
        cabeca = null;
    }

    private boolean existeDuplicado(Aluno novo) {
        No actual = cabeca;
        while (actual != null) {
            if (actual.aluno.id == novo.id) {
                System.out.println("Erro: já existe um aluno com o ID " + novo.id);
                return true;
            }
            if (actual.aluno.nome.equalsIgnoreCase(novo.nome)) {
                System.out.println("Erro: já existe um aluno com o nome \"" + novo.nome + "\"");
                return true;
            }
            if (actual.aluno.telefone.equals(novo.telefone)) {
                System.out.println("Erro: já existe um aluno com o telefone " + novo.telefone);
                return true;
            }
            actual = actual.proximo;
        }
        return false;
    }

    public void inserir(Aluno aluno) {
        if (existeDuplicado(aluno)) {
            System.out.println("Inserção cancelada.");
            return;
        }

        No novoNo = new No(aluno);

        if (cabeca == null) {
            cabeca = novoNo;
        } else {
            No actual = cabeca;
            while (actual.proximo != null) {
                actual = actual.proximo;
            }
            actual.proximo = novoNo;
        }
        System.out.println("Aluno inserido com sucesso!");
    }

    public void listarTodos() {
        if (cabeca == null) {
            System.out.println("A lista está vazia.");
            return;
        }
        System.out.println("\n=== Lista de Alunos ===");
        No actual = cabeca;
        int posicao = 1;
        while (actual != null) {
            System.out.print(posicao + " ");
            actual.aluno.mostrar();
            actual = actual.proximo;
            posicao++;
        }
    }

    public void pesquisar(int criterio, String valor) {
        No actual = cabeca;
        boolean encontrou = false;

        System.out.println("\n=== Resultado da Pesquisa ===");
        while (actual != null) {
            boolean corresponde = false;

            if (criterio == 1 && actual.aluno.id == Integer.parseInt(valor)) {
                corresponde = true;
            } else if (criterio == 2 && actual.aluno.nome.equalsIgnoreCase(valor)) {
                corresponde = true;
            } else if (criterio == 3 && actual.aluno.telefone.equals(valor)) {
                corresponde = true;
            }

            if (corresponde) {
                actual.aluno.mostrar();
                encontrou = true;
            }
            actual = actual.proximo;
        }

        if (!encontrou) {
            System.out.println("Nenhum aluno encontrado.");
        }
    }

    public void mostrarPorNota(double nota) {
        No actual = cabeca;
        boolean encontrou = false;

        System.out.println("\n=== Alunos com nota " + nota + " ===");
        while (actual != null) {
            if (actual.aluno.nota == nota) {
                actual.aluno.mostrar();
                encontrou = true;
            }
            actual = actual.proximo;
        }

        if (!encontrou) {
            System.out.println("Nenhum aluno com essa nota.");
        }
    }

    public void remover(int criterio, String valor) {
        if (cabeca == null) {
            System.out.println("A lista está vazia.");
            return;
        }

        boolean removeu = false;

        while (cabeca != null && corresponde(cabeca.aluno, criterio, valor)) {
            cabeca = cabeca.proximo;
            removeu = true;
        }

        if (cabeca != null) {
            No anterior = cabeca;
            No actual = cabeca.proximo;

            while (actual != null) {
                if (corresponde(actual.aluno, criterio, valor)) {
                    anterior.proximo = actual.proximo;
                    removeu = true;
                } else {
                    anterior = actual;
                }
                actual = actual.proximo;
            }
        }

        if (removeu) {
            System.out.println("Aluno(s) removido(s) com sucesso.");
        } else {
            System.out.println("Nenhum aluno encontrado para remover.");
        }
    }

    private boolean corresponde(Aluno aluno, int criterio, String valor) {
        if (criterio == 1) return aluno.id == Integer.parseInt(valor);
        if (criterio == 2) return aluno.nome.equalsIgnoreCase(valor);
        if (criterio == 3) return aluno.telefone.equals(valor);
        return false;
    }
}
