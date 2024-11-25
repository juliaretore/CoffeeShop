# Fire Drink Delivery
## Descrição ##
O Fire Drink Delivery é um aplicativo de delivery de bebidas desenvolvido para dispositivos móveis Android. O objetivo do projeto é proporcionar uma plataforma simples e eficiente para os usuários realizarem pedidos de bebidas, com funcionalidades como criação de conta, login, visualização do menu, adição de itens ao carrinho, finalização de compras, visualização de compras anteriores e gerenciamento do perfil do usuário.

O aplicativo utiliza arquitetura MVC (Model, View, Controller) garantindo uma separação clara entre a lógica de negócios, a interface de usuário e a manipulação de dados.

Funcionalidades

- Criar Conta: O usuário pode criar uma conta fornecendo informações básicas (nome, e-mail, senha, etc.).
- Login: Permite que o usuário faça login com username e senha cadastrados.
- Menu: O usuário pode visualizar o menu de bebidas disponíveis para compra.
- Carrinho de Compras: O usuário pode adicionar itens ao carrinho e proceder com a compra.
- Finalização de Compra: O usuário pode concluir sua compra.
- Compras Realizadas: O usuário pode visualizar o histórico de compras feitas.
- Perfil do Usuário: O usuário pode acessar e editar seu perfil.


## Requisitos Técnicos ##
Aplicativo feito no Android Studio, com a linguagem Java. Utiliza-se a arquitetura MVC (Model, View, Controller).
Banco de Dados MongoDB e arquitetura API REST, hospedados no Render (Plataforma de hospedagem para os serviços de APIs REST).


## Como Rodar o Projeto ##
Clone o repositório do projeto em sua máquina local:

```
git clone https://github.com/juliaretore/FireDrinkDelivery.git
```
Certifique-se de ter o Java instalado em sua máquina (versão 8 ou superior). Abra o projeto no Android Studio. Conecte um dispositivo Android ou utilize um emulador. Compile e execute o aplicativo no Android Studio.

O banco de dados MongoDB está hospedado na plataforma Render.
A conexão entre o back-end e o MongoDB é configurada automaticamente nas variáveis de ambiente do back-end. Caso queira rodar localmente, basta ter mongo db instalado, com executar o comando:

```
 cd server
. .env
 node .\server\server.js
```

e utilizar a seguinte linha na classe ApiClient.java:
```
private static final String BASE_URL = "http://10.0.0.2:8000";

```
