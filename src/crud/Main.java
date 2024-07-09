package crud;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Main {
    public static void main(String[] args) {
        while (true) {
            String[] options = {"Tampilkan Semua Agama", "Tambah Agama Baru", "Update Nama Agama", "Hapus Agama", "Keluar"};
            int choice = JOptionPane.showOptionDialog(null, "Silakan pilih operasi:", "Manajemen Agama", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    tampilkanSemuaAgama();
                    break;
                case 1:
                    tambahAgamaBaru();
                    break;
                case 2:
                    updateNamaAgama();
                    break;
                case 3:
                    hapusAgama();
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Terima kasih!");
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Pilihan tidak valid.");
                    break;
            }
        }
    }

    private static void tampilkanSemuaAgama() {
        try {
            List<Agama> agamaList = Agama.getAllAgama();

            // Membuat array dua dimensi untuk data tabel
            String[][] data = new String[agamaList.size()][2];
            for (int i = 0; i < agamaList.size(); i++) {
                Agama agama = agamaList.get(i);
                data[i][0] = agama.getKodeAgama();
                data[i][1] = agama.getNamaAgama();
            }

            // Nama kolom tabel
            String[] columns = {"Kode Agama", "Nama Agama"};

            // Membuat tabel dengan model data default
            JTable table = new JTable(new DefaultTableModel(data, columns));

            // Membuat JScrollPane untuk menampung tabel
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new java.awt.Dimension(500, 200));

            // Menampilkan JScrollPane dengan tabel
            JOptionPane.showMessageDialog(null, scrollPane, "Daftar Agama", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat mengambil data dari database: " + ex.getMessage());
        }
    }

    private static void tambahAgamaBaru() {
        String kodeAgama = JOptionPane.showInputDialog("Masukkan kode agama baru:");
        String namaAgama = JOptionPane.showInputDialog("Masukkan nama agama baru:");

        Agama agamaBaru = new Agama(kodeAgama, namaAgama);

        try {
            agamaBaru.save();
            JOptionPane.showMessageDialog(null, "Data agama berhasil disimpan.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat menyimpan data ke database: " + ex.getMessage());
        }
    }

    private static void updateNamaAgama() {
        String kodeUpdate = JOptionPane.showInputDialog("Masukkan kode agama yang akan diupdate:");
        try {
            Agama agamaToUpdate = Agama.getAgamaByKode(kodeUpdate);
            if (agamaToUpdate != null) {
                String newNamaAgama = JOptionPane.showInputDialog("Masukkan nama agama baru:");
                agamaToUpdate.setNamaAgama(newNamaAgama);
                agamaToUpdate.update();
                JOptionPane.showMessageDialog(null, "Data agama berhasil diupdate.");
            } else {
                JOptionPane.showMessageDialog(null, "Agama dengan kode tersebut tidak ditemukan.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat mengakses data dari database: " + ex.getMessage());
        }
    }

    private static void hapusAgama() {
        String kodeDelete = JOptionPane.showInputDialog("Masukkan kode agama yang akan dihapus:");
        try {
            Agama agamaToDelete = Agama.getAgamaByKode(kodeDelete);
            if (agamaToDelete != null) {
                agamaToDelete.delete();
                JOptionPane.showMessageDialog(null, "Data agama berhasil dihapus.");
            } else {
                JOptionPane.showMessageDialog(null, "Agama dengan kode tersebut tidak ditemukan.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat mengakses data dari database: " + ex.getMessage());
        }
    }
}
