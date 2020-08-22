package au.edu.unsw.infs3634.covidtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {
    public static final String INTENT_MESSAGE = "au.edu.unsw.infs3634.covidtracker.intent_message";

    private CountryDatabase mDb;
    private ImageView mFlag;
    private String mCountryCode;
    private TextView mCountry;
    private TextView mNewCases;
    private TextView mTotalCases;
    private TextView mNewDeaths;
    private TextView mTotalDeaths;
    private TextView mNewRecovered;
    private TextView mTotalRecovered;
    private ImageView mSearch;
    private CheckBox mHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mFlag = findViewById(R.id.ivFlag);
        mCountry = findViewById(R.id.tvCountry);
        mNewCases = findViewById(R.id.tvNewCases);
        mTotalCases = findViewById(R.id.tvTotalCases);
        mNewDeaths = findViewById(R.id.tvNewDeaths);
        mTotalDeaths = findViewById(R.id.tvTotalDeaths);
        mNewRecovered = findViewById(R.id.tvNewRecovered);
        mTotalRecovered = findViewById(R.id.tvTotalRecovered);
        mSearch = findViewById(R.id.ivSearch);
        mHome = findViewById(R.id.cbHome);

        Intent intent = getIntent();
        mCountryCode = intent.getStringExtra(INTENT_MESSAGE);

        Glide.with(this)
                .load("https://www.countryflags.io/" + mCountryCode.toLowerCase() + "/flat/64.png")
                .fitCenter()
                .into((ImageView) mFlag);

        mDb = Room.databaseBuilder(getApplicationContext(), CountryDatabase.class, "country-database").build();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Country country = mDb.countryDao().getCountry(mCountryCode);
                DecimalFormat df = new DecimalFormat( "#,###,###,###" );
                setTitle(country.getCountryCode());
                mCountry.setText(country.getCountry());
                mNewCases.setText(df.format(country.getNewConfirmed()));
                mTotalCases.setText(df.format(country.getTotalConfirmed()));
                mNewDeaths.setText(df.format(country.getNewDeaths()));
                mTotalDeaths.setText(df.format(country.getTotalDeaths()));
                mNewRecovered.setText(df.format(country.getNewRecovered()));
                mTotalRecovered.setText(df.format(country.getTotalRecovered()));
                mSearch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchCountry(country.getCountry());
                    }
                });
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference messageRef = database.getReference(FirebaseAuth.getInstance().getUid());
                messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String result = (String) snapshot.getValue();
                        if(result != null && result.equals(country.getCountryCode())) {
                            mHome.setChecked(true);
                        } else {
                            mHome.setChecked(false);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                mHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        DatabaseReference messageRef = database.getReference(FirebaseAuth.getInstance().getUid());
                        if(isChecked) {
                            messageRef.setValue(country.getCountryCode());
                        } else {
                            messageRef.setValue("");
                        }
                    }
                });
            }
        });

    }

    private void searchCountry(String country) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=covid " + country));
        startActivity(intent);
    }
}