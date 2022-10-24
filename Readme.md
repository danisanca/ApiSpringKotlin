# Api Feita em Kotlin com Spring Boot 🚀
#### Api consiste em cadastro, atualização e remoção de usuario com base em dados persistidos em Jpa.


# Conceitos Aplicados 💻
#### Arquitetura: MVC
#### Jpa
#### Teste unitario para validar cadastro do usuario.

#### Documentação automatica Swagger
http://localhost:8080/swagger-ui/index.html#/

# Endpoints
### GET /accounts
Esse endpoint é responsável por retornar a listagem de todos os usuarios.
#### Parametros
Nenhum
#### Respostas
1º: OK! 200.
<p>    - Caso essa resposta aconteça você vai receber a listagem de todos os usuarios.</p>
    - Exemplo:
    
```
[
 {
  "id": 1,
  "name": "teste",
  "document": "111222333",
  "phone": "11998894567"
}
]
```
### POST /accounts
Esse endpoint é responsável criar um usuario
#### Parametros
<p>1° Nome: Nome para cadastro.</p>
<p>2° Documento: Documendo do usuario.</p>
<p>3° Telefone: Telefone do usuario.</p>

#### Validações
<p>1° Nome do usuario não pode estar em branco.</p>
<p>2° Nome deve conter mais que 5 characters.</p>
<p>3° Documento não pode estar em branco.</p>
<p>4° Documento deve conter mais que 11 characters.</p>
<p>5° Telefone não pode estar em branco.</p>
<p>6° Telefone deve conter mais que 11 characters.</p>

#### Respostas
1º: OK! 200.
<p>    - Caso essa resposta aconteça o usuario sera cadastro e sera retornado o usuario.</p>
    - Exemplo:
    
```

 {
  "id": 1,
  "name": "teste",
  "document": "111222333",
  "phone": "11998894567"
}

```
### GET /accounts/{id}
Esse endpoint é responsável por retornar um usuario especifico.
#### Parametros
1° id: Id do usuario especifico para busca.
#### Respostas
1º: OK! 200.
<p>    - Caso essa resposta aconteça você vai receber o cliente solicitado via id.</p>
    - Exemplo:
    
```

 {
  "id": 1,
  "name": "teste",
  "document": "111222333",
  "phone": "11998894567"
}

```

### PUT /accounts/{id}

Esse endpoint é responsável atualizar o usuario.

#### Parametros

<p> 1° Nome: Nome para atualização caso queira atualizar. </p>
<p> 2° Documento: Documendo para atualização caso queira atualizar. </p>
<p> 3° Telefone: Telefone para atualização caso queira atualizar. </p>

#### Validações

<p> 1° Nome do usuario não pode estar em branco. </p>
<p> 2° Nome deve conter mais que 5 characters. </p>
<p> 3° Documento não pode estar em branco. </p>
<p> 4° Documento deve conter mais que 11 characters. </p>
<p> 5° Telefone não pode estar em branco. </p>
<p> 6° Telefone deve conter mais que 11 characters. </p>

#### Respostas

1º: OK! 200.
   <p> - Caso essa resposta aconteça o usuario será atualizado e sera retornado o usuario atualizado.</p>
    - Exemplo:
    
```

{
  "id": 1,
  "name": "teste",
  "document": "111222333",
  "phone": "11998894567"
}

```
### DELETE /accounts/{id}
Esse endpoint é responsável deletar um usuario
#### Parametros
Nenhum
#### Respostas
1º: OK! 200.
    - Caso essa resposta aconteça o usuario será deletado.
    
```






