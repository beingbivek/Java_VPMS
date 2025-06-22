/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vpms.dao;

import com.sun.jdi.connect.spi.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import vpms.database.MySqlConnection;
import vpms.model.ReportModel;

/**
 *
 * @author Chandani
 */
public class ReportDao {
    MySqlConnection mysql = new MySqlConnection();
    
    public List<ReportModel> getReportByDate(LocalDateTime from, LocalDateTime to){
        List<ReportModel> reporttList = new ArrayList<>();
        Connection conn = mySql.openConnection();
        
        String query = """
            SELECT p.payment_time, v.vehicle_number, pk.entry_time, pk.exit_time,
                                       CAST(p.regular_price AS DOUBLE) +
                                       CAST(p.demand_price AS DOUBLE) +
                                       CAST(p.reservation_price AS DOUBLE) +
                                       CAST(p.extra_charge AS DOUBLE) AS total_fee
                                FROM payments p
                                JOIN vehicle v ON p.vehicle_id = v.id
                                JOIN parking pk ON p.parking_id = pk.parking_id
                                WHERE p.payment_time BETWEEN ? AND ?
                                ORDER BY p.payment_time DESC
                            """;  
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(from));
            stmt.setTimestamp(2, Timestamp.valueOf(to));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ReportModel report = new ReportModel(
                        rs.getTimestamp("payment_time").toLocalDateTime(),
                        rs.getString("vehicle_number"),
                        rs.getTimestamp("entry_time").toLocalDateTime(),
                        rs.getTimestamp("exit_time").toLocalDateTime(),
                        rs.getDouble("total_fee")
                );
                reportList.add(report);
            }
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }

        return reportList;
    }

    public double getTotalRevenueByDate(LocalDateTime from, LocalDateTime to) {
        double total = 0;
        Connection conn = mySql.openConnection();

        String query = """
            SELECT SUM(CAST(p.regular_price AS DOUBLE) +
                       CAST(p.demand_price AS DOUBLE) +
                       CAST(p.reservation_price AS DOUBLE) +
                       CAST(p.extra_charge AS DOUBLE)) AS total
            FROM payments p
            WHERE p.payment_time BETWEEN ? AND ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setTimestamp(1, Timestamp.valueOf(from));
            stmt.setTimestamp(2, Timestamp.valueOf(to));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            mySql.closeConnection(conn);
        }

        return total;
    }
}

        
    }
    
}
