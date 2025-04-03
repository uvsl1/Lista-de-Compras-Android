### Projeto Lista de Compras Android App

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
         +String nome  
         +List<ItemLista> itens  
         +Double totalCarrinho  
         +adicionarItem(produto: Produto, quantidade: Int)  
         +calcularTotalCarrinho(): Double  
     }  
 
     class ItemLista {  
         +String nomeProduto  
         +int quantidade  
         +boolean pego  
         +Double precoUnitario
         +Double precoTotal
         +marcarComoPego(preco: Double)
     }  
 
     Main "1" -- "*" ListaCompras : possui  
     ListaCompras "1" -- "*" ItemLista : contém 
