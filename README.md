# 🏇 Corrida de Cavalos
Projeto final desenvolvido durante a disciplina de Linguagem de Programação II. 

## Execução
1. Compile os arquivos
```bash
javac -encoding ISO-8859-1 *.java  
# ou javac *. java
```

2. Inicie o servidor
```bash
# Terminal 1
java Server
```
3. Inicie a conexão com o cliente 
```bash
# Terminal 2
java Client
```

## 📂 Sobre a estrutura deste projeto
(seção em documentação)
A seguir há alguns esclarescimentos quanto a organização do projeto.

### Server
Esta classe, além de possuir um método main que inicializa um objeto Server, possui o método `listener()` que:
* Aceita um cliente na porta 9090;
* Cria fluxos de entrada e saída de dados entre o cliente
* Cria um novo objeto Race (para iniciar a corrida)
* Fecha o servidor quando a corrida é encerrada

### Client
A classe cliente define o endereço e porta de conexão (localhost:9090), além disso:
* Cria fluxos de entrada e saída de dados que são repassados para o cliente
* Quando há uma mensagem "WAITFORANSWER", libera o teclado para a interação com o servidor

### Race
Responsável por organizar a corrida desempenhando as seguintes funcionalidades:
* Exibir o menu de opções
* Criar lista de cavalos (Horse)
* Criar lista de apostadores (Gambler)
* Regras para iniciar a corrida
* Receber e consultar apostas (BookMaker, Gambler e Horse)

### Horse
A classe que extende um `runnable` é uma abstração de um cavalo de corrida, onde é possível:
* Realizar sua identificação por meio de um id e um nome
* Percorrer uma pista de corrida apresentando variações na velocidade (valor aleatório entre o máximo e mínimo)
* Obter sua colocação na corrida após cruzar a linha de chegada (e validar se ele ganhou)
* Receber número e valores de apostas de apostadores

### Gambler 
Representa o jogador ou apostador de uma típica corrida de cavalos. Um apostador pode:
* Informar seus dados para realizar a aposta
* Escolher um dos 7 cavalos para a aposta
* Ao final da corrida, ser informado se ganhou ou não
* Receber o prêmio
* Quando a corrida não é iniciada e o servidor é fechado, os apostadores recebem seu dinheiro de volta

### BookMaker (Casa de Apostas)
Possui todas as regras de uma casa de apostas e gerencia os prêmios.

## 🧑‍💻 Desenvolvedores
Christopher Tavares, Mayra Daher, Victoria Grisi, Samantha Medeiros e Yan Feitosa.
