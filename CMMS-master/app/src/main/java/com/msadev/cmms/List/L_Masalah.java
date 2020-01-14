package com.msadev.cmms.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.msadev.cmms.Adapter.MasalahAdapter;
import com.msadev.cmms.Model.MasalahModel;
import com.msadev.cmms.R;
import com.msadev.cmms.Trigger.MenuPerbaikan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.msadev.cmms.Util.JsonResponse.DATAMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_IDPENGGUNA;
import static com.msadev.cmms.Util.JsonResponse.JRES_JAM;
import static com.msadev.cmms.Util.JsonResponse.JRES_MASALAH;
import static com.msadev.cmms.Util.JsonResponse.JRES_NOMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_SHIFT;
import static com.msadev.cmms.Util.JsonResponse.JRES_SITE;
import static com.msadev.cmms.Util.JsonResponse.JRES_TANGGAL;
import static com.msadev.cmms.Util.JsonResponse.TAG_RESULT;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class L_Masalah extends AppCompatActivity implements View.OnClickListener {

    ListView listView;
    List<MasalahModel> masalahModels;
    SwipeRefreshLayout refresh;
    FloatingActionButton fab;
    SearchView searchView;
    MenuItem menuItem;
    Menu menu;
    String idmasalah, nomesin;
    MasalahAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);
        this.setTitle("Permasalahan");

        listView = (ListView) findViewById(R.id.listview);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        masalahModels = new ArrayList<>();

        refresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        masalahModels.clear();
                        loadData();
                        Toast.makeText(getApplicationContext(), "Data Berhasil diperbarui!", Toast.LENGTH_LONG).show();
                    }
                }, 3000);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                MasalahModel mm = masalahModels.get(position);
                Intent i = new Intent(getApplicationContext(), MenuPerbaikan.class );
                i.putExtra(DATAMASALAH, mm);
                startActivity(i);
            }
        });

        loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actionsearch, menu);
        menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                if (TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    private void loadData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, IPADDRESS + "/masalah", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray array = obj.getJSONArray(TAG_RESULT);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        MasalahModel masalahModel = new MasalahModel(
                                object.getString(JRES_IDMASALAH),
                                object.getString(JRES_JAM),
                                object.getString(JRES_TANGGAL),
                                object.getString(JRES_MASALAH),
                                object.getString(JRES_SHIFT),
                                object.getString(JRES_IDMESIN),
                                object.getString(JRES_IDPENGGUNA),
                                object.getString(JRES_NOMESIN),
                                object.getString(JRES_SITE)
                        );
                        masalahModels.add(masalahModel);
                    }
                    adapter = new MasalahAdapter(masalahModels, getApplicationContext());
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == fab){
            Intent i = new Intent(getApplicationContext(), L_Mesin.class );
//                i.putExtra(DATAMESIN, mm);
                startActivity(i);
        }
    }
}
