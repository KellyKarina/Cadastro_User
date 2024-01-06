# Cadastro_User

Projeto desenvolvido em Java utilizando o framework Quarkus, para gerenciar contas bancárias, saques, transferências e depósitos. Veja como executar o projeto e as rotas disponíveis.
#
Se você deseja aprender mais sobre o Quarkus, visite o site oficial: `https://quarkus.io/ .`

Você pode executar sua aplicação em modo de desenvolvimento, que permite codificação ao vivo, utilizando:

`./mvnw compile quarkus:dev`
#
Depois de iniciado o servidor, você pode acessar os seguintes Endpoint:

 <h3>Endpoint principal clientes: (api/v1/users)</h3>

Criar clientes 
* (localhost:8080/api/v1/api/v1/users): POST

Clientes
* (localhost:8080/api/v1/users/{id}): GET

Editar informações cliente 
* (localhost:8080/api/v1/users/{id}): PUT

Deletar cliente 
* (localhost:8080/api/v1/users/{id}): DELETE

#
<h3>Endpoint principal contas: (api/v1/accountusers)</h3>

Criar conta
* (localhost:8080/api/v1/accountusers): POST

Contas
* (localhost:8080/api/v1/accountusers/{id}): GET

Editar tipo da conta 
* (localhost:8080/api/v1/accountusers/{id}): PUT

Depositar
* (localhost:8080/api/v1/accountusers/deposito): POST

Sacar
* (localhost:8080/api/v1/accountusers/saque): POST

Deletar
* (localhost:8080/api/v1/accountusers/{id}): DELETE


