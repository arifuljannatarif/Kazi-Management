package com.example.kazimanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    ShortlistAdapter adapter;
    Spinner spinner;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView=findViewById(R.id.recyclerview_shortlist);
        adapter=new ShortlistAdapter(this);
        recyclerView.setAdapter(adapter);
        refreshLayout=findViewById(R.id.swiperefresh);
        progressBar=findViewById(R.id.progressbar);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
               try{
                   loadentries(spinner.getSelectedItemPosition());
               }catch (Exception es){

               }
            }
        });
        findViewById(R.id.btn_addnew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Add_new_details.class));
            }
        });
        spinner=findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        loadentries(0);
                        break;
                    case 1:
                        loadentries(1);
                        break;
                    case 2:
                        loadentries(2);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        loadentries(0);
    }

    private void loadentries(int type) {
        progressBar.setVisibility(View.VISIBLE);
        CollectionReference ref= FirebaseFirestore.getInstance().collection(UserUtils.getuseremail());
        Query query=ref.orderBy("date", Query.Direction.DESCENDING);
        switch (type){
            case 1:
                query = ref.orderBy("date", Query.Direction.DESCENDING).whereGreaterThan("date",System.currentTimeMillis());
                break;
            case 2:
                query = ref.orderBy("date", Query.Direction.DESCENDING).whereLessThanOrEqualTo("date",System.currentTimeMillis());
                break;
        }
      try{
          query.addSnapshotListener(MainActivity.this, new EventListener<QuerySnapshot>() {
              @Override
              public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                  if(e!=null){
                      Toast.makeText(MainActivity.this,"Error Loading data"+e.getMessage(),Toast.LENGTH_SHORT).show();

                  }else{
                      adapter.clearall();
                      for(QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                          MarriageModel model=snapshot.toObject(MarriageModel.class);
                          model.setIdNo(snapshot.getId());
                          adapter.additem(model,model.getDate());
                      }
                      adapter.notifyDataSetChanged();
                      progressBar.setVisibility(View.GONE);
                      refreshLayout.setRefreshing(false);
                      try{
                          Toast.makeText(MainActivity.this,"Data Updated",Toast.LENGTH_SHORT).show();
                      }catch (Exception ecc){
                      }
                   //    Snackbar.make(getCurrentFocus().getRootView(),"Data Updated",Snackbar.LENGTH_SHORT).show();
                  }

              }
          });
      }catch (Exception ex){
          ex.printStackTrace();
      }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shortlist_menu,menu);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
            search.setQueryHint("Name/serial/mobile/book no");

            search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

            search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    //adapter.filter(query);
                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.filter(newText);
                    return true;
                }
            });

        }
        return super.onCreateOptionsMenu(menu);
    }
}
