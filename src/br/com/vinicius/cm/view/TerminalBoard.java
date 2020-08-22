package br.com.vinicius.cm.view;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.vinicius.cm.exception.ExplosionException;
import br.com.vinicius.cm.exception.LeaveException;
import br.com.vinicius.cm.model.Board;

public class TerminalBoard {

	private Board board;
	private Scanner scan = new Scanner(System.in);

	public TerminalBoard(Board board) {
		this.board = board;

		executeGame();
	}

	private void executeGame() {
		try {
			boolean proceed = true;
			
			while (proceed) {
				gameLoop();
				
				System.out.println("Outra partida? (S/n)");
				String answer = scan.nextLine();
				
				if ("n".equalsIgnoreCase(answer)) {
					System.out.println("Fim de jogo.");
					proceed = false;
				} else {
					board.restart();
				}
				
			}
			
		} catch (LeaveException e) {
			System.out.println("Tchau!!!");
		} finally {
			scan.close();
		}
	}

	private void gameLoop() {
		try {
			
			while (!board.goalAchieved()) {
				System.out.println(board.toString());
				
				String typed = getTypedValue("Digite (x,y): ");
				
				Iterator<Integer> xy = Arrays.stream(typed.split(","))
					.map(e -> Integer.parseInt(e.trim())).iterator();
				
				typed = getTypedValue("1 - Abrir ou 2 - (Des)Marcar: ");
			
				if ("1".equals(typed)) {
					board.open(xy.next(), xy.next());
				} else if ("2".equals(typed)) {
					board.mark(xy.next(), xy.next());
				}
				
			}
			
			System.out.println(board.toString());
			System.out.println("Você ganhou!!!");
		} catch (ExplosionException e) {
			System.out.println(board.toString());
			System.out.println("Você perdeu!");
		}
		
	}
	
	private String getTypedValue(String text) {
		System.out.print(text);
		String typed = scan.nextLine();
		
		if("sair".equalsIgnoreCase(typed)) {
			throw new LeaveException();
		}
		
		return typed;
	}
	
	
}
