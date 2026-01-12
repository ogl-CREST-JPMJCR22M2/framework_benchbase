package com.oltpbenchmark.benchmarks.cfp.procedures;

import com.oltpbenchmark.api.Procedure;
import com.oltpbenchmark.api.SQLStmt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCFP extends Procedure {

    public final SQLStmt updateStmt = new SQLStmt(
        "UPDATE A_cfpval SET cfp = cfp - 0.01 WHERE partid = ?"
    );
    public void run(Connection conn, String partid) throws SQLException {
        try (PreparedStatement stmt = this.getPreparedStatement(conn, updateStmt)) {
            stmt.setString(1, partid);
            stmt.executeUpdate();
        }
    }
}