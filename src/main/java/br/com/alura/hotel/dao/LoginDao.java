package br.com.alura.hotel.dao;

import br.com.alura.hotel.jdbc.ConnectDb;
import br.com.alura.hotel.model.LoginModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDao {
    private Connection conn;

    public LoginDao() throws SQLException {
        this.conn = new ConnectDb().getDBConnection();
    }

    public List<LoginModel> getList() {

        List<LoginModel> logins = new ArrayList<>();
        String sql = "SELECT * FROM Usuarios;";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LoginModel l = new LoginModel();
                l.setId(rs.getInt("id"));
                l.setUsuario(rs.getString("username"));
                l.setSenha(rs.getString("password"));

                logins.add(l);
            }
            ps.close();
            rs.close();
            conn.close();

            return logins;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro na lista");
            return null;
        }
    }
}
