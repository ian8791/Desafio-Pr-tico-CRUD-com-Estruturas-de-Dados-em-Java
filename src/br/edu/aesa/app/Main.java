package br.edu.aesa.app;

import br.edu.aesa.model.Tarefa;
import br.edu.aesa.service.GerenciadorTarefas;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final GerenciadorTarefas gerenciador = new GerenciadorTarefas();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ", 0, 5);
            switch (opcao) {
                case 1 -> cadastrarTarefa();
                case 2 -> listarTarefas();
                case 3 -> buscarTarefa();
                case 4 -> atualizarTarefa();
                case 5 -> removerTarefa();
                case 0 -> System.out.println("Programa encerrado.");
                default -> System.out.println("Opção inválida.");
            }
            System.out.println();
        } while (opcao != 0);
    }

    private static void exibirMenu() {
        System.out.println("=== GERENCIADOR DE TAREFAS ===");
        System.out.println("[1] Cadastrar tarefa");
        System.out.println("[2] Listar tarefas");
        System.out.println("[3] Buscar tarefa por ID");
        System.out.println("[4] Atualizar tarefa");
        System.out.println("[5] Remover tarefa");
        System.out.println("[0] Sair");
    }

    private static void cadastrarTarefa() {
        int id = lerInteiro("ID: ", 1, Integer.MAX_VALUE);
        Tarefa tarefa = lerDadosTarefa(id, false);
        if (gerenciador.cadastrar(tarefa)) {
            System.out.println("Tarefa cadastrada com sucesso!");
        } else {
            System.out.println("Já existe uma tarefa com esse ID.");
        }
    }

    private static void listarTarefas() {
        List<Tarefa> tarefas = gerenciador.listarTodas();
        if (tarefas.isEmpty()) {
            System.out.println("Não há tarefas cadastradas.");
            return;
        }
        tarefas.forEach(System.out::println);
    }

    private static void buscarTarefa() {
        int id = lerInteiro("ID da tarefa: ", 1, Integer.MAX_VALUE);
        Optional<Tarefa> tarefa = gerenciador.buscarPorId(id);
        if (tarefa.isPresent()) {
            System.out.println(tarefa.get());
        } else {
            System.out.println("Nenhuma tarefa encontrada com o ID informado.");
        }
    }

    private static void atualizarTarefa() {
        int id = lerInteiro("ID da tarefa a atualizar: ", 1, Integer.MAX_VALUE);
        if (gerenciador.buscarPorId(id).isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada com o ID informado.");
            return;
        }
        Tarefa novosDados = lerDadosTarefa(id, true);
        if (gerenciador.atualizar(id, novosDados)) {
            System.out.println("Tarefa atualizada com sucesso!");
        }
    }

    private static void removerTarefa() {
        int id = lerInteiro("ID da tarefa a remover: ", 1, Integer.MAX_VALUE);
        if (gerenciador.remover(id)) {
            System.out.println("Tarefa removida com sucesso!");
        } else {
            System.out.println("Nenhuma tarefa encontrada com o ID informado.");
        }
    }

    private static Tarefa lerDadosTarefa(int id, boolean atualizacao) {
        System.out.print("Título: ");
        String titulo = scanner.nextLine().trim();
        while (titulo.isEmpty()) {
            System.out.println("O título não pode ficar vazio.");
            System.out.print("Título: ");
            titulo = scanner.nextLine().trim();
        }

        int prioridade = lerInteiro("Prioridade (1 a 5): ", 1, 5);
        boolean concluida = false;
        if (atualizacao) {
            concluida = lerInteiro("Status: [1] Pendente [2] Concluída: ", 1, 2) == 2;
        }
        return new Tarefa(id, titulo, prioridade, concluida);
    }

    private static int lerInteiro(String mensagem, int minimo, int maximo) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Informe um número entre " + minimo + " e " + maximo + ".");
        }
    }
}