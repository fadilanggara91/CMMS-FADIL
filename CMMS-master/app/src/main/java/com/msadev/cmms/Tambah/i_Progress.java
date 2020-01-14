package com.msadev.cmms.Tambah;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msadev.cmms.List.L_Masalah;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_ENGGINER;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_PERBAIKAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_SHIFT;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGAL;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class i_Progress extends AppCompatActivity implements View.OnClickListener {

    TextView tvMesin, tvSite, tvMasalah;
    EditText etPerbaikan, etEngginer, etTanggal, etShift;
    Button btnSimpan;
    String idmasalah;
    Calendar kalender;
    DatePickerDialog.OnDateSetListener tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        this.setTitle("Input Record Perbaikan");

        MasalahModel mm = getIntent().getParcelableExtra(DATAMASALAH);
        idmasalah = mm.getIdmasalah();

        etPerbaikan = (EditText) findViewById(R.id.etPerbaikan);
        etEngginer = (EditText) findViewById(R.id.etEngginer);
        etTanggal = (EditText) findViewById(R.id.etTanggal);
        etShift = (EditText) findViewById(R.id.etShift);
        tvMesin = (TextView) findViewById(R.id.tvNoMesin);
        tvSite = (TextView) findViewById(R.id.tvSite);
        tvMasalah = (TextView) findViewById(R.id.tvMasalah);
        btnSimpan = (Button) findViewById(R.id.btnSimpan);
        btnSimpan.setOnClickListener(this);
        tvMesin.setText(mm.getNomesin());
        tvSite.setText(mm.getSite());
        tvMasalah.setText(mm.getMasalah());

        //DATE PICKER
        SimpleDateFormat formatTanggal = new SimpleDateFormat("dd-MM-yyyy");
        Date Tanggal = new Date();
        etTanggal.setText(formatTanggal.format(Tanggal));

        kalender = Calendar.getInstance();
        tanggal = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                kalender.set(Calendar.YEAR, year);
                kalender.set(Calendar.MONTH, month);
                kalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String FormatTanggal = "dd-MM-yyy";
                SimpleDateFormat sdf = new SimpleDateFormat(FormatTanggal, Locale.US);
                etTanggal.setText(sdf.format(kalender.getTime()));
            }
        };
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(i_Progress.this, tanggal,
                        kalender.get(Calendar.YEAR),
                        kalender.get(Calendar.MONTH),
                        kalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //END DATE PICKER

    }

    private void tambahData(){
        final String perbaikan = etPerbaikan.getText().toString();
        final String tanggal = etTanggal.getText().toString();
        final String engginer = etEngginer.getText().toString();
        final String shift = etShift.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest tambah = new StringRequest(Request.Method.POST, IPADDRESS + "/progress", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error.Response", String.valueOf(error));
            }
        }
        ){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(JRES_PERBAIKAN, perbaikan);
                params.put(JRES_TANGGAL, tanggal);
                params.put(JRES_IDMASALAH, idmasalah);
                params.put(JRES_ENGGINER, engginer);
                params.put(JRES_SHIFT, shift);
                return params;
            }
        };
        queue.add(tambah);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan){
            AlertDialog.Builder builder = new AlertDialog.Builder(i_Progress.this);
            builder.setMessage("Pastikan Data yang anda masukkan benar!");
            builder.setPositiveButton("Sudah Benar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    tambahData();
                    Intent intent = new Intent(i_Progress.this, L_Masalah.class);
                    startActivity(intent);
                    finish();
                }
            });
            builder.setNegativeButton("Cek Lagi", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
