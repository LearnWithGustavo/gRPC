# gRPC-GraphQL
Tutorial de gRPC + GraphQL

Seguindo a mesma logica do gRPC, iremos desenvolver um arquivo proto para configurarmos um serviço


Apos o procedimento de desenvolvimento do arquivo proto. 
Executar o comando mvn clean compile

Existe uma pratica onde armazenamos todos os nossos arquivos proto em um repositorio, dessa forma os dsenvolvedores
vão alterando e dando manutenção nesses arquivos durante o desenvolvimento do projeto e produto

Uma ideia interessante é não tratar como classes os arquivos gerados apos o mvn clean compile, mas sim tratar elas como uma biblioteca.
Uma pratica legal é armazenar elas em ci/cd 