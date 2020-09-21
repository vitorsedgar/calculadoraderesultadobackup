**A aplicação:**
- Java 11 com SpringBoot
- Maven

**Como rodar:**
- Sugerida a utilização da IDE IntelliJ, pode ser a versão Community mas o email acadêmico da PUC possui licença para Ultimate;
- Importar como um projeto Maven;
- Baixar dependências;
- Garantir que nas configurações do projeto esteja configurado Java 11;
- Executar o método Main classe CalculadoraBackApplication;
- Acessar em http://localhost:8080

**Banco de Dados:**
- No arquivo *application.yaml* há configuração para 2 bancos: Oracle rodando local e H2; Para alterar o banco em uso: alterar o profile na linha 3 para *test* ou *local*
- Para rodar o local: baixar o docker, e executar *docker run -d -p 49160:22 -p 49161:1521 deepdiver/docker-oracle-xe-11g*

**Observações:**
- Swagger já está configurado, qualquer API criada dentro da pasta com.br.ages.calculadoraback.api estará visível na UI;
- CORS está configurado para acesso livre;
- Dependência SLF4J já colocada para utilização de logs, exemplo na classe DemoApi;
- Já adicionadas dependências para uso de: MongoDB, Lombok, JUnit, JWT