
<h1 align="center">App Resultados Copa 2022</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=21"><img src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat" border="0" alt="API"></a>
  <br>
  <a href="https://wa.me/+5511961422254"><img alt="WhatsApp" src="https://img.shields.io/badge/WhatsApp-25D366?style=for-the-badge&logo=whatsapp&logoColor=white"/></a>
  <a href="https://www.linkedin.com/in/rubens-francisco-125529162/"><img alt="Linkedin" src="https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white"/></a>
  <a href="mailto:rubens_assis@outlook.com.br"><img alt="Outlook" src="https://img.shields.io/badge/Microsoft_Outlook-0078D4?style=for-the-badge&logo=microsoft-outlook&logoColor=white"/></a>
</p>

<p align="center">  

⭐ Esse é um projeto para demonstrar meu conhecimento técnico no desenvolvimento Android nativo com Kotlin. Mais informações técnicas abaixo.

⛅ Aplicativo que mostra os resultados dos jogos ocorridos na Copa do Mundo de 2022 no Qatar.

</p>

</br>

<p float="left" align="center">
<img alt="screenshot" width="30%" src="https://raw.githubusercontent.com/seu-usuario/App-Resultados-Copa-2022/main/App-Resultados-Copa-2022/app/src/main/appscreenshots/Screenshot_20230605_141121.png"/>
<img alt="screenshot" width="30%" src="screenshots/screenshot-2.png"/>
<img alt="screenshot" width="30%" src="screenshots/screenshot-3.png"/>
</p>

## Download
Faça o download na loja:

//TODO
Faça o download da <a href="apk/app-debug.apk?raw=true">APK diretamente</a>. Você pode ver <a href="https://www.google.com/search?q=como+instalar+um+apk+no+android">aqui</a> como instalar uma APK no seu aparelho android.

  


## Tecnologias usadas e bibliotecas de código aberto

- Minimum SDK level 21
- [Linguagem Kotlin](https://kotlinlang.org/)

- Componentes da SDK do android que foram utilizados:
  - Swipe Refresh Layout: Usado para implementação da atualização da tela ao fazer o swipe na tela.
  - View Model: Utilizado para fornecer um pouco de desacoplamento entre a camada de dados e a view.
  - Navigation: Utilizado para facilitar o código da navegação entre telas no app.
  - Fragment: Os fragments foram utilizados para fornecer uma organização melhor para as telas do meu app. Além de funcionar muito bem com o NvigationComponent.
  - LiveData: Obedece o ciclo de vida da view e fornece a funcionalidade de observer para variáveis que necessitam ser observadas para notificar quando os dados foram obtidos para fazer todo o processo de atualização das views.
  - ViewBinding: Fornece uma maneira simples de referenciar os elementos da view nas classes que precisam manipular de alguma forma esses elementos.
  - Espresso: biblioteca utilizada para fazer testes de ui no meu aplicativo.
  - JUnit4: biblioteca para conseguir rodar testes automatizados no android studio.

- Arquitetura 
  - MVVM (View - ViewModel - Model): Utilizada para colocar um intermediário entre a view e a lógica de negócio. No meu app a viewModel presente na arquitetura mvvm me ajuda a separar a lógica de obtençao de dados da camada de view(fragments e activity).
  - Eu também utilizei uma interface para servir de intermediario entre a api e a classe que deseja obter os dados.
  
- Bibliotecas 
  - [Retrofit](https://square.github.io/retrofit/): Biblioteca para fazer requisições HTTP para as APIs.
  - [Gson Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson): Biblioteca para converter objetos JSON em objetos JAVA compreendíveis no Android Studio.
  - [okHttp Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor): Biblioteca que serve para obter Logs mais detalhados das requisições HTTPs.
  - [Glide](https://github.com/bumptech/glide): Biblioteca para carregar imagens através da url e armazená-las em cache.


## Arquitetura
**App Resultados Copa 2022** utiliza a arquitetura [MVVM]
(https://developer.android.com/topic/architecture).
</br></br>
<img width="60%" src="screenshots/arquitetura.png">
<br>

## API de terceiros

App Clima usa a [Weather API](https://openweathermap.org/api).<br>
 A Weather API disponibiliza o clima atual de vários lugares do mundo em tempo real. <br>
 O App Clima também utiliza a API [Rest Countries](https://restcountries.com/). A Rest Countries Api disponibiliza dados (nome, capital, etc) da maioria dos países do mundo. 

## Features

### Ver clima local ou global
<img src="screenshots/feature-1.gif" width="25%"/>

Visualização do clima local da cidade onde o usuário está, pegando essa informação de forma dinâmica da web. E visualização de uma recycler view de países com suas respectivas bandeiras que o usuário pode escolher e ver o clima atual na capital daquele país.


# Licença



```xml
    Copyright [2023] [Rubens Francisco de Assis]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

```
