package data
import java.io.Serializable


data class Placar(
            var nome_partida :String,
            var firstPlayerName :String,
            var secondPlayerName :String,
            var scoreFirstPlayer :Short = 0,
            var scoreSecondPlayer :Short = 0,
            var setFirstPlayer :Short = 0,
            var setSecondPlayer :Short = 0
      ) :Serializable {

      fun getMatchResult() :String{
            return "${this.scoreFirstPlayer} - ${this.scoreSecondPlayer}";
      }
}
