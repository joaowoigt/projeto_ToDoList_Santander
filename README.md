# Projeto Final Santander Bootcamp || Mobile Developer

## Criando um app de lembretes e tarefas em Kotlin

###### Proposta: Crie um App no estilo "To Do List" do zero e conduza todo o processo de  desenvolvimento usando Kotlin, uma das linguagens de programação de  maior ascensão nos últimos anos. Além disso, você é desafiado a evoluir o App e entregar uma solução ainda mais robusta.

___



:hand: Olá, seja bem vindo ao repositório com a minha resolução para o este projeto final. Meu nome é João Woigt e no final deste texto você pode ler um pouco mais sobre mim.

___



Além do código base desenvolvido junto ao mentor do projeto, as minhas evoluções foram:

- Acredito que a adição mais importante a ser comentada é a implementação do **RoomDatabase**, com a intenção de garantir a persistência de dados no aplicativo. Isto traz maior utilidade para o aplicativo, uma vez que os dados das tarefas eram todos perdidos assim que  a aplicação original era destruída.

- Nesta implementação foi respeitada as diretrizes do MVVM. A seção de data é composta por:  **DataClass para representar o modelo das tarefas; Data Access Object(DAO) para acessar corretamente a tabela criada pelo database; RoomDatabase para inicializar o banco de dados local; Repository para fazer a conexão recomendada entre o banco de dados, o ViewModel e a UI; Application para instanciar tanto o repositório quanto o RoomDatabase**.
  - Todas essas aplicações são adaptadas para trabalhar com os dados da DataClass das Tarefas do aplicativo, que consistem em: Título, Descrição, Data, Hora e Completa (que indica se a tarefa já foi encerrada ou não).
  - O DAO implementa as seguintes manipulações do banco de dados:
    - getTasks; insert; update; delete; getItem; updateCompleted;

- Ainda na manipulação de dados, como já foi referenciado o Aplicativo trabalha com um **ViewModel** único que provê as informações para todas as páginas, tudo isso a fim de não bloquear a Thread principal. Dessa forma, junto com o repositório, o **TaskViewModel** garante o funcionamento correto e rápido da aplicação, trabalhando com o sistema de **Corroutines**.

- Todo o código respeita as divisões de boas práticas do **MVVM**. Criando a separação correta dos componentes e seguindo suas diretrizes de implementação.

- Para além do projeto original, foram adicionadas as seguintes funcionalidades:
  - **Tela exclusiva** para mostrar maiores **detalhes** ao clicar na tarefa. Com Layout simples mas moderno, as informações são passadas mais claramente para o usuário, entregando uma experiência mais agradável e satisfatória;
  - Desta tela, também, o usuário tem acesso a duas funcionalidades já existentes. **Excluir** atividade, que acrescentei um **diálogo de alerta** para evitar esbarradas acidentais no botão. E **editar tarefa**, que redireciona o usuário para a página de edição, depois de editada, retorna para a tela de detalhes;
  - Ainda, o usuário pode optar por **agendar a tarefa criada no App na Agenda nativa do Android**. Dando a facilidade de já importar *Título* e *Descrição* da tarefa para a tela de criação de compromisso da Agenda. Tendo acesso a um sistema de notificações já integrado com seu email de preferência. 
  - Na tela principal, onde são mostrada todas as tarefas registradas, o usuário tem a opção de **clicar no botão de :heavy_check_mark: para riscar suas tarefas**. Dando a sensação de missão cumprida no final do dia.

- Por último, mas não menos importante, foi projetado um Layout seguindo as **Regras de UX e do Material Design** para criar uma identidade própria para o aplicativo, carinhosamente denomidado de TuDu List!, em homenagem ao abrasileiramento da língua inglesa.

___

### Sobre o Autor

Olá, meu nome é João Lucas Woigt Azevedo, mas as pessoas geralmente costumam me chamar de Woigt apenas. Sou uma pessoa apaixonada tanto por educação quanto por tecnologias.  Atualmente curso Ciências Sociais na Unicamp, mas durante a pandemia  decidi aprender a programar em paralelo e acabei me encontrando com isso também! Na área de tecnologia, comecei meus estudos de programação em 2020 com  curso online de Java Orientado a Objetos na Udemy. Depois disso, decidi  transitar para o python, linguagem que já acumulo mais de 50 horas de  estudo online, tendo um curso completo do básico ao avançado.
Contudo, recentemente, devido a oportunidades, comecei a me aventurar em  Bootcamps, onde descobri o universo do Kotlin para desenvolvimento  Android, área que mais me interessa no momento e que considero mais apto para atuar. Nos bootcamps, participo do Santander Bootcamp | Mobile  Developer na DIO e também no Bootcamp HiringCoders  FullStack da Vtex em parceria com a Gama Academy. Também, estou no processo seletivo para  Bolsa de Estudos na Let's Code em FullStack oferecida pelo Santander  Universidades.
Fora isso, sou uma pessoa extremamente comunicativa,  versátil e organizada. Me considero multipotencial, gosto muito de  estudar e lidar com novos desafios.



[Linkedin](https://www.linkedin.com/in/joaowoigt/)   [GitHub](https://github.com/joaowoigt)

<joaowoigt@gmail.com>

