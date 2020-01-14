package com.msadev.cmms.Trigger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.msadev.cmms.Edit.e_Permasalahan;
import com.msadev.cmms.List.L_Progress;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.Tambah.i_Progress;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.MENUPERBAIKAN;

public class convertPerbaikan extends AppCompatActivity {

    String item, valueIntent, idmasalah;
    MasalahModel mm;

    @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        item = intent.getStringExtra(MENUPERBAIKAN);

        mm = getIntent().getParcelableExtra(DATAMASALAH);

        if (item.equals("Tambah Progress")){
            intent = new Intent(getApplicationContext(), i_Progress.class);
            intent.putExtra(DATAMASALAH, mm);
            startActivity(intent);
            finish();
        }else if (item.equals("Lihat Progress")) {
            intent = new Intent(getApplicationContext(), L_Progress.class);
            intent.putExtra(DATAMASALAH, mm);
            startActivity(intent);
            finish();
        }else if (item.equals("Edit Masalah")){
            intent = new Intent(getApplicationContext(), e_Permasalahan.class);
            intent.putExtra(DATAMASALAH, mm);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(getApplicationContext(), "Menu yang anda pilih tidak sesuai...", Toast.LENGTH_SHORT).show();
        }
    }
}
