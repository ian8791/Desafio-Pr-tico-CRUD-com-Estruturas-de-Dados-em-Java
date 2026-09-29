  Gerenciador de Tarefas

Aplicação de console em Java para cadastrar e administrar tarefas usando operações CRUD e a estrutura de dados `HashMap`.

 O que a aplicação faz?

Cada tarefa possui um ID, um título, uma prioridade de 1 a 5 e um status (pendente ou concluída). Pelo menu, é possível cadastrar, listar, buscar, atualizar e remover tarefas. Os dados ficam na memória e são perdidos quando o programa é encerrado; não há banco de dados ou gravação em arquivo.

   Tecnologias e recursos

- Java 17 ou superior.
- `Scanner`, para ler entradas no terminal.
- `HashMap<Integer, Tarefa>`, para guardar tarefas indexadas pelo ID.
- `ArrayList`, para devolver uma listagem independente do mapa.
- `Optional<Tarefa>`, para representar uma busca que pode não encontrar resultado.
- Pacotes Java (`model`, `service` e `app`), para separar responsabilidades.

Não são necessárias bibliotecas externas.

   Organização dos arquivos

```text
src/
└── br/edu/aesa/
	├── model/Tarefa.java
	├── service/GerenciadorTarefas.java
	└── app/Main.java
```

- [Tarefa.java](src/br/edu/aesa/model/Tarefa.java) define os dados e as regras básicas de validade de uma tarefa.
- [GerenciadorTarefas.java](src/br/edu/aesa/service/GerenciadorTarefas.java) guarda as tarefas e implementa as operações CRUD.
- [Main.java](src/br/edu/aesa/app/Main.java) mostra o menu, lê as respostas e chama o serviço.

  Como a solução foi organizada

1. **Definir o domínio:** escolheu-se um gerenciador de tarefas. Seus campos são ID, título, prioridade e status.
2. **Criar o modelo:** `Tarefa` representa um registro. Seu construtor rejeita ID não positivo, título nulo ou vazio e prioridade fora do intervalo de 1 a 5. Os campos são `final`, portanto, depois que uma tarefa é criada, seus dados não são alterados diretamente.
3. **Escolher a estrutura:** cada tarefa é armazenada em um `HashMap`, usando o ID como chave. Isso atende à necessidade de localizar registros por identificador.
4. **Implementar o serviço:** `GerenciadorTarefas` encapsula o mapa e oferece cadastro, busca, listagem, atualização e remoção sem expor o mapa à classe do menu.
5. **Criar a interação:** `Main` mantém o menu em um laço até a opção `0`. As entradas são lidas como texto e convertidas com validação, evitando que uma resposta não numérica encerre o programa com erro.
6. **Verificar os fluxos:** o programa foi compilado com compatibilidade Java 17 e executado para verificar cadastro, listagem, busca encontrada e não encontrada, atualização e remoção.

  Por que usar `HashMap`?

O mapa associa uma chave (`Integer`, o ID) ao objeto `Tarefa`. Assim, para buscar um registro, o serviço consulta `tarefas.get(id)` em vez de percorrer uma lista inteira.

| Operação | Custo médio | Motivo |
| --- | --- | --- |
| Cadastrar | O(1) | Insere usando o ID como chave. |
| Buscar | O(1) | Acessa o mapa pela chave. |
| Atualizar | O(1) | Substitui o valor associado ao ID. |
| Remover | O(1) | Remove o valor pela chave. |
| Listar | O(n) | Precisa percorrer as tarefas armazenadas. |

Esses custos de acesso ao mapa são médios/esperados; não há garantia de ordem na listagem. O consumo de memória cresce proporcionalmente ao número de tarefas, O(n). Para este exercício, a busca frequente por ID justifica a escolha.

  Como o CRUD funciona

- **Create (`cadastrar`)**: recebe uma `Tarefa`, recusa valores nulos ou IDs já cadastrados e insere o registro com `put`.
- **Read (`buscarPorId`)**: procura com `get` e devolve um `Optional`. Se não houver tarefa para aquele ID, devolve `Optional.empty()`. `listarTodas` cria uma nova lista com os valores do mapa; a lista pode ser percorrida sem permitir que quem a recebeu altere diretamente a estrutura interna.
- **Update (`atualizar`)**: só substitui a tarefa se o ID já existir e corresponder ao ID do novo objeto. Como `Tarefa` é imutável, a atualização cria outro objeto e o coloca no mapa com `put`.
- **Delete (`remover`)**: usa `remove`. O resultado indica se havia uma tarefa para aquele ID.

No menu, as respostas booleanas do serviço são convertidas em mensagens de sucesso ou de não encontrado. Uma tentativa de listar sem registros também recebe uma mensagem específica.
 
  Executar no VS Code (Windows / PowerShell)

Abra a pasta do projeto no VS Code e abra o terminal integrado com **Ctrl+`**. Como neste computador o JDK está em `C:\Program Files\Java\jdk-27` e não está adicionado permanentemente ao `PATH`, configure-o para a sessão atual e compile:

```powershell
Set-Location 'C:\Users\ian\Desktop\tramp'
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-27'
$env:Path = "$env:JAVA_HOME\bin;$env:Path"
javac --release 17 -encoding UTF-8 -d out src/br/edu/aesa/model/Tarefa.java src/br/edu/aesa/service/GerenciadorTarefas.java src/br/edu/aesa/app/Main.java
```

Se a compilação terminar sem mensagens de erro, inicie o programa:

```powershell
java -cp out br.edu.aesa.app.Main
```

O menu aparecerá no terminal. Digite o número da opção e pressione Enter. Para encerrar, escolha `0`. Se abrir um novo terminal, repita a configuração de `JAVA_HOME` e `PATH` antes dos comandos.

 Roteiro para demonstrar

1. Apresente o tema e os campos da entidade `Tarefa`.
2. Explique que o `HashMap` usa o ID como chave, seus custos médios e que a listagem não tem ordem garantida.
3. Mostre como `GerenciadorTarefas` valida duplicidade e implementa as quatro operações.
4. No programa, cadastre três tarefas com IDs diferentes e liste todas.
5. Busque um ID cadastrado e depois um ID inexistente.
6. Atualize uma tarefa e liste novamente para mostrar a alteração.
7. Remova uma tarefa, liste outra vez para confirmar a exclusão e encerre com `0`.

Ao gravar, informe seu nome, curso e disciplina. Mostre o código e execute o programa ao vivo, com áudio claro e tela legível.