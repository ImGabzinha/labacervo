# Um empréstimo no LabAcervo

A demonstração acompanha Ana, que precisa retirar um microscópio para uma aula. No Postman, execute as pastas em ordem. Vale deixar a resposta da retirada aberta enquanto consulta o histórico, porque o mesmo número de patrimônio aparece nas duas telas.

**1. Identificar o aparelho.** Nas pastas 01 a 04, cadastre a categoria, o laboratório, Ana e o microscópio. Na resposta do equipamento, localize o patrimônio. Mesmo que haja dois aparelhos com o mesmo nome, esse número permite saber qual deles saiu da sala B14.

**2. Registrar a retirada.** Na pasta 05, faça o empréstimo. Compare os campos `retirada`, `prazo` e `devolucao`. O sistema registra o dia da retirada, quem faz o pedido informa o prazo e a devolução fica vazia até o aparelho voltar. Use o PUT para corrigir a observação da aula.

**3. Conferir onde está o microscópio.** Na pasta 06, consulte os equipamentos disponíveis no laboratório e depois o histórico de Ana. O microscópio continua cadastrado e aparece no histórico dela. Se outra pessoa tentar retirar esse mesmo aparelho agora, a API responde com 409 e explica que já existe um empréstimo em aberto.

**4. Comparar os erros de acesso.** Na pasta 07, abra primeiro o GET sem credenciais: ele retorna 401. Depois, tente editar o equipamento com o usuário `consulta`: a senha é aceita, mas esse perfil só pode fazer consultas, então o retorno é 403. Nessa mesma pasta há exemplos de nome em branco (400) e de busca por um ID inexistente (404).

**5. Devolver e conferir.** Na pasta 08, registre a devolução e confira a data na resposta. Repita a operação para ver como o sistema trata uma segunda devolução. Na consulta seguinte, o patrimônio volta a aparecer entre os disponíveis. As últimas requisições removem os cadastros usados no exemplo.

## Para explicar olhando o código

**E se duas pessoas tentarem retirar o microscópio ao mesmo tempo?** Abra o método `criar` em `EmprestimoServico`. Ele bloqueia o registro daquele equipamento enquanto verifica se existe uma retirada em aberto. A segunda solicitação espera a primeira terminar. Se a primeira retirada foi confirmada, a segunda recebe 409.

**Como o sistema sabe que o aparelho está disponível?** Veja a consulta `disponiveis` em `EquipamentoRepositorio`. Ela procura os equipamentos do laboratório e deixa de fora os que têm um empréstimo com devolução vazia. Assim, registrar a devolução já basta para o microscópio aparecer na lista.

**Qual é a diferença entre informar dados inválidos e tentar uma retirada impossível?** Se faltar o prazo, a própria entrada está incompleta e a validação retorna 400. Se o prazo estiver preenchido corretamente, mas outra pessoa já estiver com o aparelho, quem recusa o pedido é o serviço, com 409.

**Por onde passa uma retirada?** A rota está em `EmprestimoRecurso`. O Spring confere os campos de entrada. O recurso chama `EmprestimoServico`, que verifica as regras e usa o repositório para gravar a retirada no MySQL. Na resposta 201, o cabeçalho `Location` indica onde consultar o empréstimo criado.

**Como conferir se o cadastro ficou salvo?** Antes da limpeza da pasta 08, anote o ID de um equipamento. Se estiver usando Docker Compose, execute `docker compose restart api`, aguarde a aplicação iniciar e consulte o mesmo ID.
