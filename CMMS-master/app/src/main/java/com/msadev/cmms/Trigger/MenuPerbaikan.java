package com.msadev.cmms.Trigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.R;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.MENUPERBAIKAN;

public class MenuPerbaikan extends AppCompatActivity {
    ListView listView;
    FloatingActionButton fab;

    ArrayAdapter<String> adapter;
    Intent intent;
    String idmasalah, nomesin;
    MasalahModel mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        mm = getIntent().getParcelableExtra(DATAMASALAH);
        idmasalah = mm.getIdmesin();
        this.setTitle("Action masalah Mesin "+mm.getNomesin());


        listView = (ListView) findViewById(R.id.listview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        String menu[] = {
                "Tambah Progress", "Lihat Progress", "Edit Masalah"
        };

        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, menu);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listSelected);

    }

    private AdapterView.OnItemClickListener listSelected = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String itemView = (String) listView.getItemAtPosition(position);
            intent = new Intent(MenuPerbaikan.this, convertPerbaikan.class);
            intent.putExtra(MENUPERBAIKAN, itemView);
            intent.putExtra(DATAMASALAH, mm);
            startActivity(intent);
            finish();
        }
    };
}
