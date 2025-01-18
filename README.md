
# Discord Roleplay Bot

Este é um bot do Discord desenvolvido em Java utilizando a biblioteca **JDA (Java Discord API)**. O bot oferece funcionalidades como comandos de roleplay, um sistema de moedas com CRUD utilizando um banco de dados MySQL e comandos de música.

## Funcionalidades

- **Comando de Roleplay**: O bot permite que os usuários enviem comandos de roleplay, como dar abraços a outros membros do servidor.
- **Sistema de Moeda**: Um sistema de moeda virtual baseado em banco de dados MySQL, onde os usuários podem ganhar e gastar moedas.
- **Comandos de Música**: O bot pode tocar música em canais de voz, permitindo que os usuários interajam com comandos para controlar a reprodução.

## Dependências

O projeto depende das seguintes bibliotecas:

- [JDA](https://github.com/DV8FromTheWorld/JDA) - Biblioteca para criar bots no Discord.
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/) - Conector MySQL para Java.
- Webhooks
- LavaPlayer
Você pode incluir as dependências no arquivo `pom.xml` do seu projeto (caso esteja utilizando Maven):

```xml
<dependencies>
    <dependency>
        <groupId>net.dv8tion</groupId>
        <artifactId>JDA</artifactId>
        <version>5.0.0-alpha.11</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.26</version>
    </dependency>
</dependencies>

Comandos Disponíveis

Comando de Abraço

Uso: /abraço @usuário

O bot enviará uma mensagem de roleplay de abraço para o usuário mencionado.

Comando de Moeda

Uso: /trabalhar

O usuário pode trabalhar uma vez a cada 10 minutos para ganhar suas Super Coins

Comando de Música

Uso: /play <URL ou nome>
Toca a música no canal de voz atual a partir da URL fornecida, pegando músicas do soundcloud

Uso: /stop
Para a música em reprodução.

Uso: /pause
Pausa a música em reprodução.

Uso: /resume
Retoma a música pausada.




