package data
import java.io.Serializable


data class Placar(
            var nomePartida :String,
            var firstPlayerName :String,
            var secondPlayerName :String,
            var scoreFirstPlayer :Short = 0,
            var scoreSecondPlayer :Short = 0,
            var setFirstPlayer :Short = 0,
            var setSecondPlayer :Short = 0,
            var useTimer :Boolean = true
      ) :Serializable {

      fun getMatchResult() :String{
            return "${this.scoreFirstPlayer} - ${this.scoreSecondPlayer}";
      }
}
