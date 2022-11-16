package ufc.smd.esqueleto_placar
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import data.Placar



class ConfigActivity : AppCompatActivity() {

            var placar: Placar = Placar(
                "Jogo sem Config",
                "Marcos",
                "Patrick",
                10,
                4,
                1,
                2,
                true
            )


            override fun onCreate (savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)
                        super.setContentView(R.layout.activity_config)
                        super.getSupportActionBar()?.hide();
                       // placar= getIntent().getExtras()?.getSerializable("placar") as Placar
                        //Log.v("PDM22",placar.nome_partida)
                        //Log.v("PDM22",placar.has_timer.toString())

                        this.loadConfig();
            }


            private fun loadConfig () {
                        val sp:SharedPreferences = super.getSharedPreferences( "configPlacar", Context.MODE_PRIVATE)
                        this.placar.nomePartida = sp.getString("matchName","Jogo Padrão").toString()
                        this.placar.firstPlayerName = sp.getString("firstPlayerName", "").toString()
                        this.placar.secondPlayerName = sp.getString("secondPlayerName", "").toString()
                        this.placar.useTimer = sp.getBoolean("useTimer",  true);

                        this.initInterface( this.placar );
            }

            private fun initInterface ( placar :Placar) {
                        Log.v("PDM 2022: ", "Placar Name: ${placar.nomePartida}  /// Nome P1: ${placar.firstPlayerName}");

                        super.findViewById<EditText>( R.id.inputNomePartida).setText( placar.nomePartida)
                        super.findViewById<EditText>( R.id.inputFirstPlayerName).setText( placar.firstPlayerName)
                        super.findViewById<EditText>( R.id.inputSecondPlayerName).setText( placar.secondPlayerName )
                        super.findViewById<Switch>( R.id.useTimer).isChecked = placar.useTimer
            }



            private fun updatePlacarConfig () {
                        this.placar.nomePartida  = findViewById<TextView>(R.id.nomePartida).toString();
                        this.placar.firstPlayerName = findViewById<TextView>(R.id.inputFirstPlayerName).toString();
                        this.placar.secondPlayerName = super.findViewById<TextView>(R.id.inputSecondPlayerName).toString();
                        this.placar.useTimer = super.findViewById<Switch>(R.id.useTimer).isEnabled

                        this.saveConfig( this.placar ); //Salva no Shared preferences
            }


            private fun saveConfig (  placar :Placar ) {
                        val sp :SharedPreferences = super.getSharedPreferences("configPlacar", Context.MODE_PRIVATE);
                        val edShared = sp.edit();

                        edShared.putString("matchName", placar.nomePartida);
                        edShared.putString("firstPlayerName", placar.firstPlayerName);
                        edShared.putString("secondPlayerName", placar.secondPlayerName );
                        edShared.putBoolean("useTimer", placar.useTimer);
                        edShared.commit();
            }


            fun openPlacar (v: View) { //Executa ao click do Iniciar Jogo
                        this.updatePlacarConfig(); //Pega da Interface e joga no placar

                        val intent = Intent(this, PlacarActivity::class.java).apply{
                                    this.putExtra("placar", placar)
                        }

                        super.startActivity( intent );
            }
}