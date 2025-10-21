# SISTEMA DE GERENCIAMENTO DE TAREFAS - BACKEND

## Para execução da aplicação, deverá seguir os seguintes passos:

## -Clonar em um diretório a aplicação do github - https://github.com/alvarofederal/sgt-backend

## -Criação de um banco de dados em postgres "sgtdb":
#### Na primeira execução, não alterar a flag "create":
##### spring.jpa.hibernate.ddl-auto=create

#### Na segunda execução em diante, pode alterar para "update", para que não perca os dados informados:
##### spring.jpa.hibernate.ddl-auto=update

## -Por se tratar de uma aplicação Springboot, executar a subida do servidor, tanto no Intelij quanto no Eclipse STS

## -Quando executado a primeira vez, após a criação da estrutura de tabelas do banco de dados, a aplicação, atravez da classe DBServi.class,
## executa uma carga no banco de dados, contendo usuários, e algumas tarefas, que poderão se acessadas no backend. Abaixo, se encontra os usuários da aplicação:
                      
##    NOME            USUARIO DA APLICAÇÃO  SENHA                                               
#####   Valdir Amaral   | valdir@mail.com     |123                         
                                      
#####   Albert Einstein | einstein@mail.com   |123     
                                      
#####   Marie Curie     | curie@mail.com      |123     
                                               
#####   Charles Darwin  | darwin@mail.com     |123     
                                      
#####   Stephen Hawking | hawking@mail.com    |123                          
                                      
#####   Max Planck      | planck@mail.com     |123     


 
