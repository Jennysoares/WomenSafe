# WomenSafe

Aplicativo mobile que promova através do compartilhamento de informações referentes a abusos sofridos por mulheres, rotas seguras, além do mapeamento de locais perigosos que propiciam e favorecem a ação de agressores e criminosos, a fim de evitar possíveis riscos em um determinado local, ampliando a proteção da comunidade feminina

## Motivação

Atividade da matéria Projeto Integrador II, conduzida pelo Professor Kenniston Bonfim e realizada por 
alunos do 5º Semestre do curso de Ciências da Computação do Centro Universitário IESB. 
O aplicativo permite que usuários demarquem zonas consideradas perigosas, para que sejam evitadas e para que as outros usuários saibam a localidade de pontos específicos relacionados a agressões, assaltos e assédios além de auxiliar uma vítima de qualquer abuso, informando as ações legais que podem ser tomadas.
O aplicativo ainda  possibilita que o usuário cadastre placas de carros de aplicativos nos quais o usuário tenha sofrido algum tipo de assédio por parte do motorist e através desse cadastro, proporciona outros usuários a realizar uma busca pela placa de carros de aplicativo para que, antes de aceitar uma corrida, o usuário obtenha informações a respeito de algum abuso ocorrido anteriormente nesse mesmo veículo.

### Capturas de Tela 

![tela_inicial](https://user-images.githubusercontent.com/62408316/84426865-4f5ead80-abfa-11ea-8041-97835075b325.jpg)

![tela_menu](https://user-images.githubusercontent.com/62408316/84427054-a95f7300-abfa-11ea-96f5-e458571741f6.jpg)

### Frameworks

AndroidStudio
Google Firebase
* Firebase Authentification
* Firebase Realtime Database
* DialogFlow
* Heroku

### Exemplo do código

#### Building Gradle
    defaultConfig {
        applicationId "br.com.victoriasantos.womensafe"
        minSdkVersion 23
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    
    

    buildTypes release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }


    dependencies{

    /** Android Jetpack Dependencies */
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'androidx.lifecycle:lifecycle-extensions:2.2.0'
    implementation 'androidx.core:core-ktx:1.2.0'
    implementation 'androidx.fragment:fragment:1.2.4'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.2.2'
    implementation 'androidx.navigation:navigation-ui-ktx:2.2.2'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.2.0'

    /** Firebase Dependencies */
    implementation 'com.google.firebase:firebase-analytics:17.4.0'
    implementation 'com.google.firebase:firebase-auth:19.3.1'
    implementation 'com.google.firebase:firebase-database:19.3.0'

    /** Retrofit Libraries */
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:retrofit:2.7.1'
    implementation 'com.squareup.retrofit2:converter-gson:2.7.1'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.3.0'


    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'com.vicmikhailau:MaskedEditText:3.0.4'
    implementation 'com.google.android.gms:play-services-location:17.0.0'
    implementation 'com.google.android.gms:play-services-maps:17.0.0'
    implementation 'com.google.android.gms:play-services-places:17.0.0'
    testImplementation 'junit:junit:4.13'
    androidTestImplementation 'androidx.test.ext:junit:1.1.1'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'

    //ChatBot libraries
    implementation 'com.github.bassaer:chatmessageview:1.10.0'
    implementation 'com.github.kittinunf.fuel:fuel-android:1.12.1'


    // CardView
    implementation 'androidx.cardview:cardview:1.0.0'




### Refências da API

### Autores

* **Victória Marques dos Santos** - (botar o user do git de cada um)
* **Jennifer Jerônimo** - 
* **Fernando Alvares de Sousa** - sousaf (https://github.com/sousaf)
