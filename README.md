
# bike-integration

API de integração para gerenciamento do APP principal (Bicity).

## Descrição

Este projeto é uma API desenvolvida em Java utilizando o Maven para gerenciamento de dependências. 
A API é responsável por integrar e gerenciar as funcionalidades do aplicativo principal (Bicity).

## Tecnologias Utilizadas

- Java
- Maven
- Spring Boot
- JPA/Hibernate
- Banco de Dados Relacional (MySQL)

## Requisitos

- Java 11 ou superior
- Maven 3.6.0 ou superior
- Banco de Dados configurado

## Configuração do Ambiente

1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/bike-integration.git
   ```
2. Navegue até o diretório do projeto:
   ```sh
   cd bike-integration
   ```
3. Configure o arquivo `application.properties` com as informações do seu banco de dados:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/seu_banco_de_dados
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   ```

## Compilação e Execução

Para compilar e executar o projeto, utilize os seguintes comandos:

1. Compile o projeto:
   ```sh
   mvn clean install
   ```
2. Execute a aplicação:
   ```sh
   mvn spring-boot:run
   ```

## Endpoints

A API utiliza Swagger para mapeamento dos endpoints


## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo [LICENSE](LICENSE) para mais detalhes.
