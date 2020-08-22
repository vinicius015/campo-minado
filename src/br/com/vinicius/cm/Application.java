package br.com.vinicius.cm;

import br.com.vinicius.cm.model.Board;
import br.com.vinicius.cm.view.TerminalBoard;

public class Application {

	public static void main(String[] args) {
		
		Board board = new Board(10, 10, 10);
		new TerminalBoard(board);
		
	}
}
