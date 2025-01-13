package org.khrapov.pegsolitaire.test;

import org.khrapov.pegsolitaire.solver.Move;
import org.khrapov.pegsolitaire.solver.Position;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Util {

    public static String hash(String s) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(s.getBytes());
        return toHexString(messageDigest.digest()).substring(0, 48);
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static String solutionToString(Position initialPosition, List<Move> solution) {
        Position p = initialPosition.copy();
        StringBuilder s = new StringBuilder();
        s.append(p);
        for (int i = 0; i < solution.size(); i += 1) {
            s.append("----------------\n");
            Move move = solution.get(i);
            p.set(move.x1, move.y1, false);
            p.set((move.x1 + move.x2) / 2, (move.y1 + move.y2) / 2, false);
            p.set(move.x2, move.y2, true);
            s.append(p);
        }
        return s.toString();
    }
}
