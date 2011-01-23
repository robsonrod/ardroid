/*
 * Web Server com a exibição da temperatura de a luz ambiente no formato xml.
 * Autor: Robson Antonio Rodrigues
 * Objetivo: Usar os dados em uma aplicaçao android que informa os status de luz/temperatura.
 * Baseado no exemplo WebServer da versão 0021 da IDE Arduino e no exemplo
 * do blog http://blogdoje.com.br/2010/04/11/teste-do-shield-ethernet-seeeduino/
 * 
 */

#include <SPI.h>
#include <Ethernet.h>
const int PINO_LM35 = A3; // Usando a porta 3 para analogica 3 para o LM35
const int PINO_LDR = A5; //Usando a porta 5 pra o LDR 
const int LIGADO = 0;
const int DESLIGADO = 1;

// Selecione um endereço MAC para a shield.
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };
// Defina um IP para o seu dispositivo:
byte ip[] = { 192,168,0, 177 };
//Inicializa o servidor Web utilizando a porta padrao
Server server(80);

void setup()
{
  //Inicia a conexao 
  Ethernet.begin(mac, ip);
  server.begin();
}

void loop()
{
  Client client = server.available();
  if (client) {
    // an http request ends with a blank line
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        //Recebe um caracter
        char c = client.read();
        
         // Se o caracter 'c' for um linefeed e a linha está em branco, 
         //a requisição http encerrou. Entao envia a resposta.
        if (c == '\n' && currentLineIsBlank) {
          //Envia um cabeçalho de sucesso padrao do http
          client.println("HTTP/1.1 200 OK");
          //Monta o xml com os dados da luz e temperatura
          client.println("Content-Type: text/xml");
          client.println();
          client.println("<xml version=\"1.0\">");
          client.println("<sensores>");
          client.println("<luz>");
          client.println(recuperaEstadoLuz());
          client.println("</luz>");
          client.println("<temperatura>");
          client.println(recuperaResultadoCelsius());
          client.println("</temperatura>");
          client.println("</sensores>");
          client.println("</xml>");
          break;
        }
        if (c == '\n') {
          //se o caracter 'c' é um linefeed, entao esta começando a 
          //receber uma nova linha de caracteres
          currentLineIsBlank = true;
        } 
        else if (c != '\r') {
         //se 'c' for diferente de nova linha e de retorno do carro 
         //entao a linha de entrada esta preenchida
          currentLineIsBlank = false;
        }
      }
    }
    // aguarda o navegador receber os dados
    delay(1);
    client.stop();
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
