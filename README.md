### Projeto Lista de Compras Android App

## 🚀 Fluxo de Trabalho com Git

⚠️ **Nunca use `git checkout` e `git push origin` no mesmo comando!**

### 📌 Criando uma nova branch de tarefa

```bash
# 1. Ir para a main
git checkout main

# 2. Atualizar a main com últimas alterações do repositório remoto
git pull origin main

# 3. Criar uma nova branch baseada na main
git checkout -b feat/nome-da-tarefa

# 4. Adicionar arquivos modificados
git add NomeDoArquivo.java

# 5. Criar o commit
git commit -m "feat: descrição da atividade realizada"

# 6. Enviar para o GitHub
git push origin feat/nome-da-tarefa
```

---

### 🔄 Atualizando todas as branches locais

```bash
git fetch --prune
```

---

### 🔃 Atualizando a branch `main`

```bash
git checkout main
git pull origin main
```

---

### 📝 Convenção de Commits

Use prefixos para indicar o tipo de alteração:

| Tipo      | Descrição                                 |
|-----------|-------------------------------------------|
| feat      | Nova funcionalidade                       |
| fix       | Correção de bug                           |
| refactor  | Refatoração de código (sem alterar comportamento) |
| chore     | Tarefas administrativas ou ajustes menores |
| docs      | Alterações na documentação                |

**Exemplo:**

```bash
git commit -m "feat: adiciona tela de cadastro de pets"
```

---


### Diagrama de Classes

```mermaid  
classDiagram  
     class Main {   
         +List<ListaCompras> listas  
         +criarLista(nome: String): ListaCompras
         +deletarLista(nome: String): ListaCompras
         +editarLista(nome: String): ListaCompras
     }  
 
     class ListaCompras {  
         +Int id
         +String nome  
         +List<ItemLista> produtos  
         +BigDecimal totalCarrinho
     }  
 
     class Produto {  
         +Int id
         +String nome  
         +BigDecimal quantidade  
         +boolean status  
         +BigDecimal precoUnitario
         +BigDecimal precoTotal
         +alterarStatus()
     }  
 
     Main "1" -- "*" ListaCompras : possui  
     ListaCompras "1" -- "*" Produto : contém 
