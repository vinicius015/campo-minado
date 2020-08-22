package br.com.vinicius.cm.model;

import java.util.ArrayList;
import java.util.List;

import br.com.vinicius.cm.exception.ExplosionException;

public class Field {

	private final int line;
	private final int column;

	private boolean uncoverd = false;
	private boolean undermined = false;
	private boolean marked = false;

	private List<Field> neighbors = new ArrayList<>();

	Field(int line, int column) {
		this.line = line;
		this.column = column;
	}

	boolean addNeighbor(Field neighbor) {
		boolean differentLine = line != neighbor.line;
		boolean differentColumn = column != neighbor.column;
		boolean diagonal = differentLine && differentColumn;

		int deltaLine = Math.abs(line - neighbor.line);
		int deltaColumn = Math.abs(column - neighbor.column);
		int deltaGeneral = deltaLine + deltaColumn;

		if (deltaGeneral == 1 && !diagonal) {
			neighbors.add(neighbor);
			return true;
		} else if (deltaGeneral == 2 && diagonal) {
			neighbors.add(neighbor);
			return true;
		} else {
			return false;
		}
	}

	void tickToggle() {
		if (!uncoverd) {
			marked = !marked;
		}
	}

	boolean open() {

		if (!uncoverd && !marked) {
			uncoverd = true;

			if (undermined) {
				throw new ExplosionException();
			}

			if (safetyNeighborhood()) {
				neighbors.forEach(n -> n.open());
			}
			return true;
		} else {
			return false;
		}
	}

	boolean safetyNeighborhood() {
		return neighbors.stream().
				noneMatch(neighbor -> neighbor.undermined);
	}
	
	void mine() {
		undermined = true;
	}
	
	public boolean isUndermined() {
		return undermined;
	}
	
	public boolean isMarked() {
		return marked;
	}

	public void setUncoverd(boolean uncoverd) {
		this.uncoverd = uncoverd;
	}

	public boolean isOpened() {
		return uncoverd;
	}

	public boolean isClosed() {
		return !isOpened();
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}
	
	boolean goalAchieved() {
		boolean discovered = !undermined && uncoverd;
		boolean protectd = undermined && marked;
		return discovered || protectd;
	}
	
	long minesInTheNeighborhood() {
		return neighbors.stream().
				filter(neighbor -> neighbor.undermined).count();
	}
	
	void restart() {
		uncoverd = false;
		undermined = false;
		marked = false;
	}

	public String toString() {
		if (marked) {
			return "x";
		} else if (uncoverd && undermined) {
			return "*";
		} else if (uncoverd && minesInTheNeighborhood() > 0) {
			return Long.toString(minesInTheNeighborhood());
		} else if (uncoverd) {
			return " ";
		} else {
			return "?";
		}
	}
}

