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

### Observações: ###
- Ciente de não ser uma boa prática, foi incluído o cadastro hardcode de quatro contas inciais. Tal escolha teve como objetivo apenas o retrabalhos sempre que o programa fosse executado.
- Por tal motivo, também foi mantido a opção no menu inicial de LISTAR CONTAS, ciente também de que tais informações não deveriam ficar expostas para qualquer tipo de usuário do sistema.


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
            - limit : Double
            - startTime : LocalTime
            - endTime : LocalTime
            + availableLimit() double
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
            + transactionStatement(transactions : List, account : Account) void
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
            - transactionType : TransactionType
            - dateTimeNow : String

            + isWithinTimeRestriction (account : Account) boolean
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

### Diagramas de sequencia: ###
## Acesso de agência ##
```mermaid
sequenceDiagram
actor branchEmployee
UI ->> branchEmployee: show main menu
branchEmployee ->> UI: select register account menu option
UI --) branchEmployee: request client info
branchEmployee ->> UI: input client info
UI ->> BankingSystem: client id already exists?
alt is true
    BankingSystem --) UI: cancel registration and reload UI
    UI ->> branchEmployee: show main menu
else is false
    BankingSystem -) BankingSystem: register client
    UI --) branchEmployee: request account info
    branchEmployee ->> UI: input account info
    UI ->> BankingSystem: object Account
    BankingSystem ->> BankingSystem: register account
    BankingSystem --) UI: reload UI
    UI ->> branchEmployee: show main menu
end
```
## Acesso de cliente ##
```mermaid
sequenceDiagram
actor client
UI ->> client: show main menu
client ->> UI: select account log in menu option
UI --) client: request user id (CPF or CNPJ)
client ->> UI: input client info
UI ->> BankingSystem: client id already exists?
alt is true
    BankingSystem --) UI: perform login
    UI ->> client: show client main menu
    note right of client: when logged in, client could have 6 operations available
else is false
    BankingSystem --) UI: cancel login and reload UI
    UI ->> client: show main menu    
end
```

## Operação: saque ##
```mermaid
sequenceDiagram
actor client
UI ->> client: show client main menu
client ->> UI: select withdraw operation in menu option
UI --) client: request amount
client ->> UI: input amount info
UI ->> BankingSystem: pass info as parameters
BankingSystem ->> BankingSystem: uses infos to construct an object Transaction
BankingSystem ->> Transaction: check if the transaction complies with business rules
Transaction ->> Transaction: is time now within time restriction?
alt is true
        Transaction ->> Transaction: is amount <= 1000?
        alt is true
                Transaction ->> Transaction: is balance preview ok?
                alt is true
                    Transaction ->> Account: update balance
                    Transaction --) BankingSystem: transaction done
                    BankingSystem ->> BankingSystem: record transaction
                    BankingSystem --) UI: reload UI
                    UI ->> client: show client main menu
                else is false
                    Transaction --) BankingSystem: cancel transaction
                    BankingSystem --) UI: reload UI
                    UI ->> client: show client main menu
                end
        else is false
            Transaction --) BankingSystem: cancel transaction
            BankingSystem --) UI: reload UI
            UI ->> client: show client main menu
        end
else is false
        Transaction ->> Transaction: is balance preview ok?
        alt is true
            Transaction ->> Account: update balance
            Transaction --) BankingSystem: transaction done
            BankingSystem ->> BankingSystem: record transaction
            BankingSystem --) UI: reload UI
            UI ->> client: show client main menu
        else is false
            Transaction --) BankingSystem: cancel transaction
            BankingSystem --) UI: reload UI
            UI ->> client: show client main menu
        end
end
```

## Operação: deposito ##
```mermaid
sequenceDiagram
actor client
UI ->> client: show client main menu
client ->> UI: select deposit operation in menu option
UI --) client: request amount
client ->> UI: input amount info
UI ->> BankingSystem: pass info as parameters
BankingSystem ->> BankingSystem: uses infos to construct an object Transaction
BankingSystem ->> Transaction: check if the transaction complies with business rules
Transaction ->> Transaction: is a payroll account?
alt is true
    Transaction --) BankingSystem: cancel transaction
    BankingSystem --) UI: reload UI
    UI ->> client: show client main menu
else is false
    Transaction ->> Account: update balance
    Transaction --) BankingSystem: transaction done
    BankingSystem ->> BankingSystem: record transaction
    BankingSystem --) UI: reload UI
    UI ->> client: show client main menu
end
```

## Operação: transferencia ##
```mermaid
sequenceDiagram
actor client
UI ->> client: show client main menu
client ->> UI: select transfer operation in menu option
UI --) client: request receiver id
client ->> UI: input receiver id info
UI ->> BankingSystem: client id already exists?
alt is true
    UI --) client: request amount
    client ->> UI: input amount info
    UI ->> BankingSystem: pass info as parameters
    BankingSystem ->> BankingSystem: uses infos to construct an object Transaction
    BankingSystem ->> Transaction: check if the transaction complies with business rules
    par Transaction to Transaction
        Transaction ->> Transaction: is receiver account a payroll account and is sender client not a business client?
        alt is true
            Transaction --) BankingSystem: cancel transaction
            BankingSystem --) UI: reload UI
            UI ->> client: show client main menu
        end
    end
    par Transaction to Transaction
        Transaction ->> Transaction: is sender account a payroll account and is receiver client id not equals sender client id?
        alt is true
            Transaction --) BankingSystem: cancel transaction
            BankingSystem --) UI: reload UI
            UI ->> client: show client main menu
        end
    end
    par Transaction to Transaction
        Transaction ->> Transaction: are sender client id and receiver client id equals?
        alt is true
            Transaction --) BankingSystem: cancel transaction
            BankingSystem --) UI: reload UI
            UI ->> client: show client main menu
        end
    end
    par Transaction to Transaction
        Transaction ->> Transaction: is time now within time restriction?
        alt is true
                Transaction ->> Transaction: is amount <= 1000?
                alt is true
                        Transaction ->> Transaction: is balance preview ok?
                        alt is true
                            Transaction ->> Account: update sender balance
                            Transaction ->> Account: update receiver balance
                            Transaction --) BankingSystem: transaction done
                            BankingSystem ->> BankingSystem: record sender transaction
                            BankingSystem ->> BankingSystem: record receiver transaction
                            BankingSystem --) UI: reload UI
                            UI ->> client: show client main menu
                        else is false
                            Transaction --) BankingSystem: cancel transaction
                            BankingSystem --) UI: reload UI
                            UI ->> client: show client main menu
                        end
                else is false
                    Transaction --) BankingSystem: cancel transaction
                    BankingSystem --) UI: reload UI
                    UI ->> client: show client main menu
                end
        else is false
                Transaction ->> Transaction: is balance preview ok?
                alt is true
                    Transaction ->> Account: update sender balance
                    Transaction ->> Account: update receiver balance
                    Transaction --) BankingSystem: transaction done
                    BankingSystem ->> BankingSystem: record sender transaction
                    BankingSystem ->> BankingSystem: record receiver transaction
                    BankingSystem --) UI: reload UI
                    UI ->> client: show client main menu
                else is false
                    Transaction --) BankingSystem: cancel transaction
                    BankingSystem --) UI: reload UI
                    UI ->> client: show client main menu
                end
        end
    end
else is false
    BankingSystem --) UI: cancel transaction and reload UI
    UI ->> client: show client main menu
end
```

## Operação: extrato ##
```mermaid
sequenceDiagram
actor client
UI ->> client: show client main menu
client ->> UI: select transfer operation in menu option
UI ->> BankingSystem: pass acount info as parameter
BankingSystem ->> BankingSystem: filter recorded transactions by account
BankingSystem ->> OS Windows: directory already exists?
alt is true:
    BankingSystem ->> OS Windows: create file csv
else is false:
    BankingSystem ->> OS Windows: create directory
    BankingSystem ->> OS Windows: create file
end 
BankingSystem --) UI: reload UI
UI ->> client: show client main menu
```

## Operação: alteração de restrição de horário ##
```mermaid
sequenceDiagram
actor client
UI ->> client: show client main menu
client ->> UI: select edit time restriction operation in menu option
UI --) client: request start time
client ->> UI: input start time info
UI --) client: request end time
client ->> UI: input end time info
UI ->> BankingSystem: pass info as parameters
BankingSystem ->> Account: update time restriction preferences
BankingSystem --) UI: reload UI
UI ->> client: show client main menu
```

## Operação: alteração de limite de credito ##
```mermaid
sequenceDiagram
actor client
UI ->> client: show client main menu
client ->> UI: select edit limit operation in menu option
UI --) client: request amount
client ->> UI: input amount info
UI ->> BankingSystem: pass info as parameters
BankingSystem ->> BankingSystem: is account an instance of CheckingAccount?
alt is true
    BankingSystem ->> BankingSystem: is client an instance of Personal?
    alt is true
        BankingSystem ->> Account: update limit
    else is false
        BankingSystem --) UI: cancel operation and reload UI
        UI ->> client: show main menu
    end
else is false
    BankingSystem --) UI: cancel operation and reload UI
    UI ->> client: show main menu
end
```