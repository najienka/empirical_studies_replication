package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	//classe partida de xadrez, aqui ester� as regras do jogo
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8); // dimens�o do tabuleiro
		initialSetup();
	}
	
	public ChessPiece[][] getPieces(){
		//retorna uma matriz de pe�a de xadrez correspondente a partida
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i=0; i<board.getRows(); i++) {
			for(int j=0; j<board.getColumns(); j++) {
				mat[i][j] = (ChessPiece)board.piece(i, j);
			}
		}
		return mat; //retorna a matriz de pe�a da partida de xadrez
	}
	
	private void initialSetup() {
		//inicia a partida de xadrez, colocando as pe�as no tabuleiro
		
		board.placePice(new Rook(board, Color.WHITE), new Position(2, 1));
		board.placePice(new King(board, Color.BLACK), new Position(0, 4));
		board.placePice(new King(board, Color.WHITE), new Position(7, 4));
		
	}
	

}
