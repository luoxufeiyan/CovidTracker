package au.edu.unsw.infs3634.covidtracker;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidService {
    @GET("summary")
    Call<Response> getResponse();
}
