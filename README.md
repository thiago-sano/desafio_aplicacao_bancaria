# Desafio 1 - Aplicação bancária
Repositorio criado para entrega do desafio proposto, fazendo parte do nivelamento de conhecimento de Java.

### Solicitação ###
Faça uma aplicação bancária que permita transferências de valores. O sistema deve conter as seguintes funcionalidades (mínimas):

- Cadastro de conta bancária
- Numero da conta, numero agencia, cliente, saldo, limite, tipo da conta
- Depósito
- Retirada (saque)
- Alteração de limite
- Transferências
- Pensem em limitar o valor de acordo com o horário;
- Exportação de histórico de transações (CSV).

A aplicação deve conter um menu via terminal para seleção da operação desejada.

### Considerações: ###
- Perceba que a descrição do sistema foi feita de forma genérica, propositalmente para encorajar a extensão de funcionalidades de acordo com a sua necessidade.
- As funcionalidades descritas acima são básicas e mandatórias para o funcionamento e aceite da entrega.
- Utilize o máximo de conceitos abordados durante o curso. Ex: menus com Scanner, boas práticas de nomenclatura, herança, listas, interfaces, trabalho com arquivos, etc.
- Não há necessidade de persistência em bancos de dados. Pensem numa estrutura utilizando listas/mapas em memória para armazenamento.
- Os relacionamentos entre as classes (entidades do sistema) ficam ao seu critério. Utilizem quantas classes e atributos julgarem necessário para a modelagem.
- Sigam o princípio: baixo acoplamento, alta coesão.
- Para estruturar seu código, imagine a aplicação como um entregável que possa ser evoluído sem a necessidade de grande refatoração. Ex: não tenho um banco de dados hoje ou uma API Rest para acesso às operações, mas posso construir um módulo sem afetar O DOMÍNIO do sistema (classes de negócio e entidades).
- Pensem que toda operação repetitiva pode ter sua própria classe ou método, como apresentação das informações na tela (ou input), que pode ter dados como parâmetros.

### Requisitos: ###
- O projeto deve estar em repositório público no GitHub.
- O projeto deve conter um README.md com instruções de execução e operação da aplicação.
- Desenhe um diagrama de classes e um de sequência para explicar o funcionamento do sistema. O mesmo deve estar na página explicativa (README) do projeto.
- Sugestão: utilizem MermaidJS: https://github.com/mermaid-js/mermaid/blob/develop/README.md

### Diagrama de classe: ###
```mermaid
classDiagram
    Client <|-- Personal
    Client <|-- Business
    Account <|-- SavingsAccount
    Account <|-- CheckingAccount
    Account <|-- PayrollAccount
    Client "1" *--> "1..*" Account : client
    Client "1..*" --> "1" BankingSystem : client

    BankingSystem "1" <-- "1..*" Account : account
    BankingSystem "1" <-- "1..*" Transaction : transaction

    Account "1" --> "1..*" Transaction : account

    Transaction --|> Withdraw
    Transaction --|> Deposit
    Transaction --|> Transfer
    
    namespace user{
        class Client{
            <<abstract>>
            - name : String
            - email : String
            - id : String
            - clientType : ClientType
        }
        class Personal{

        }
        class Business{
            - statedCapital: Double
        }
        class ClientType{
            <<enum>>
            - PERSONAL
            - BUSINESS
        }
    }
    
    namespace bank{
        class Account {
            <<abstract>>
            - branchNumber : String
            - accountNumber : String
            - balance : Double
            - accountType : AccountType
            - client : Client
        }
        class SavingsAccount{

        }
        class CheckingAccount{
            - limit : Double
        }
        class PayrollAccount{

        }
        class AccountType{
            <<enum>>
            - SAVINGS
            - CHECKING
            - PAYROLL
        }
        class BankingSystem{
            + registerClient(client : Client) Client
            + listClients() Set
            + registerAccount(account : Account) void
            + listAccounts() List
            + recordTransaction (transaction : Transactio) void
            + listTransactions() List
            + filterTransactionsList(transactions : List, account : Account) List
            + findAccountByUserId(accounts : List<Account>, userId : String) Account
            + dateTimeNow() String
            + transactionStatement(transactions : List, account : Accont) void
            + withdrawTransaction(amount : double, account : Account, transactionType : TransactionType) void
            + depositTransaction(amount : double, account : Account, transactionType : TransactionType) void
            + transferTransaction(amount: double, sender : Account, receiver : Account, transactionType : TransactionType) void
        }
    }
    
    namespace transaction{
        class Transaction{
            - amount : double
            - account : Account
            - sender : Account
            - receiver : Account
            - receiverId : String
            - transactionType : TransactionType
            - dateTimeNow : String

            + isWithinTimeRestriction () boolean
            + isBalancePreviewOk(amount : double, account : Account) boolean
            + isTransactionOk(transaction : Transaction) boolen
            + deposit(amount : double, account : Account, transactionType : TransactionType, localDateTime : String) void
            + withdraw(amount : double, account : Account, transactionType : TransactionType, localDateTime : String) void
            + transfer(amount : double, sender : Account, receiver : Account, transactionType : TransactionType, localDateTime : String) void
        }
        class Withdraw{
            <<interface>>
            + withdraw : void
        }
        class Deposit{
            <<interface>>
            + deposit : void
        }
        class Transfer{
            <<interface>>
            + transfer : void
        }
        class TransactionType{
            <<enum>>
            - WITHDRAW
            - DEPOSIT
            - TRANSFER
        }
    }

``` 