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
