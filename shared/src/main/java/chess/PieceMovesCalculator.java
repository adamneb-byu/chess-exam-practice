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
        // {topright, topleft, bottomright, bottomleft}
        boolean[] blocked = {false, false, false, false};
        int[][] corners = {
                {1,0}, {0,-1},{-1,0},{0,1}
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
}

class KingMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves1 = (ArrayList) getDiagonals(board,myPosition,1);
        ArrayList<ChessMove> moves2 = (ArrayList) getStraights(board,myPosition,1);
        moves1.removeAll(moves2);
        moves1.addAll(moves2);
        return moves1;
    }
}

class QueenMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves1 = (ArrayList) getDiagonals(board,myPosition,8);
        ArrayList<ChessMove> moves2 = (ArrayList) getStraights(board,myPosition,8);
        moves1.removeAll(moves2);
        moves1.addAll(moves2);
        return moves1;
    }
}

class BishopMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        return getDiagonals(board,myPosition,8);
    }
}

class RookMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        return getStraights(board,myPosition,8);
    }
}

class KnightMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        ArrayList<ChessMove> moves = new ArrayList<>();
        // {topright, topleft, bottomright, bottomleft}
        boolean[] blocked = {false, false, false, false};
        int[][] options = {
                {2,1}, {2,-1},{-2,1},{-2,-1},{1,2},{1,-2},{-1,2},{-1,-2}
        };

        for(int[] option : options){
            moves.addFirst(new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() + option[0], myPosition.getColumn() + option[1]),
                    null));
            if (!moves.getFirst().isValid()) {
                moves.removeFirst();
            }else if (board.getPiece(moves.getFirst().getEndPosition()) != null){
                if (board.getPiece(moves.getFirst().getEndPosition()).getTeamColor() ==
                        board.getPiece(moves.getFirst().getStartPosition()).getTeamColor()){
                    moves.removeFirst();
                }
            }
        }
        return moves;
    }
}

class PawnMovesCalculator extends PieceMovesCalculator{
    public static Collection<ChessMove> getMoves(ChessBoard board, ChessPosition myPosition){
        int colorMod = 1;
        if (board.getPiece(myPosition).getTeamColor() == ChessGame.TeamColor.BLACK){
            colorMod = -1;
        }
        // {row, col, capture?}
        int[][] options = {
                {colorMod,0,0},{colorMod,1,1},{colorMod,-1,1}
        };
        if((myPosition.getRow() == 2 && colorMod == 1) ||
                (myPosition.getRow() == 7 && colorMod == -1)){
            options = new int[][] {
                    {colorMod,0,0},{colorMod,1,1},{colorMod,-1,1},{colorMod*2,0,0}
            };
        }
        ArrayList<ChessMove> moves = new ArrayList<>();
        for(int[] option : options){
            boolean removed = false;
            moves.addFirst(new ChessMove(myPosition,
                    new ChessPosition(myPosition.getRow() + option[0], myPosition.getColumn() + option[1]),
                    null));
            if (!moves.getFirst().isValid()) {
                moves.removeFirst();
                removed = true;
            }else if (board.getPiece(moves.getFirst().getEndPosition()) != null){
                if (board.getPiece(moves.getFirst().getEndPosition()).getTeamColor() ==
                        board.getPiece(moves.getFirst().getStartPosition()).getTeamColor()){
                    moves.removeFirst();
                    removed = true;
                }else if(option[2] == 0){
                    moves.removeFirst();
                    removed = true;
                }
            }else if(option[2] == 1) {
                moves.removeFirst();
                removed = true;
            }
            if(!removed && option[0] == colorMod*2 &&
                    board.getPiece(new ChessPosition(myPosition.getRow()+colorMod, myPosition.getColumn())) != null){
                moves.removeFirst();
                removed = true;
            }
            if(!removed && (moves.getFirst().getEndPosition().getRow() == 1 ||
            moves.getFirst().getEndPosition().getRow() == 8)){
                moves.getFirst().setPromotionPiece(ChessPiece.PieceType.QUEEN);
                ChessPiece.PieceType[] types = {
                        ChessPiece.PieceType.ROOK,
                        ChessPiece.PieceType.BISHOP,
                        ChessPiece.PieceType.KNIGHT
                };
                for(ChessPiece.PieceType type : types){
                    moves.addFirst(new ChessMove(
                            new ChessPosition(myPosition.getRow(), myPosition.getColumn()),
                            new ChessPosition(moves.getFirst().getEndPosition().getRow(), moves.getFirst().getEndPosition().getColumn()),
                            type
                    ));
                }
            }
        }
        return moves;
    }
}