package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PieceMovesCalculator {
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        switch(board.getPiece(myPosition).getPieceType()){
            case KING -> {
                return KingMovesCalculator.getMoves(board, myPosition);
            }
            case QUEEN -> {
                return QueenMovesCalculator.getMoves(board, myPosition);
            }
            case BISHOP -> {
                return BishopMovesCalculator.getMoves(board, myPosition);
            }
            case KNIGHT -> {
                return KnightMovesCalculator.getMoves(board, myPosition);
            }
            case ROOK -> {
                return RookMovesCalculator.getMoves(board, myPosition);
            }
            case PAWN -> {
                return PawnMovesCalculator.getMoves(board, myPosition);
            }
            case null, default -> {
                return List.of();
            }
        }
    }

    public static Collection<ChessMove> getDiagonals(ChessBoard board, ChessPosition myPosition, int range){
        ArrayList<ChessMove> moves = new ArrayList<>();
        // {topright, topleft, bottomright, bottomleft}
        boolean[] blocked = {false, false, false, false};
        int[][] corners = {
                {1,1}, {1,-1},{-1,1},{-1,-1}
        };

        for(int i =1; i <= range; i++){
            for (int j = 0; j < corners.length; j++) {
                int[] corner = corners[j];
                moves.addFirst(new ChessMove(myPosition,
                        new ChessPosition(myPosition.getRow() + (i * corners[j][0]), myPosition.getColumn() + (i * corners[j][1])),
                        null));
                if (!moves.getFirst().isValid() || blocked[j]) {
                    moves.removeFirst();
                }else if (board.getPiece(moves.getFirst().getEndPosition()) != null){
                    if (board.getPiece(moves.getFirst().getEndPosition()).getTeamColor() ==
                    board.getPiece(moves.getFirst().getStartPosition()).getTeamColor()){
                        moves.removeFirst();
                    }
                    blocked[j] = true;
                }
            }
        }
        return moves;
    }

    public static Collection<ChessMove> getStraights(ChessBoard board, ChessPosition myPosition, int range){
        ArrayList<ChessMove> moves = new ArrayList<>();

        return moves;
    }
}

class KingMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<>();

        return moves;
    }
}

class QueenMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<>();

        return moves;
    }
}

class BishopMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = (ArrayList) getDiagonals(board,myPosition,8);

        return moves;
    }
}

class RookMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<>();

        return moves;
    }
}

class KnightMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<>();

        return moves;
    }
}

class PawnMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<>();

        return moves;
    }
}