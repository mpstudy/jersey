package mps.study.utils;

import mps.study.vo.Member;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataServiceHelper {
    public static DataServiceHelper dataServiceHelper = null;
    private Connection con = null;
    private DataSource dataSource = null;
    private InitialContext initialContext = null;

    private static final String DB_URL = "jdbc:h2:~/test";
    private static final String DRIVER_NAME = "org.h2.Driver";

    private Connection getConnection() throws ClassNotFoundException,
            SQLException {
        Class.forName(DRIVER_NAME);
        con = DriverManager.getConnection(DB_URL, "sa", "");
        return con;
    }

    private void closeConnection() throws SQLException {
        if (isConnectionOpen()) {
            con.close();
            con = null;
        }
    }

    private boolean isConnectionOpen() {
        return (con != null);
    }

    public static DataServiceHelper getInstance() {
        if (dataServiceHelper == null) {
            dataServiceHelper = new DataServiceHelper();
        }
        return dataServiceHelper;
    }

    public boolean execute(String query) throws SQLException, ClassNotFoundException {
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        boolean result = stmt.execute(query);
        closeConnection();
        return result;
    }

    public int executeUpdateQuery(String query) throws SQLException, ClassNotFoundException {
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        int result = stmt.executeUpdate(query);
        closeConnection();
        return result;
    }

    public List<Member> executeQuery(String query) throws ClassNotFoundException, SQLException {
        Connection con = getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        List<Member> als = convertPojoList(rs);
        closeConnection();
        return als;
    }

    private List<Member> convertPojoList(ResultSet rs) throws SQLException {
        List<Member> asl = new ArrayList<Member>();
        while (rs.next()) {
            Member member = new Member(rs.getString("id"), rs.getString("pw"));
            asl.add(member);
        }
        return asl;
    }
}