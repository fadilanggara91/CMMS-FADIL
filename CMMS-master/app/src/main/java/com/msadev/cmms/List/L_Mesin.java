package com.msadev.cmms.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.msadev.cmms.Adapter.MesinAdapter;
import com.msadev.cmms.Model.MesinModel;
import com.msadev.cmms.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.msadev.cmms.Util.JsonResponse.JRES_IDMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_KETERANGAN;
import static com.msadev.cmms.Util.JsonResponse.JRES_NOMESIN;
import static com.msadev.cmms.Util.JsonResponse.JRES_SITE;
import static com.msadev.cmms.Util.JsonResponse.TAG_RESULT;
import static com.msadev.cmms.Util.Server.IPADDRESS;

public class L_Mesin extends AppCompatActivity {

    ListView listView;
    List<MesinModel> mesinModelList;
    SwipeRefreshLayout refresh;
    SearchView searchView;
    MenuItem menuItem;
    Menu menu;
    MesinAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        listView = findViewById(R.id.listview);
        mesinModelList = new ArrayList<>();

        refresh = findViewById(R.id.refresh);
        refresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh.setRefreshing(false);
                        mesinModelList.clear();
                        loadData();
                        Toast.makeText(getApplicationContext(), "Data Berhasil diperbarui!", Toast.LENGTH_LONG).show();
                    }
                }, 3000);
            }
        });

//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                MesinModel mesinModel = mesinModelList.get(position);
//                Intent i = new Intent((getApplicationContext(), ))
//            }
//        });

        loadData();

    }

    private void loadData(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, IPADDRESS + "/mesin", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject obj = new JSONObject(response);
                    JSONArray array = obj.getJSONArray(TAG_RESULT);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        MesinModel mesinModel = new MesinModel(
                                object.getString(JRES_IDMESIN),
                                object.getString(JRES_NOMESIN),
                                object.getString(JRES_SITE),
                                object.getString(JRES_KETERANGAN)
                        );
                        mesinModelList.add(mesinModel);
                    }
                    adapter = new MesinAdapter(mesinModelList, getApplicationContext());
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

}
