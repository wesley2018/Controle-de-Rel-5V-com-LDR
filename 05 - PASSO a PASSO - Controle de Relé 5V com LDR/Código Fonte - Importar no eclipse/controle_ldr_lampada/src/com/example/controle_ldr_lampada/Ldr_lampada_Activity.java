package com.example.controle_ldr_lampada;
/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
public class Ldr_lampada_Activity extends ActionBarActivity implements OnClickListener {
	
	// DECLARAÇÃO DE VARIÁVEIS
	ImageButton btLampada, btEnviar,btConectar;
	CheckBox ck_ldr;
	boolean status = true;
	TextView tvStatusLampada, tvStatusLDR;
	EditText et_Ip;
	String L, hostIp = null;
	int stLDR = 1;
	Handler mHandler;
	long lastPress;
	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		telaIp(); // FAZ A CHAMADA DO MÉTODO "telaIp"
	}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	// MÉTODO "telaIp"
			public void telaIp(){
				setContentView(R.layout.tela_ip); // INICIALIZA A TELA
				et_Ip = (EditText)findViewById(R.id.et_Ip); // ESTANCIA O EDITTEXT
				/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		    	btConectar = (ImageButton) findViewById(R.id.btConectar); // ESTANCIA O IMAGEBUTTON
		        btConectar.setOnClickListener(this); // ATIVA O CLICK DO BOTÃO
		        /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		    	if(btConectar.isPressed()){ // SE O BOTÃO FOR PRESSIONADO
		    		onClick(btConectar); // EXECUTA A FUNÇÃO REFERENTE AO BOTÃO
		    	}
		    }/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			// MÉTODO "telaPrincipal"
			public void telaPrincipal(){   	
				setContentView(R.layout.activity_ldr_lampada); // INICIALIZA A TELA
				/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				mHandler = new Handler(); // VARIÁVEL "mHandler" INICIALIZADA
		        mHandler.post(mUpdate);	 // VARIÁVEL "mHandler" CHAMA O MÉTODO "mUpdate"
		        /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		     // ESTANCIA O IMAGEBUTTON
				btLampada = (ImageButton) findViewById(R.id.btLampada);
				btEnviar = (ImageButton) findViewById(R.id.bt_Enviar);
				// ATIVA O CLICK DO BOTÃO
				btLampada.setOnClickListener(this);
		    	btEnviar.setOnClickListener(this);
		    	// ESTANCIA O TEXTVIEW
		    	tvStatusLampada = (TextView) findViewById(R.id.tvStatusLampada);
		    	tvStatusLDR = (TextView) findViewById(R.id.tvStatusLDR);
		    	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		    	// ESTACNCIA O CHECKBOX
		    	ck_ldr = (CheckBox) findViewById(R.id.ck_ldr);
			
				if(btLampada.isPressed()){ // SE O BOTÃO FOR PRESSIONADO
					onClick(btLampada); // EXECUTA A FUNÇÃO REFERENTE AO BOTÃO
				}
				if(btEnviar.isPressed()){ // SE O BOTÃO FOR PRESSIONADO
					onClick(btEnviar); // EXECUTA A FUNÇÃO REFERENTE AO BOTÃO
				}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			}
			// MÉTODO QUE EXECUTA A ATUALIZAÇÃO DO TEXTVIEW COM INFORMAÇÃO RECEBIDAS DO ARDUINO
			private Runnable mUpdate = new Runnable() {
		    	public void run() {
		    		arduinoStatus("http://"+hostIp+"/"); // CHAMA O MÉTODO "arduinoStatus"
		    		mHandler.postDelayed(this, 500); // TEMPO DE INTERVALO PARA ATUALIZAR NOVAMENTE A INFORMAÇÃO (500 MILISEGUNDOS)
		    	}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		    };
		 // MÉTODO "arduinoStatus"
		    public void arduinoStatus(String urlArduino){
		    	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				String urlHost = urlArduino; // CRIA UMA STRING
				String respostaRetornada = null; // CRIA UMA STRING CHAMADA "respostaRetornada" QUE POSSUI VALOR NULO
				/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				try{
					respostaRetornada = ConectHttpClient.executaHttpGet(urlHost); // STRING "respostaRetornada" RECEBE RESPOSTA RETORNADA PELO ARDUINO
					String resposta = respostaRetornada.toString(); // STRING "resposta"
					resposta = resposta.replaceAll("\\s+", "");
					
					String[] b = resposta.split(",");  // O VETOR "String[] b" RECEBE  O VALOR DE "resposta.split(",")"    	     
					/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
					if(b[0].equals("LDRLIG")){	// SE POSIÇÃO 0 DO VETOR IGUAL A "LDRLIG"			
						tvStatusLDR.setText("LIGADO"); // TEXTVIEW RECEBE LIGADO
						ck_ldr.setText("Desabilitar o sensor LDR"); // TEXTO DO CHECKBOX É ALTERADO
					}
					else{/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
						if(b[0].equals("LDRDESLIG")){ // SE POSIÇÃO 0 DO VETOR IGUAL A "LDRDESLIG"	
							tvStatusLDR.setText("DESLIGADO"); // TEXTVIEW RECEBE DESLIGADO
							ck_ldr.setText("Habilitar o sensor LDR"); // TEXTO DO CHECKBOX É ALTERADO
						}
					}
					if(b[1].equals("LLIG")){ // SE POSIÇÃO 0 DO VETOR IGUAL A "LLIG"		
						tvStatusLampada.setText("LIGADA"); // TEXTVIEW RECEBE LIGADA
					} 
					else{
						if(b[1].equals("LDESLIG")){ // SE POSIÇÃO 0 DO VETOR IGUAL A "LDESLIG"
							tvStatusLampada.setText("DESLIGADA"); // TEXTVIEW RECEBE DESLIGADA				
						}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
					}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				catch(Exception erro){
				}
			}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	@Override
	public void onClick(View bt) { // MÉTODO QUE GERENCIA OS CLICK'S NOS BOTÕES
		if(bt == btConectar){ // SE BOTÃO CLICKADO
			if(et_Ip.getText().toString().equals("")){ // SE EDITTEXT ESTIVER VAZIO
				Toast.makeText(getApplicationContext(), // FUNÇÃO TOAST
				"Digite o IP do Ethernet Shield!", Toast.LENGTH_SHORT).show(); // EXIBE A MENSAGEM
			}else{ // SENÃO
			hostIp = et_Ip.getText().toString(); // STRING "hostIp" RECEBE OS DADOS DO EDITTEXT CONVERTIDOS EM STRING
			// FUNÇÃO QUE OCULTA O TECLADO APÓS CLICAR EM CONECTAR
			InputMethodManager escondeTeclado = (InputMethodManager)getSystemService(
		    Context.INPUT_METHOD_SERVICE);
		    escondeTeclado.hideSoftInputFromWindow(et_Ip.getWindowToken(), 0);
			telaPrincipal(); // FAZ A CHAMADA DO MÉTODO "telaPrincipal"
			}	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		}
		/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		String url = null; // CRIA UMA STRING CHAMADA "url" QUE POSSUI VALOR NULO
		/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		if(bt == btLampada){ // SE BOTÃO CLICKADO
			/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			if(tvStatusLDR.getText() == "LIGADO"){ // SE TEXTVIEW POSSUI VALOR "LIGADO"
				Toast.makeText(getApplicationContext(), // FUNÇÃO TOAST
    					"Desabilite o LDR para controlar a lâmpada pelo botão!", Toast.LENGTH_SHORT).show();
			}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			if(tvStatusLDR.getText() == "DESLIGADO"){ // SE TEXTVIEW POSSUI VALOR "DESLIGADO"
				if(tvStatusLampada.getText() == "LIGADA"){ // SE TEXTVIEW POSSUI VALOR "LIGADA"
					url = "http://"+hostIp+"/?RELE=0"; // STRING "url" RECEBE O VALOR APÓS O SINAL DE "="
				}else{ // SENÃO
					if(tvStatusLampada.getText() == "DESLIGADA"){ // SE TEXTVIEW POSSUI VALOR "DESLIGADA"
						url = "http://"+hostIp+"/?RELE=1"; // STRING "url" RECEBE O VALOR APÓS O SINAL DE "="
					}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			}	/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		if(bt == btEnviar){ // SE BOTÃO CLICKADO
			if(ck_ldr.isChecked()){ // SE CHECKBOX ESTÁ CHECADO
				if(stLDR == 1){ // SE VARIÁVEL FOI IGUAL A 1
            		ck_ldr.setText("Habilitar o sensor LDR"); // TEXTO DO CHECKBOX É ALTERADO
            		stLDR = 0; // VARIÁVEL RECEBE 0
            		url = "http://"+hostIp+"/?LDR=0"; // STRING "url" RECEBE O VALOR APÓS O SINAL DE "="
            		ck_ldr.setChecked(false); // DESMARCA O CHECKBOX
            		}else{ // SENÃO
            			if(stLDR == 0){ // SE VARIÁVEL FOI IGUAL A 0
            				ck_ldr.setText("Desabilitar o sensor LDR"); // TEXTO DO CHECKBOX É ALTERADO
            				stLDR = 1; // VARIÁVEL RECEBE 1 
            				url = "http://"+hostIp+"/?LDR=1"; // STRING "url" RECEBE O VALOR APÓS O SINAL DE "="
            				ck_ldr.setChecked(false); // DESMARCA O CHECKBOX
            			}
            		}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
				}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
			}/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		
		String urlGetHost = url; // CRIA UMA STRING CHAMADA "urlGetHost" QUE RECEBE O VALOR DA STRING "url"
		/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
		//INICIO DO TRY CATCH
		try{
			ConectHttpClient.executaHttpGet(urlGetHost); // PASSA O PARÂMETRO PARA O O MÉTODO "executaHttpGet" NA CLASSE "ConectHttpClient" E ENVIA AO ARDUINO
		}
		catch(Exception erro){ // FUNÇÃO DE EXIBIÇÃO DO ERRO
		}
		/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	}
	// MÉTODO QUE VERIFICA O BOTÃO DE VOLTAR DO DISPOSITIVO ANDROID E ENCERRA A APLICAÇÃO SE PRESSIONADO 2 VEZES SEGUIDAS
	public void onBackPressed() {		
	    long currentTime = System.currentTimeMillis();
	    if(currentTime - lastPress > 5000){
	        Toast.makeText(getBaseContext(), "Pressione novamente para sair.", Toast.LENGTH_LONG).show();
	        lastPress = currentTime;  
	    }else{ /**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
	        super.onBackPressed();
	        android.os.Process.killProcess(android.os.Process.myPid());
	    }
	}
}
/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/
/**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*//**MASTERWALKER SHOP*/