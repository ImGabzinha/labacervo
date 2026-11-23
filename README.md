# LabAcervo

**Projeto Integrador de Engenharia de Software • Padrões de Desenvolvimento Web • 4º termo**  
**Autora** Gabriella de Albuquerque Reis | ID acadêmico 265949

Dois microscópios do mesmo modelo podem estar em situações diferentes: um está na bancada e o outro saiu para uma aula. Por isso, no LabAcervo cada aparelho tem seu próprio número de patrimônio. Quem cuida do laboratório consegue consultar quais aparelhos estão disponíveis, quem retirou cada um e qual é o prazo de devolução.

## Executar com Docker Desktop

É necessário Docker com o comando Compose e acesso à internet na primeira construção. Abra o terminal na pasta que contém o arquivo `compose.yaml` e execute:

```sh
docker compose up --build -d
docker compose logs -f api
```

Aguarde a mensagem `Started LabAcervoApplication`. Pressione Ctrl+C para sair dos logs. Abra http://localhost:8080/api/equipamentos e entre com o usuário de consulta da tabela abaixo. Na primeira execução, a resposta esperada é uma lista vazia. A API e o MySQL sobem juntos. Para parar, use `docker compose down`. O volume mantém os registros entre execuções.

| Usuário de demonstração | Senha de teste | Operações |
|---|---|---|
| consulta | consulta_demo | Consultar cadastros, disponibilidade e histórico |
| tecnico | tecnico_demo | Consultar, cadastrar, atualizar, excluir e registrar devolução |

## Executar com JDK e MySQL locais

Use JDK 21 e MySQL 8.4 em execução. Com uma conta administrativa no banco, execute os comandos abaixo em uma instalação de desenvolvimento:

```sql
CREATE DATABASE IF NOT EXISTS labacervo CHARACTER SET utf8mb4;
CREATE USER IF NOT EXISTS 'lab'@'localhost' IDENTIFIED BY 'lab_demo';
GRANT ALL ON labacervo.* TO 'lab'@'localhost';
```

No Windows PowerShell, execute `.\mvnw.cmd spring-boot:run`. Em Linux ou macOS, use `./mvnw spring-boot:run`. A aplicação cria as tabelas na inicialização. Se o banco estiver em outro endereço ou usar outra conta, defina `DB_URL`, `DB_USER` e `DB_PASSWORD` antes de iniciar a aplicação.

## Testar a API no Postman

1. Importe `postman/LabAcervo.postman_collection.json`. Se o Postman estiver no navegador, selecione o agente local para alcançar a API.
2. Selecione a coleção e use **Run collection**. Mantenha as pastas 01 a 08 na ordem. A primeira criação gera um identificador de rodada e um prazo. Os testes armazenam os IDs retornados para uso nas próximas requisições.
3. Abra os exemplos salvos em cada requisição para inspecionar o corpo e o status HTTP. Para registrar novos exemplos manualmente, envie a requisição no Postman e use **Save Response**. Antes de exportar, compartilhe os valores das variáveis de demonstração e use o formato Collection v2.1.
4. A última pasta exclui somente os registros criados naquela rodada. Para observar os dados no banco, pause antes dessa pasta ou envie os pedidos individualmente na ordem indicada.

## Tecnologias e organização

Java 21, Spring Boot 4.1.1, Spring MVC, Spring Data JPA, Hibernate, MySQL, Bean Validation, Spring Security com HTTP Basic, Lombok, Maven e Postman.

| Pacote | Responsabilidade |
|---|---|
| entidades | Mapeamento das cinco tabelas e dos relacionamentos |
| dto | Campos e validações aceitos nas requisições de criação e atualização |
| recursos | Rotas HTTP, leitura do corpo e códigos de sucesso |
| servicos | Regras, transações, integração entre os registros |
| repositorios | Persistência, consultas e bloqueios de escrita |
| erros e config | Respostas de erro e permissões dos perfis |

## Adaptação do projeto da disciplina

O projeto de e-commerce usado em aula já trazia a conexão com o MySQL e a relação entre produto e categoria. Essa relação também faz sentido no laboratório: microscópios diferentes pertencem à mesma categoria. A principal mudança está na retirada. Para emprestar um microscópio, é preciso identificar o aparelho pelo patrimônio e vincular a retirada a uma pessoa. As entidades `Equipamento`, `Solicitante` e `Emprestimo` guardam essas informações. A aplicação aproveita o Maven Wrapper, a configuração Java 21 e a estrutura JPA e MVC da base. [Projeto-base](https://github.com/sergiodelfino/ecommerce-backend-4a/tree/main/ecommerce)

## Materiais

- [Modelo de dados](docs/modelo-dados.png)
- [Rotas, regras e exemplos de uso](docs/api.md)
- [Roteiro para demonstração e perguntas](docs/demonstracao.md)
- [Resultados obtidos com MySQL e Newman](docs/evidencias.md)
- [Coleção Postman](postman/LabAcervo.postman_collection.json)

## Entrega

O repositório deve conter este README, os arquivos da raiz, `src`, `docs`, `postman` e a pasta oculta `.mvn`. Verifique o acesso do professor e envie o link do repositório na atividade **Projeto Integrador** do Ambiente Conhecer.

### Publicar os arquivos pelo Git

Crie um repositório vazio chamado `labacervo` na sua conta do GitHub. Na pasta extraída, abra o terminal e execute os comandos abaixo, substituindo `SEU-USUARIO` pelo nome do seu perfil. O comando `git add .` inclui a pasta oculta do Maven Wrapper.

```sh
git init
git add .
git commit -m "Implementa o backend de empréstimos do LabAcervo"
git branch -M main
git remote add origin https://github.com/SEU-USUARIO/labacervo.git
git push -u origin main
```

No GitHub, abra o README, o diagrama e o JSON do Postman para conferir a publicação. Se o repositório for privado, conceda acesso ao professor conforme as orientações da disciplina.
