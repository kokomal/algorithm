package yuanjun.chen.game.guessNumber;

import yuanjun.chen.game.guessNumber.inner.GuessGame;

public class GuessNumberConsole {

	public static void main(String[] args) throws Exception {
		GuessGame game = new GuessGame(4, 10, '0');
		game.play();
	}
}