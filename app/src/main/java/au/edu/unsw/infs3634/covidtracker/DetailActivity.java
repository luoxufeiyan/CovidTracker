package au.edu.unsw.infs3634.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    public static final String INTENT_MESSAGE = "au.edu.unsw.infs3634.covidtracker.intent_message";

    private TextView mCountry;
    private TextView mNewCases;
    private TextView mTotalCases;
    private TextView mNewDeaths;
    private TextView mTotalDeaths;
    private TextView mNewRecovered;
    private TextView mTotalRecovered;
    private ImageView mSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mCountry = findViewById(R.id.tvCountry);
        mNewCases = findViewById(R.id.tvNewCases);
        mTotalCases = findViewById(R.id.tvTotalCases);
        mNewDeaths = findViewById(R.id.tvNewDeaths);
        mTotalDeaths = findViewById(R.id.tvTotalDeaths);
        mNewRecovered = findViewById(R.id.tvNewRecovered);
        mTotalRecovered = findViewById(R.id.tvTotalRecovered);
        mSearch = findViewById(R.id.ivSearch);

        Intent intent = getIntent();
        String countryCode = intent.getStringExtra(INTENT_MESSAGE);

        ArrayList<Country> countries = Country.getCountries();
        for(final Country country : countries) {
            if(country.getCountryCode().equals(countryCode)) {
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
            }
        }
    }

    private void searchCountry(String country) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=covid " + country));
        startActivity(intent);
    }
}