package br.edu.aesa.model;

public class Tarefa {
    private final int id;
    private final String titulo;
    private final int prioridade;
    private final boolean concluida;

    public Tarefa(int id, String titulo, int prioridade, boolean concluida) {
        if (id <= 0) {
            throw new IllegalArgumentException("O ID deve ser positivo.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título não pode ficar vazio.");
        }
        if (prioridade < 1 || prioridade > 5) {
            throw new IllegalArgumentException("A prioridade deve estar entre 1 e 5.");
        }

        this.id = id;
        this.titulo = titulo.trim();
        this.prioridade = prioridade;
        this.concluida = concluida;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public boolean isConcluida() {
        return concluida;
    }

    @Override
    public String toString() {
        String status = concluida ? "Concluída" : "Pendente";
        return "ID: " + id + " | Título: " + titulo
                + " | Prioridade: " + prioridade + " | Status: " + status;
    }
}