# Teste Pismo

O projeto disponibiliza duas APIs:

- `pismo-transactions`;
- `pismo-accounts`.

# Executando o projeto

É possível executar o projeto de duas formas:

## Da maneira fácil

Usando [Docker](https://www.docker.com/) com [Docker Compose](https://docs.docker.com/compose/): `docker-compose up --build`.

Isso fará com que o projeto seja compilado e executado, com as devidas dependências, tendo ao final da execução os seguintes containers:

- `mysql-accounts`: banco MySQL para a API de accounts;
- `mysql-transactions`: banco MySQL para a API de transactions;
- `pismo-accounts`: API de accounts;
- `pismo-transactions`: API de transactions.

**Obs.:** Dependendo da configuração da máquina, é possível que as APIs subam antes dos bancos MySQL. 
Dessa maneira, é recomendável que antes da primeira execução os bancos sejam inicializados com
`docker-compose up mysql-accounts mysql-transactions`.

## Da maneira não tão fácil

Será necessário ter instalado na máquina:

- Java JDK 10;
- Maven 3.2.3+.

Para compilar: 

`mvn clean package` em cada um dos projetos: `pismo-transactions` e `pismo-accounts`.

Para executar:

- `pismo-transactions`: `java -jar target/pismo-accounts-api-1.0.jar`
- `pismo-accounts`: `java -jar target/pismo-transactions-api-1.0.jar` 

Este tipo de execução usa bancos H2 em memória. Dessa maneira, todas as alterações são perdidas ao término da execução dos serviços.

