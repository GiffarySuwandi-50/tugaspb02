package com.example.tugaspb02;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroupProducts;
    private RadioButton rbReg;
    private RadioButton rbDry;
    private RadioButton rbExpo;
    private RadioButton rbMembership;
    private Button btnPesan;

    private String produkTerpilih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroupProducts = findViewById(R.id.radioGroupProducts);
        rbReg = findViewById(R.id.rbReg);
        rbDry = findViewById(R.id.rbDry);
        rbExpo = findViewById(R.id.rbExpo);
        rbMembership = findViewById(R.id.rbMembership);
        btnPesan = findViewById(R.id.btnPesan);

        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesTransaksi();
            }
        });

        radioGroupProducts.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int idRadioButtonTerpilih = radioGroupProducts.getCheckedRadioButtonId();

                if (idRadioButtonTerpilih == R.id.rbReg) {
                    produkTerpilih = "Regular Service";
                } else if (idRadioButtonTerpilih == R.id.rbDry) {
                    produkTerpilih = "Dry Cleaning Service";
                } else if (idRadioButtonTerpilih == R.id.rbExpo) {
                    produkTerpilih = "Express-o! Service";
                }
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
        if (rbReg.isChecked()) { totalBiayaAdmin += biayaAdmin("Regular Service"); }
        if (rbDry.isChecked()) { totalBiayaAdmin += biayaAdmin("Dry Cleaning Service"); }
        if (rbExpo.isChecked()) { totalBiayaAdmin += biayaAdmin("Express-o! Service"); }
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
        if (rbReg.isChecked()) { kuantitasBarang++;}
        if (rbDry.isChecked()) { kuantitasBarang++;}
        if (rbExpo.isChecked()) { kuantitasBarang++;}
        return kuantitasBarang;
    }

    private void prosesTransaksi() {
        double feeAdmin = kalkulasiBiayaAdmin();
        double diskon = rbMembership.isChecked() ? 0.05 : 0.0;

        // Bon Pemesanan Layanan
        String bon = "===== Receipt =====\nPelayanan yang anda pilih\n";

        if (produkTerpilih != null) {
            double hargaLayananTerpilih = hargaLayanan(produkTerpilih);
            bon += produkTerpilih + " - Harga: " + hargaLayananTerpilih + "\n";
        }

        bon += "Banyak Barang: " + jumlahBarang() + "\n";

        double totalHarga = ambilTotalHargaLayanan();
        double totalBiayaAdmin = feeAdmin;

        // Hitung total harga barang + biaya admin
        double totalHargaBarangDanBiayaAdmin = totalHarga + totalBiayaAdmin;

        // Hitung diskon
        double totalDiskon = totalHargaBarangDanBiayaAdmin * diskon;

        // Hitung total setelah diskon
        double totalSetelahDiskon = totalHargaBarangDanBiayaAdmin - totalDiskon;

        bon += "Biaya Admin: " + totalBiayaAdmin +
                "\nDiskon: " + (diskon * 100) + "%" +
                "\nPemotongan: " + totalDiskon +
                "\nTotal Bayar: " + totalSetelahDiskon;

        tunjukkanStrukBon(bon);
    }



    private double ambilTotalHargaLayanan() {
        double totalHargaProduk = 0;

        if (rbReg.isChecked()) { totalHargaProduk += hargaLayanan("Regular Service");  }
        if (rbDry.isChecked()) { totalHargaProduk += hargaLayanan("Dry Cleaning Service"); }
        if (rbExpo.isChecked()) { totalHargaProduk += hargaLayanan("Express-o! Service");  }
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