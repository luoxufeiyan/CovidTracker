package au.edu.unsw.infs3634.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private CountryAdapter mAdapter;
    private CountryDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        CountryAdapter.RecyclerViewClickListener listener = new CountryAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, String countryCode) {
                launchDetailActivity(countryCode);
            }
        };

        mAdapter = new CountryAdapter(new ArrayList<Country>(), listener);
        mRecyclerView.setAdapter(mAdapter);
        mDb = Room.databaseBuilder(getApplicationContext(), CountryDatabase.class, "country-database").build();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mAdapter.setData(mDb.countryDao().getCountries());
                mAdapter.sort(CountryAdapter.SORT_METHOD_TOTAL);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.covid19api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        CovidService service = retrofit.create(CovidService.class);
        Call<Response> responseCall = service.getResponse();
        responseCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                List<Country> countries = response.body().getCountries();

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        mDb.countryDao().deleteAll(mDb.countryDao().getCountries().toArray(new Country[0]));
                        mDb.countryDao().insertAll(countries.toArray(new Country[0]));
                    }
                });
                mAdapter.setData(countries);
                mAdapter.sort(CountryAdapter.SORT_METHOD_TOTAL);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference messageRef = database.getReference(FirebaseAuth.getInstance().getUid());
                messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String result = (String) snapshot.getValue();
                        if(result != null) {
                            for(Country home : countries) {
                                if(home.getCountryCode().equals(result)) {
                                    Toast.makeText(MainActivity.this, home.getNewConfirmed() + " new cases in " + home.getCountry(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                log.d(TAG,"")

            }
        });
    }

    private void launchDetailActivity(String message) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_new:
                mAdapter.sort(CountryAdapter.SORT_METHOD_NEW);
                return true;
            case R.id.sort_total:
                mAdapter.sort(CountryAdapter.SORT_METHOD_TOTAL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}