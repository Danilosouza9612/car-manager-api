
## Estórias de Usuário

- Project Setup: Spring Boot
- Database Setup
- Spring Security Setup
- Users CRUD
- Cars CRUD
- "/api/me" endpoint
- Project setup: Angular
- Create CRUD for Users
- Create CRUD for Cars
- Frontend interface for "/api/me"
- Repeat password functionality
- Fix User/Car creation bug
- Fix update bug
- Unit tests for Cars
- Unit tests for Users
- Adjust validation messages
- Swagger UI
- JWT Key configuration in application.properties
- Architecture Improvements
- Create endpoint for car and users image upload.
- Adjust JWT Security Architecture
- Fix CarService update bug
- Fix UserService update bug
- Backend deployment
- SonarQube: Remove passwords from application.properties
- Hotfix: Fix license plate validation in user creation endpoint
- Jenkins Pipeline Setup
- SonarQube: Integrate with tests coverage
- Configure Nexus Repository
- Change token subject from user login to id
- Implement documentation(JavaDoc)
- Improve architecture: Remove Spring annotations from car-manager-core module

## Solução

- Foi gerado as tabelas do banco de dados aplicando quarta forma normal para tabela Users, removendo o atributo multivalorado "cars".

![Arquitetura da aplicação](https://i.postimg.cc/Nfn0STyz/Arquitetura-drawio.png "Logo Title Text 1")

- A aplicação é dividida em quatro módulos: API onde controla a autenticação do usuário e roteia a URL para o endpoint especificado, Core onde está a regra de negócio da aplicação, Storage que se responsabiliza de armazenar o arquivo no Amazon S3 e o Repository que faz a integração com o banco de dados através do JPA/Hibernate.

- O sistema de autenticação foi feito a partir de uma adaptação do Spring Security, permitindo um reaproveitamento de grande parte das funcionalidades da ferramenta.

- Foi aplicado alguns conceitos de Clean Architecture no projeto: Há uma inversão de dependência entre o Core e os módulos Storage e Repository. Através do padrão bridge, Storage implementa a interface FileStorage e o Repository implementa os gateways que se conectação com o Hibernate. Ainda há a injeção de dependências realizada pela API(Spring Boot), que injeta as instâncias do Repository e Storage dentro do Core. Um ganho da abordagem de conceitos da Clean Architecture para esse projeto é que evitou dependência cíclica entre os módulos, evitando erros oriundo disso.

- A API está rodando no serviço ECS da AWS(http://car-manager-alb-148629350.eu-west-1.elb.amazonaws.com) com Load Balacing configurado para duas instâncias. Isso aumenta a resiliência da aplicação, pois caso uma instância caia, existe outra ainda saudável e uma nova instância será provisionada. Para banco de dados é usado o RDS que provisiona o PostgreSQL. Tem-se ainda o Amazon S3 para armazenamento das fotografias do Usuário e do Carro. O S3 tem diversas ferramentas para garantir a resiliência e disponibilidade dos dados.

![](https://i.postimg.cc/gkhQKr73/sonar01.png)

- A aplicação foi integrada com o SonarQube. Em uma primeira análise o Sonar apontou vulnerabilidades de segurança devido a senha do banco de dados estar no application.properties. A senha foi removida, sendo agora fornecida por variáveis de ambiente. Outro dado sensível como a chave criptográfica do JWT também foi removida do application.properties. Em uma nova análise, o Sonar não identificou nenhuma outra vulnerabilidade. Além disso, foi identificada apenas 5.7% de duplicação de código e algumas sugestões para aumentar a resiliência e a manutenabilidade da aplicação. Aplicação de padrões de projetos e SOLID contribuiram muito para esses índices.

## Execução

Na primeira execução, esses seguintes comandos são necessários:

````
docker compose db up
docker compose exec db psql -U postgres
CREATE DATABASE db_car_manager;
````

Estou enfrentando problemas referente a execução local da aplicação. 
Fora do ambiente da IDE, pelo comando do Maven, ele não encontra a classe Main. Já tentei várias configurações e nada

Para testar:

````
./mvnw test
````

## Links

http://car-manager-alb-1717640298.eu-west-1.elb.amazonaws.com

