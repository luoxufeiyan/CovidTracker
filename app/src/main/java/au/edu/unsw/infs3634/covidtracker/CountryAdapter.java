package au.edu.unsw.infs3634.covidtracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {
    private ArrayList<Country> mCountries;
    private RecyclerViewClickListener mListener;

    public CountryAdapter(ArrayList<Country> countries, RecyclerViewClickListener listener) {
        mCountries = countries;
        mListener = listener;
    }

    public interface RecyclerViewClickListener {
        void onClick(View view, String countryCode);
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_row, parent, false);
        return new CountryViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {
        Country country = mCountries.get(position);
        DecimalFormat df = new DecimalFormat( "#,###,###,###" );
        holder.country.setText(country.getCountry());
        holder.totalCases.setText(df.format(country.getTotalConfirmed()));
        holder.newCases.setText("+" + df.format(country.getNewConfirmed()));
        holder.itemView.setTag(country.getCountryCode());
    }

    @Override
    public int getItemCount() {
        return mCountries.size();
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView country, totalCases, newCases;
        private RecyclerViewClickListener listener;

        public CountryViewHolder(@NonNull View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            this.listener = listener;
            itemView.setOnClickListener(this);
            country = itemView.findViewById(R.id.tvCountry);
            totalCases = itemView.findViewById(R.id.tvTotalCases);
            newCases = itemView.findViewById(R.id.tvNewCases);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, (String) v.getTag());
        }
    }
}
