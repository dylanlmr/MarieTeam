package fr.xkgd.marieteam.api.service;


import fr.xkgd.marieteam.api.ApiResponse;
import fr.xkgd.marieteam.model.Capitaine;
import fr.xkgd.marieteam.model.LoginRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CapitaineService {

    /**
     * Récupère un capitaine par son id
     * @param id id du capitaine
     * @return le capitaine
     */
    @GET("capitaine/{id}")
    Call<ApiResponse<Capitaine>> getCapitaine(@Path("id") int id);

    /**
     * Récupère un capitaine par son email
     * @param loginRequest email du capitaine
     * @return le capitaine
     */
    @POST("capitaine/login")
    Call<ApiResponse<Capitaine>> login(@Body LoginRequest loginRequest);
}
