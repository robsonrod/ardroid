/*
 * Web Server disponibilizando os dados em JSON.
 * Funçao: Trabalhar na atuaçao dos leds 4 e 8 e na exibiçao dos dados de Luz e Temperatura  
 * Autor: Robson Antonio Rodrigues
 * Baseado no exemplo WebServer da versão 0021 da IDE Arduino e no exemplo e 
 * no exemplo encontrado em http://nerdydog.it/ 
 *
 * Testado com a gem RestClient do ruby e com a API rest do Java.
 * Faltando atuaçao com a placa tomada e o Sensor de Corrente.
 */

#include <SPI.h>
#include <Ethernet.h>

const int PINO_LM35 = A3; // Usando a porta 3 para analogica 3 para o LM35
const int PINO_LDR = A5; //Usando a porta 5 pra o LDR 
const int LIGADO = 1;
const int DESLIGADO = 0;

// Selecione um endereço MAC para a shield.
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };

// Defina um IP para o seu dispositivo:
byte ip[] = { 192,168,0, 177 };

//Inicializa o servidor Web utilizando a porta padrao
Server server(80);

String leitura = String(30);

void setup()
{
  //Inicia a conexao 
  Ethernet.begin(mac, ip);
  delay(1000);
  
  //Configura os leds
  pinMode(4, OUTPUT);
  digitalWrite(4, LOW);
  pinMode(8, OUTPUT); 
  digitalWrite(5, LOW);
  //Inicia o Serial
  Serial.begin(9600);
}

void loop()
{
  Client client = server.available();
  if (client) {
    while (client.connected()) {
      if (client.available()) {
        //Recebe um caracter
        char c = client.read();
        if (leitura.length() < 30){
          leitura = leitura + c;
        }
        
        if (c == '\n') {
          Serial.print(leitura);
          //Send defaul html header
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println();
          
          if(leitura.startsWith("GET /?out=4&status=1")){
            Serial.print("\n 4 HIGH \n");
            digitalWrite(4, HIGH);
            client.print("{\"estado\" : \"1\", \"out\":\"");
            client.print(4);
            client.print("\"}");
          }

          if(leitura.startsWith("GET /?out=4&status=0")){
            Serial.print("\n 4 LOW \n");
            digitalWrite(4, LOW);
            client.print("{\"estado\" : \"0\", \"out\":\"");
            client.print(4);
            client.print("\"}");
          }
          
          if(leitura.startsWith("GET /?out=8&status=1")){
            Serial.print("\n 8 HIGH \n");
            digitalWrite(8, HIGH);
            client.print("{\"estado\" : \"1\", \"out\":\"");
            client.print(8);
            client.print("\"}");
          }

          if(leitura.startsWith("GET /?out=8&status=0")){
            Serial.print("\n 8 LOW \n");
            digitalWrite(8, LOW);
            client.print("{\"estado\" : \"0\", \"out\":\"");
            client.print(8);
            client.print("\"}");
          }
          
          if(leitura.startsWith("GET /?out=temp")){
            client.print("{device:[{\"estado\" : \"ok\" , \"value\" : \"");
            client.print(recuperaResultadoCelsius());
            client.print("\"}]}");
          }
         
          if(leitura.startsWith("GET /?out=light")){
            client.print("{device:[{\"estado\" : \"ok\" , \"value\" : \"");
            client.print(recuperaEstadoLuz());
            client.print("\"}]}");
          }  
          
          if(leitura.startsWith("GET /?out=all")){
            Serial.print("\n OUT ALL\n");
            client.print("{\"ip\" : \"192.168.0.177\", ");
            client.print("\"devices\" : ");
            client.print("[{ \"type\" : \"led\", \"nome\" : \"Luz Vermelha\", \"out\" : \"");
            client.print("8");
            client.print("\"}");
            client.print(",{ \"type\" : \"led\", \"nome\" : \"Luz Amarela\", \"out\" : \"");
            client.print("4");
            client.print("\"}");
            client.print(",{ \"type\" : \"sensor\", \"nome\" : \"Temperatura\", \"out\" : \"");
            client.print("temp");
            client.print("\"}");
            client.print(",{ \"type\" : \"sensor\", \"nome\" : \"Luminosidade\", \"out\" : \"");
            client.print("light");
            client.print("\"}");
            client.print("]}");
          }
    leitura = "";
    client.stop();
    
  }
}
    }
  }
}

float recuperaResultadoCelsius(){
 return (analogRead(PINO_LM35) * 5.0 * 100)/ 1024.0;
}

int recuperaEstadoLuz(){
  
int leituraLDR = analogRead(PINO_LDR);
       
  if(leituraLDR < 850){
     return LIGADO;  
  } else {
     return DESLIGADO; 
  }
}
  
