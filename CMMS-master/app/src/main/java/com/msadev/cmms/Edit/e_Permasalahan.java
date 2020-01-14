package com.msadev.cmms.Edit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

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
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_JAM;
import static com.msadev.cmms.Util.JsonResponse.JRES_MASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_SHIFT;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGAL;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class e_Permasalahan extends AppCompatActivity implements View.OnClickListener {

    TextView tvMesin, tvSite;
    EditText etMasalah, etJam, etTanggal, etShift;
    Button btnSimpan;
    String idmasalah, idmesin;
    Calendar kalender;
    TimePickerDialog time;
    DatePickerDialog.OnDateSetListener tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permasalahan);
        this.setTitle("Edit Permasalahan");

        etMasalah = findViewById(R.id.etPermasalahan);
        etTanggal = findViewById(R.id.etTanggal);
        etJam = findViewById(R.id.etJam);
        etShift = findViewById(R.id.etShift);
        btnSimpan = findViewById(R.id.btnSimpan);
        tvMesin = findViewById(R.id.tvNoMesin);
        tvSite = findViewById(R.id.tvSite);
        btnSimpan.setOnClickListener(this);

        MasalahModel mm =getIntent().getParcelableExtra(DATAMASALAH);
        etMasalah.setText(mm.getMasalah());
        etTanggal.setText(mm.getTanggal());
        etJam.setText(mm.getJam());
        etShift.setText(mm.getShift());
        tvMesin.setText(mm.getNomesin());
        tvSite.setText(mm.getSite());
        idmasalah = mm.getIdmasalah();
        idmesin = mm.getIdmesin();

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
                new DatePickerDialog(e_Permasalahan.this, tanggal,
                        kalender.get(Calendar.YEAR),
                        kalender.get(Calendar.MONTH),
                        kalender.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        etJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
            }
        });
        //END DATE PICKER
    }

    private void showTimeDialog() {
        kalender = Calendar.getInstance();
        time = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                etJam.setText(hourOfDay+":"+minute);
            }
        },
                kalender.get(Calendar.HOUR_OF_DAY), kalender.get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this));
        time.show();
    }

    private void tambahData(){
        final String masalah = etMasalah.getText().toString();
        final String tanggal = etTanggal.getText().toString();
        final String jam = etJam.getText().toString();
        final String shift = etShift.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest tambah = new StringRequest(Request.Method.PUT, IPADDRESS + "/masalah", new Response.Listener<String>() {
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
                params.put(JRES_MASALAH, masalah);
                params.put(JRES_SHIFT, shift);
                params.put(JRES_TANGGAL, tanggal);
                params.put(JRES_JAM, jam);
                params.put(JRES_IDMESIN, idmesin);
                params.put(JRES_IDMASALAH, idmasalah);
                return params;
            }
        };
        queue.add(tambah);
    }

    @Override
    public void onClick(View view) {
        if (view == btnSimpan){
            AlertDialog.Builder builder = new AlertDialog.Builder(e_Permasalahan.this);
            builder.setMessage("Pastikan Data yang anda masukkan benar!");
            builder.setPositiveButton("Sudah Benar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    tambahData();
                    Intent intent = new Intent(e_Permasalahan.this, L_Masalah.class);
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
