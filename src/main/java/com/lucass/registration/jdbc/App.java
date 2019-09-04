package com.lucass.registration.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {
        Connection connection = DbUtils.getConnection();

        String sqlSelect = "SELECT * FROM public.\"Rockets\"";

        String sqlInsert = "INSERT INTO public.\"Rockets\"(" +
                "name, operator, length, boosters, in_project)" +
                "VALUES ('UFO', 'Cripton', 100.0, 4, false)";

        String sqlUpdate = "UPDATE public.\"Rockets\"" +
                "SET name='Planet Express Ship', thrust=100500.0, length=100.0, weight=200.0, boosters=0, operator='Planet Express'" +
                "WHERE id=1";

        String sqlDelete = "DELETE FROM public.\"Rockets\"" +
                "WHERE id=2";

        ResultSet rs = DbUtils.executeQuery(sqlSelect);

        while (rs.next()){

            System.out.println("[Ship name: " + rs.getString(2) + "]");
            System.out.println("Operator = " + rs.getString("operator"));
            System.out.println("Length = " + rs.getDouble("length"));
            System.out.println("Boosters = " + rs.getInt("boosters") + "\n\n");
        }

        DbUtils.execute(sqlInsert);

        System.out.println("Affected rows: " + DbUtils.executeUpdate(sqlUpdate));

        DbUtils.execute(sqlDelete);

        DbUtils.close();
    }
}
