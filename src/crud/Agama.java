package crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Agama {
    private String kodeAgama;
    private String namaAgama;

    public Agama() {
        // Constructor kosong
    }

    public Agama(String kodeAgama, String namaAgama) {
        this.kodeAgama = kodeAgama;
        this.namaAgama = namaAgama;
    }

    public String getKodeAgama() {
        return kodeAgama;
    }

    public void setKodeAgama(String kodeAgama) {
        this.kodeAgama = kodeAgama;
    }

    public String getNamaAgama() {
        return namaAgama;
    }

    public void setNamaAgama(String namaAgama) {
        this.namaAgama = namaAgama;
    }

    // Create
    public void save() throws SQLException {
        String sql = "INSERT INTO agama (kode_agama, nama_agama) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, this.kodeAgama);
            stmt.setString(2, this.namaAgama);

            stmt.executeUpdate();
        }
    }

    // Read all
    public static List<Agama> getAllAgama() throws SQLException {
        List<Agama> agamaList = new ArrayList<>();
        String sql = "SELECT kode_agama, nama_agama FROM agama";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Agama agama = new Agama();
                agama.setKodeAgama(rs.getString("kode_agama"));
                agama.setNamaAgama(rs.getString("nama_agama"));
                agamaList.add(agama);
            }
        }

        return agamaList;
    }

    // Read by kode_agama
    public static Agama getAgamaByKode(String kodeAgama) throws SQLException {
        Agama agama = null;
        String sql = "SELECT kode_agama, nama_agama FROM agama WHERE kode_agama = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kodeAgama);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    agama = new Agama();
                    agama.setKodeAgama(rs.getString("kode_agama"));
                    agama.setNamaAgama(rs.getString("nama_agama"));
                }
            }
        }

        return agama;
    }

    // Update
    public void update() throws SQLException {
        String sql = "UPDATE agama SET nama_agama = ? WHERE kode_agama = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, this.namaAgama);
            stmt.setString(2, this.kodeAgama);

            stmt.executeUpdate();
        }
    }

    // Delete
    public void delete() throws SQLException {
        String sql = "DELETE FROM agama WHERE kode_agama = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, this.kodeAgama);

            stmt.executeUpdate();
        }
    }
}
