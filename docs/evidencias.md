# Evidências de execução

O Maven gerou o JAR executável com sucesso em Java 21. A aplicação foi iniciada com esse JAR para a rodada abaixo.

Coleção executada em 23/11/2026 com o Newman, executor de coleções do Postman, contra a API Spring Boot e um banco MySQL Community Server 8.4.9. Todas as 40 requisições e as 62 verificações passaram. O script de preparação da rodada também passou. Cada pedido na coleção contém um exemplo salvo com o status e o corpo recebidos. As respostas 204 têm corpo vazio.

Em teste adicional, duas retiradas simultâneas para o mesmo patrimônio retornaram 201 e 409. A devolução retornou 200 e a nova retirada retornou 201.

| Nº | Requisição | Método | HTTP recebido | Verificações |
|---:|---|---|---:|---:|
| 1 | Criar categoria | POST | 201 | 1 |
| 2 | Listar registros | GET | 200 | 2 |
| 3 | Buscar pelo ID | GET | 200 | 2 |
| 4 | Atualizar cadastro | PUT | 200 | 2 |
| 5 | Criar laboratorio | POST | 201 | 1 |
| 6 | Listar registros | GET | 200 | 2 |
| 7 | Buscar pelo ID | GET | 200 | 2 |
| 8 | Atualizar cadastro | PUT | 200 | 2 |
| 9 | Criar solicitante | POST | 201 | 1 |
| 10 | Listar registros | GET | 200 | 2 |
| 11 | Buscar pelo ID | GET | 200 | 2 |
| 12 | Atualizar cadastro | PUT | 200 | 2 |
| 13 | Criar equipamento | POST | 201 | 1 |
| 14 | Listar registros | GET | 200 | 2 |
| 15 | Buscar pelo ID | GET | 200 | 2 |
| 16 | Atualizar cadastro | PUT | 200 | 2 |
| 17 | Criar emprestimo | POST | 201 | 1 |
| 18 | Listar registros | GET | 200 | 2 |
| 19 | Buscar pelo ID | GET | 200 | 2 |
| 20 | Atualizar observação da retirada | PUT | 200 | 2 |
| 21 | Disponíveis durante o empréstimo | GET | 200 | 2 |
| 22 | Histórico do solicitante | GET | 200 | 2 |
| 23 | Segunda retirada do mesmo patrimônio | POST | 409 | 2 |
| 24 | Impedir exclusão de retirada aberta | DELETE | 409 | 1 |
| 25 | Prazo anterior à retirada | PUT | 409 | 1 |
| 26 | Preservar categoria vinculada | DELETE | 409 | 1 |
| 27 | Campo obrigatório vazio | POST | 400 | 2 |
| 28 | ID inexistente | GET | 404 | 1 |
| 29 | Consulta sem credenciais | GET | 401 | 1 |
| 30 | Consulta autenticada | GET | 200 | 1 |
| 31 | Leitor tenta alterar cadastro | PUT | 403 | 1 |
| 32 | Registrar devolução | POST | 200 | 2 |
| 33 | Repetir devolução | POST | 409 | 2 |
| 34 | Disponíveis após devolver | GET | 200 | 2 |
| 35 | Excluir emprestimo | DELETE | 204 | 1 |
| 36 | Excluir equipamento | DELETE | 204 | 1 |
| 37 | Excluir solicitante | DELETE | 204 | 1 |
| 38 | Excluir laboratorio | DELETE | 204 | 1 |
| 39 | Excluir categoria | DELETE | 204 | 1 |
| 40 | Confirmar exclusão | GET | 404 | 1 |
