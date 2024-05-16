package cadastrobd.model.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceManager {

    public static int getValue(String sequenceName) {
        int nextValue = -1;
        String sql = "SELECT NEXT VALUE FOR " + sequenceName + " AS nextval";
        try (Connection conn = ConectorBD.getConnection();
             PreparedStatement stmt = ConectorBD.getPrepared(conn, sql);
             ResultSet rs = ConectorBD.getSelect(stmt)) {

            if (rs.next()) {
                nextValue = rs.getInt("nextval");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextValue;
    }
}
