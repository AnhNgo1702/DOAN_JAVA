package DAO;

import DTO.TaiKhoanDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaiKhoanDAO {

    private ConnectDataBase mySQL;

    public TaiKhoanDAO() {
        try {
            mySQL = new ConnectDataBase();
        } catch (SQLException e) {
            System.out.println("That bai");
        }
    }

    public ArrayList<TaiKhoanDTO> list() {
        ArrayList<TaiKhoanDTO> dstk = new ArrayList<>();
        try {
            mySQL.connect();
            String sql = "SELECT * FROM taikhoan ";
            ResultSet rs = mySQL.executeQuery(sql);
            while (rs.next()) {
                String maNV = rs.getString("MANV");
                String username = rs.getString("USERNAME");
                String password = rs.getString("PASSWORD");
                String ngayTao = rs.getDate("NGAYTAOTK") + "";
                String maQuyen = rs.getString("MAQUYEN");
                int state = rs.getInt("TINHTRANG");

                TaiKhoanDTO tk = new TaiKhoanDTO(maNV, username, password, ngayTao, maQuyen, state);
                dstk.add(tk);
            }
            rs.close();
            mySQL.disconnect();

        } catch (SQLException ex) {
            Logger.getLogger(TaiKhoanDTO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dstk;
    }

    public void set(TaiKhoanDTO tk) {
        try {
            mySQL.connect();
            String sql = "UPDATE taikhoan SET ";
            sql += "USERNAME='" + tk.getUsername() + "', ";
            sql += "PASSWORD='" + tk.getPassword() + "', ";
            sql += "NGAYTAOTK='" + tk.getNgayTao() + "', ";
            sql += "TINHTRANG='" + tk.getState() + "', ";
            sql += "MAQUYEN='" + tk.getMaQuyen() + "' ";
            sql += " WHERE MANV='" + tk.getMaNV() + "'";
            System.out.println(sql);
            mySQL.executeUpdate(sql);
            mySQL.disconnect();
        } catch (SQLException ex) {

        }

    }

    public void add(TaiKhoanDTO tk) {
        try {
            mySQL.connect();
            String sql = "INSERT INTO taikhoan VALUES ";
            sql += "('" + tk.getMaNV() + "', ";
            sql += "'" + tk.getUsername() + "', ";
            sql += "'" + tk.getPassword() + "', ";
            sql += "'" + tk.getNgayTao() + "', ";
            sql += "'" + tk.getState() + "', ";
            sql += "'" + tk.getMaQuyen() + "')";
            System.out.println(sql);
            mySQL.executeUpdate(sql);
            mySQL.disconnect();

        } catch (SQLException ex) {

        }
    }

    public void delete(String maNV) {
        try {
            mySQL.connect();
            String sql = "UPDATE taikhoan SET ";
            sql += "TINHTRANG='2' ";
            sql += " WHERE MANV='" + maNV + "'";
            System.out.println(sql);
            mySQL.executeUpdate(sql);
            mySQL.disconnect();
        } catch (SQLException ex) {
        }
    }

    public static void main(String[] args) {
        TaiKhoanDAO t = new TaiKhoanDAO();
        System.out.println(t.list().size());
        TaiKhoanDTO d = new TaiKhoanDTO("NV001", "phuong", "1234", "2016-09-03", "AD", 1);
        t.delete("NV001");
    }
}