# Referência da API

Endereço local: `http://localhost:8080/api`. As rotas exigem HTTP Basic. Use JSON nas requisições com corpo. O perfil CONSULTA pode realizar leituras. O perfil TECNICO também pode escrever.

## Cadastros

Em cada linha, o caminho da coleção aceita GET para listar e POST para criar. Acrescente `/{id}` para consultar com GET, substituir os campos editáveis com PUT e excluir com DELETE. Os campos de entrada são obrigatórios. A observação aceita texto vazio.

| Caminho após /api | Corpo de exemplo para POST e PUT |
|---|---|
| /categorias | `{"nome":"Microscopia","descricao":"Observação de lâminas"}` |
| /laboratorios | `{"nome":"Laboratório de Biologia","sala":"B12"}` |
| /solicitantes | `{"nome":"Ana Exemplo","matricula":"AL-100"}` |
| /equipamentos | `{"nome":"Microscópio binocular","patrimonio":"MIC-001","categoriaId":1,"laboratorioId":1}` |
| /emprestimos | `{"equipamentoId":1,"solicitanteId":1,"prazo":"2099-04-20","observacao":"Aula de citologia"}` |

Substitua os IDs pelos retornados pela sua API e escolha um prazo igual ou posterior à data da retirada. A criação retorna 201 com o registro e o cabeçalho Location. A leitura e a atualização retornam 200. A exclusão retorna 204 com corpo vazio. As respostas de equipamento trazem os objetos categoria e laboratório. As de empréstimo trazem o equipamento, o solicitante, as datas e a observação.

## Operações do laboratório

| Requisição | Efeito |
|---|---|
| GET /api/equipamentos/disponiveis?laboratorioId=1 | Seleciona os equipamentos do laboratório sem empréstimo aberto e ordena por patrimônio. |
| GET /api/emprestimos/historico?solicitanteId=1 | Lista as retiradas do solicitante por ID decrescente, incluindo as devolvidas. |
| POST /api/emprestimos/{id}/devolucao | Sem corpo. Preenche a data da devolução usando a data do servidor e libera o equipamento para nova retirada. Retorna 200 com o registro. |

Um laboratório ou solicitante existente sem resultados devolve 200 com `[]`. Um identificador de laboratório ou solicitante inexistente devolve 404.

## Integridade da retirada

- Criar um empréstimo registra a data de retirada no servidor. A devolução começa nula. O prazo pode ser o próprio dia da retirada.
- Um patrimônio comporta um empréstimo aberto por vez. Duas retiradas simultâneas disputam o bloqueio da mesma linha de equipamento. Sob o isolamento READ COMMITTED, a segunda transação verifica o estado após a confirmação da primeira.
- Em uma retirada aberta, PUT permite editar o prazo e a observação. Trocar o solicitante ou o equipamento alteraria a autoria da movimentação e gera conflito.
- Somente uma retirada aberta aceita devolução. Uma segunda tentativa retorna 409 e preserva a data registrada.
- A exclusão de um empréstimo exige devolução prévia. O perfil técnico pode excluir um empréstimo encerrado para corrigir o acervo de teste. O histórico reflete os registros mantidos no banco.
- Chaves estrangeiras protegem cadastros vinculados. Nome de categoria, nome de laboratório, matrícula e patrimônio são únicos nas respectivas tabelas.

## Respostas de erro

| HTTP | Situação demonstrada |
|---|---|
| 400 | Nome vazio, campo obrigatório ausente ou corpo JSON inválido |
| 401 | Acesso sem autenticação ou com credenciais incorretas |
| 403 | Usuário de consulta autenticado tentando escrever |
| 404 | ID de cadastro inexistente |
| 409 | Retirada duplicada, prazo anterior à retirada, devolução repetida, duplicidade de chave única ou exclusão de cadastro vinculado |

As falhas de validação identificam os campos em `campos`. Os conflitos de negócio informam o motivo em `mensagem`. Spring Security verifica autenticação e autorização antes de chamar o recurso REST.
