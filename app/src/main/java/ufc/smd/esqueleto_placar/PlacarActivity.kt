package ufc.smd.esqueleto_placar
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.getSystemService
import data.Placar
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.charset.StandardCharsets
import java.lang.Class;



class PlacarActivity : AppCompatActivity() {

            // objeto Placar inicializado na classe "ConfigActivity"
            lateinit var placar :Placar;
            lateinit var resultadoJogo: TextView;
            private var game :Int = 0;

            override fun onCreate(savedInstanceState: Bundle?) {
                        super.onCreate(savedInstanceState)
                        super.setContentView(R.layout.activity_placar)
                        super.getSupportActionBar()?.hide();
                        // this.placar = super.getIntent().getExtras()?.getSerializable("placar") as Placar;
                        this.placar = super.getIntent().getSerializableExtra("placarBundle") as Placar;


               //         this.placar = super.getIntent().getExtras()?.getSerializable("placarBundle") as Placar

                        Log.v("PDM 2022: ", "(Placar activity)  Name: ${placar.nomePartida}  /// Nome P1: ${placar.firstPlayerName}, useTimer? ${placar.useTimer}");

                        super.findViewById<TextView>(R.id.nomePartida).text = this.placar.nomePartida;
                        super.findViewById<TextView>(R.id.nameFirstPlayer).text = this.placar.firstPlayerName;
                        super.findViewById<TextView>(R.id.nameSecondPlayer).text  = this.placar.secondPlayerName;
                        super.findViewById<TextView>(R.id.firstPlayerScore).text = this.placar.scoreFirstPlayer.toString();
                        super.findViewById<TextView>(R.id.secondPlayerScore).text = this.placar.scoreSecondPlayer.toString();
                        super.findViewById<TextView>(R.id.setPlayerUm).text = this.placar.setFirstPlayer.toString();
                        super.findViewById<TextView>(R.id.setPlayerDois).text = this.placar.setSecondPlayer.toString();


                if ( placar.useTimer ) {
                                 super.findViewById<TextView>(R.id.txtTimer) .visibility = View.VISIBLE;
                                 super.findViewById<ImageButton>(R.id.btnPause).visibility = View.VISIBLE;
                                 super.findViewById<ImageButton>(R.id.btnPlay).visibility = View.VISIBLE;
                        }else{
                                super.findViewById<TextView>(R.id.txtTimer).visibility = View.INVISIBLE;
                                super.findViewById<ImageButton>(R.id.btnPause).visibility = View.INVISIBLE;
                                super.findViewById<ImageButton>(R.id.btnPlay).visibility = View.INVISIBLE;
                        }

                     //   this.ultimoJogos();
            }


            fun ultimoJogos ( v:View) {
                            val sharedFilename :String = "PreviousGames";
                            val sp :SharedPreferences = super.getSharedPreferences( sharedFilename, Context.MODE_PRIVATE);
                            var matchNumber  = sp.getInt("numberMatch", 0);

                           // var matchStr :String = sp.getString("match1","").toString();
                             Log.v("PDM22", " Ultima partida: "+matchNumber);


                            if ( matchNumber != 0 ){
                                        var matchStr  = sp.getString("match$matchNumber", "");

                                        var dis = ByteArrayInputStream( matchStr?.toByteArray( Charsets.ISO_8859_1))
                                        var oos = ObjectInputStream(dis)
                                        var prevPlacar :Placar = oos.readObject() as Placar

                                        Log.v("PDM22", "Jogo Salvo:"+ prevPlacar.getMatchResult())
                            }
            }


            fun saveGame(v: View) {

                            val sharedFilename = "PreviousGames";
                            val sp: SharedPreferences = getSharedPreferences(sharedFilename, Context.MODE_PRIVATE);
                            val edShared = sp.edit();

                            //Salvar o número de jogos já armazenados mais o jogo atual
                            var numMatches = sp.getInt("numberMatch", 0) + 1;
                            edShared.putInt("numberMatch", numMatches)

                            //Escrita em Bytes de Um objeto Serializável
                            var dt = ByteArrayOutputStream();
                            var oos = ObjectOutputStream(dt);
                            oos.writeObject(placar);

                            //Salvar como "match"
                            edShared.putString("match"+numMatches, dt.toString(StandardCharsets.ISO_8859_1.name()));
                            edShared.commit();
            }



            fun alteraPontosJogadorUm (v:View){
                            this.placar.scoreFirstPlayer++;

                            if( this.placar.scoreFirstPlayer == 15.toShort() ) {
                                        this.placar.setFirstPlayer++;
                                        this.placar.scoreFirstPlayer = 0;
                                        super.findViewById<TextView>( R.id.setPlayerUm ).text =  this.placar.setFirstPlayer.toString();
                            }
                            super.findViewById<TextView>( R.id.firstPlayerScore).text = placar.scoreFirstPlayer.toString() ;
            }


            fun alteraPontosJogadorDois (v :View){
                            this.placar.scoreSecondPlayer++;

                            if( this.placar.scoreSecondPlayer == 15.toShort() ) {
                                        this.placar.setSecondPlayer++;
                                        this.placar.scoreSecondPlayer = 0;
                                        super.findViewById<TextView>( R.id.setPlayerDois ).text =  this.placar.setSecondPlayer.toString();
                            }
                            super.findViewById<TextView>( R.id.secondPlayerScore).text = placar.scoreSecondPlayer.toString() ;
            }


           fun vibrar (v:View){
                            val buzzer = this.getSystemService<Vibrator>()
                            val pattern = longArrayOf(0, 200, 100, 300)

                            buzzer?.let {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
                                } else {
                                    //deprecated in API 26
                                    buzzer.vibrate(pattern, -1)
                                }
                            }
           }
}