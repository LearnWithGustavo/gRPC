# gRPC em Java - Tutorial

Este repositório apresenta um guia prático de como utilizar **gRPC** em Java, desde a definição dos arquivos `.proto` até a execução de um servidor e cliente gRPC.

---

## 1. Pré-requisitos

- O sistema operacional é detectado automaticamente para executar o **Protobuf**.
- Tenha o **Maven** configurado corretamente no seu ambiente de desenvolvimento.

---

## 2. Definindo o arquivo `.proto`

No diretório `proto/`, criamos os arquivos `.proto` responsáveis por definir:

- As **mensagens** utilizadas pelo gRPC.
- Os **atributos** de cada mensagem.
- O **serviço** (service) com os métodos, requests e responses.

📌 Exemplo: `proto/veiculo/veiculo.proto`

---

## 3. Gerando classes Java a partir do `.proto`

Após criar o arquivo `.proto`, execute o comando abaixo no terminal:

```bash
mvn clean compile
```
Esse processo irá gerar automaticamente as classes Java correspondentes no diretório target/.

## 4. Configuração no IntelliJ IDEA
#### Em alguns casos, o IntelliJ não reconhece o diretório target como fonte válida. Para corrigir:

- Clique com o botão direito sobre a pasta target/.
- Selecione Mark Directory as → Non Excluded.
- Clique novamente com o botão direito e selecione Mark Directory as → Generated Source Root.
- Assim, o IntelliJ passa a reconhecer as classes geradas a partir do .proto.

## 5. Implementando o Service
No pacote service/, implementamos a lógica que será executada pelo serviço definido no arquivo .proto.
É aqui que desenvolvemos a regra de negócio correspondente aos métodos expostos via gRPC.

## 6. Criando o Servidor gRPC
#### No diretório server/GrpcServer, está a configuração do servidor gRPC:

- Inicialização do servidor.
- Definição da porta de escuta (por padrão, 6565).
- Registro dos serviços implementados.

## 7. Executando Servidor e Cliente
```Servidor```
#### Inicie a classe Starter.class → o servidor ficará escutando na porta 6565.

```Cliente```
- Utilize a classe StarterClient.class para enviar requisições e receber respostas do servidor.
- No StarterClient, estão definidas todas as configurações necessárias:
- Criação do channel.
- Definição do tipo de stub (existem 4 tipos disponíveis).
- Instanciação do request.
- Execução do método do serviço definido no .proto.
- Tratamento da response recebida.
- Finalização do channel.

## 8. Boas práticas
- Organize os campos de forma lógica e clara.
- Divida mensagens grandes em mensagens menores, para facilitar a manutenção e legibilidade.

## 9. Curiosidades
- Cada atributo da mensagem recebe um número (tag) definido no .proto.
- Durante a serialização e desserialização, apenas números de campo e valores são transmitidos (ex.: 1:Sam).
- O Proto não permite valores nulos.
- O arquivo .proto não contém lógica, apenas definições de atributos, mensagens e serviços.
- As tags não são valores; elas identificam os campos.
- Tags entre 1 e 15 utilizam menos espaço (≈1 byte).