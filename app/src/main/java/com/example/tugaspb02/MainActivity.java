package com.example.tugaspb02;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private CheckBox cbReg;
    private CheckBox cbDry;
    private CheckBox cbExpo;
    private RadioButton rbMembership;
    private Button btnPesan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbReg = findViewById(R.id.cbReg);
        cbDry = findViewById(R.id.cbDry);
        cbExpo = findViewById(R.id.cbExpo);
        rbMembership = findViewById(R.id.rbMembership);
        btnPesan = findViewById(R.id.btnPesan);
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesTransaksi();
            }
        });
    }
    private double hargaLayanan(String daftarLayanan) {
        switch (daftarLayanan) {
            case "Regular Service": return 12000.0;
            case "Dry Cleaning Service": return 10000.0;
            case "Express-o! Service": return 15000.0;
            default:
                return 0.0;
        }
    }

    private double kalkulasiBiayaAdmin() {
        double totalBiayaAdmin = 0;
        if (cbReg.isChecked()) { totalBiayaAdmin += biayaAdmin("Regular Service"); }
        if (cbDry.isChecked()) { totalBiayaAdmin += biayaAdmin("Dry Cleaning Service"); }
        if (cbExpo.isChecked()) { totalBiayaAdmin += biayaAdmin("Express-o! Service"); }
        return totalBiayaAdmin;
    }

    private double biayaAdmin(String namaLayanan) {
        switch (namaLayanan) {
            case "Regular Service": return 1000.0;
            case "Dry Cleaning Service": return 500.0;
            case "Express-o! Service": return 2000.0;
            default:
                return 0.0;
        }
    }
    private int jumlahBarang() {
        int kuantitasBarang = 0;
        if (cbReg.isChecked()) { kuantitasBarang++;}
        if (cbDry.isChecked()) { kuantitasBarang++;}
        if (cbExpo.isChecked()) { kuantitasBarang++;}
        return kuantitasBarang;
    }

    private void prosesTransaksi() {
        double feeAdmin = kalkulasiBiayaAdmin();
        double diskon = rbMembership.isChecked() ? 0.05 : 0.0;

        // Bon Pemesanan Layanan
        String bon = "===== Receipt =====\nPelayanan yang anda pilih\n";
        if (cbReg.isChecked()) { bon += "Regular Service" + "\n"; }
        if (cbDry.isChecked()) { bon += "Dry Cleaning Service" + "\n"; }
        if (cbExpo.isChecked()) { bon += "Express-o! Service" + "\n"; }
        bon += "Banyak Barang: " + jumlahBarang() + "\n";

        double totalHarga = ambilTotalHargaLayanan();
        double totalDiskon = totalHarga * diskon;
        double hargaAkhir = totalHarga + feeAdmin - totalDiskon;

        bon += "Biaya Admin: " + feeAdmin +
                "\nDiskon: " + (diskon * 100) + "%" +
                "\nPemotongan: " + totalDiskon +
                "\nTotal Bayar: " + hargaAkhir;

        tunjukkanStrukBon(bon);
    }

    private double ambilTotalHargaLayanan() {
        double totalHargaProduk = 0;

        if (cbReg.isChecked()) { totalHargaProduk += hargaLayanan("Regular Service");  }
        if (cbDry.isChecked()) { totalHargaProduk += hargaLayanan("Dry Cleaning Service"); }
        if (cbExpo.isChecked()) { totalHargaProduk += hargaLayanan("Express-o! Service");  }
        return totalHargaProduk;
    }


    private void tunjukkanStrukBon(String receipt) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pemesanan Layanan Sukses! :D")
                .setMessage(receipt)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}