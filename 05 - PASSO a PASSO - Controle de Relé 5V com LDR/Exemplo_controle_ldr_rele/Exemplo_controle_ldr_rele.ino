/*ONDE EXISTIR "//" SIGNIFICA QUE É UM COMENTÁRIO REFERENTE A LINHA*/

//INCLUSÃO DAS BIBLIOTECAS NECESSÁRIAS PARA A EXECUÇÃO DO CÓDIGO

#include <SPI.h>
#include <Client.h>
#include <Ethernet.h>
#include <Server.h>
#include <Udp.h>
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED }; // NÃO PRECISA MEXER
byte ip[] = { 192, 168, 0, 177 }; // COLOQUE UMA FAIXA DE IP DISPONÍVEL DO SEU ROTEADOR. EX: 192.168.1.110  **** ISSO VARIA, NO MEU CASO É: 192.168.0.177
byte gateway[] = { 192, 168, 0, 1 };// MUDE PARA O GATEWAY PADRÃO DO SEU ROTEADOR **** NO MEU CASO É O 192.168.0.1
byte subnet[] = { 255, 255, 255, 0 }; //NÃO PRECISA MEXER
EthernetServer server(80); //CASO OCORRA PROBLEMAS COM A PORTA 80, UTILIZE OUTRA (EX:8082,8089)
byte sampledata=50; 

int portaRele = 9; // PORTA UTILIZADA PELO RELÉ 
int portaLDR = A2; // PORTA ANALÓGICA UTILIZADA PELO LDR
int estado; // VARIÁVEL QUE RECEBE O STATUS ATUAL DO LDR
int stLDR = 1; // VARIÁVEL QUE CONTROLA O STATUS DO LDR(LIGADO/DESLIGADO)
int st = 0; // VARIÁVEL QUE IDENTIFICA O STATUS ATUAL DO RELÉ(LIGADO/DESLIGADO)

String readString = String(30); //CRIA UMA STRING CHAMADA "readString"

String RELE; // DECLARAÇÃO DE VARIÁVEL DO TIPO STRING
String LDR; // DECLARAÇÃO DE VARIÁVEL DO TIPO STRING

void setup(){
  pinMode(portaRele, OUTPUT); // DEFINE A portaRele COMO SAÍDA
  pinMode(portaLDR, INPUT); // DEFINE A portaLDR COMO ENTRADA
  digitalWrite(portaRele, HIGH); // RELÉ INICIA DESLIGADO
  Ethernet.begin(mac, ip, gateway, subnet); // INICIALIZA A CONEXÃO ETHERNET
  
}
void loop(){
  if(stLDR == 1){ // SE VARIAVEL IGUAL A 1 FAZ
    LDR = "LDRLIG,"; // VARIAVEL RECEBE O VALOR
  estado = analogRead(portaLDR); // VARIÁVEL RECEBE A LEITURA FEITA NA PORTA ANALÓGICA
    
  if (estado > 800)  // SE VARIÁVEL MAIOR QUE 800 FAZ  
  {  
    digitalWrite(portaRele, LOW); // ACIONA O RELÉ  PARA ACENDER A LÂMPADA 
    RELE = "LLIG"; // VARIÁVEL RECEBE O VALOR
    st = 1;  // VARIÁVEL RECEBE O VALOR
  }  
  else // SENÃO
  {  
    digitalWrite(portaRele, HIGH); // RELÉ PERMANECE DESLIGADO
    RELE = "LDESLIG"; // VARIÁVEL RECEBE O VALOR
    st = 0; // VARIÁVEL RECEBE O VALOR
  }
 }

EthernetClient client = server.available(); // CRIA UMA VARIÁVEL CHAMADA client
  if (client) { //SE EXISTE CLIENTE
    while (client.connected()) { // ENQUANTO  EXISTIR CLIENTE CONECTADO
   if (client.available()) { // SE EXISTIR CLIENTE HABILITADO
    char c = client.read(); // CRIA A VARIÁVEL c

    if (readString.length() < 100) // SE O ARRAY FOR MENOR QUE 100
      {
        readString += c; // "readstring" VAI RECEBER OS CARACTERES LIDO
      }
        if (c == '\n') { // SE ENCONTRAR "\n" É O FINAL DO CABEÇALHO DA REQUISIÇÃO HTTP
          if (readString.indexOf("?") <0) //SE ENCONTRAR O CARACTER "?"
          {
          }
          else // SENÃO
        if(readString.indexOf("LDR=1") >0){ // SE ENCONTRAR O PARÂMETRO "LDR=1"
           pinMode(portaLDR, INPUT); // ATIVA A PORTA REFERENTE AO LDR
           stLDR = 1; // VARIÁVEL RECEBE O VALOR
           }
           if(readString.indexOf("LDR=0") >0){ // SE ENCONTRAR O PARÂMETRO "LDR=0"
             stLDR = 0; // VARIÁVEL RECEBE O VALOR
             analogWrite(portaLDR, 127); // SETA A PORTA ANALÓGICA EM NÍVEL BAIXO(DESLIGADA)
             if(st == 1){ // SE VARIÁVEL IGUAL A 1 FAZ
               digitalWrite(portaRele, LOW); // ACIONA O RELÉ  PARA ACENDER A LÂMPADA 
             }
             LDR = "LDRDESLIG,"; // VARIÁVEL RECEBE O VALOR
           }
           if(readString.indexOf("RELE=1") >0){ // SE ENCONTRAR O PARÂMETRO "RELE=1"
             digitalWrite(portaRele, LOW); // ACIONA O RELÉ  PARA ACENDER A LÂMPADA 
             RELE = "LLIG"; // VARIÁVEL RECEBE O VALOR
           }
           if(readString.indexOf("RELE=0") >0){ // SE ENCONTRAR O PARÂMETRO "RELE=0"
             digitalWrite(portaRele, HIGH); // DESLIGA O RELÉ
             RELE = "LDESLIG"; // VARIÁVEL RECEBE O VALOR
           }
           
           client.println("HTTP/1.1 200 OK"); // ESCREVE PARA O CLIENTE A VERSÃO DO HTTP
           client.println("Content-Type: text/html"); // ESCREVE PARA O CLIENTE O TIPO DE CONTEÚDO(texto/html)
           client.println();
          
           client.println(LDR); // RETORNA PARA O CLIENTE INFORMAÇÕES DO LDR
           client.println(RELE); // RETORNA PARA O CLIENTE INFORMAÇÕES DO RELÉ

           readString="";
          client.stop(); // FINALIZA A REQUISIÇÃO HTTP
            }
          }
        }
      }
 }
