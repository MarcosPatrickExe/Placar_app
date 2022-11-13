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

            var placar: Placar = Placar("Jogo sem Config","Marcos", "Patrick",10, 4, 1, 2)


            override fun onCreate (savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)
                        setContentView(R.layout.activity_config)
                       // placar= getIntent().getExtras()?.getSerializable("placar") as Placar
                        //Log.v("PDM22",placar.nome_partida)
                        //Log.v("PDM22",placar.has_timer.toString())

                        this.loadConfig()
            }


            private fun saveConfig () {
                        val sp :SharedPreferences = super.getSharedPreferences("configPlacar", Context.MODE_PRIVATE)
                        val edShared = sp.edit()

                        edShared.putString("matchName", placar.nomePartida)
                        edShared.putString("firstPlayerName", placar.firstPlayerName)
                        edShared.putString("secondPlayerName",placar.secondPlayerName )
                        edShared.putBoolean("useTimer",placar.useTimer)
                        edShared.commit()
            }


            private fun loadConfig () {
                        val sp:SharedPreferences = super.getSharedPreferences( "configPlacar", Context.MODE_PRIVATE)
                        this.placar.nomePartida = sp.getString("matchName","Jogo Padrão").toString()
                        this.placar.firstPlayerName = sp.getString("firstPlayerName", "").toString()

                        this.initInterface( this.placar );
            }


            private fun initInterface ( placar :Placar) {
                        super.findViewById<EditText>( R.id.inputNomePartida).setText( placar.nomePartida)
                        super.findViewById<EditText>( R.id.inputFirstPlayerName).setText( placar.firstPlayerName)
                        super.findViewById<EditText>( R.id.inputSecondPlayerName).setText( placar.secondPlayerName )
                        super.findViewById<Switch>( R.id.useTimer).isChecked = placar.useTimer
            }



            private fun updatePlacarConfig () {

                    this.placar.nomePartida  = findViewById<TextView>(R.id.nomePartida).toString()
                    this.placar.firstPlayerName = findViewById<TextView>(R.id.inputFirstPlayerName).toString()
                    this.placar.secondPlayerName = super.findViewById<TextView>(R.id.inputSecondPlayerName).toString()
                    this.placar.useTimer = super.findViewById<Switch>(R.id.useTimer).isEnabled
            }



            fun openPlacar (v: View) { //Executa ao click do Iniciar Jogo
                        this.updatePlacarConfig() //Pega da Interface e joga no placar
                        this.saveConfig() //Salva no Shared preferences

                        val intent = Intent(this, PlacarActivity::class.java).apply{
                                    putExtra("placar", placar)
                        }

                        super.startActivity( intent )
            }
}