package br.edu.aesa.service;

import br.edu.aesa.model.Tarefa;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class GerenciadorTarefas {
    private final HashMap<Integer, Tarefa> tarefas = new HashMap<>();

    public boolean cadastrar(Tarefa tarefa) {
        if (tarefa == null || tarefas.containsKey(tarefa.getId())) {
            return false;
        }
        tarefas.put(tarefa.getId(), tarefa);
        return true;
    }

    public Optional<Tarefa> buscarPorId(int id) {
        return Optional.ofNullable(tarefas.get(id));
    }

    public List<Tarefa> listarTodas() {
        return new ArrayList<>(tarefas.values());
    }

    public boolean atualizar(int id, Tarefa novosDados) {
        if (novosDados == null || !tarefas.containsKey(id) || novosDados.getId() != id) {
            return false;
        }
        tarefas.put(id, novosDados);
        return true;
    }

    public boolean remover(int id) {
        return tarefas.remove(id) != null;
    }
}