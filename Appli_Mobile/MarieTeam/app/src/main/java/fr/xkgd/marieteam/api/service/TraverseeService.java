package fr.xkgd.marieteam.api.service;

import java.util.List;

import fr.xkgd.marieteam.api.ApiResponse;
import fr.xkgd.marieteam.model.Traversee;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TraverseeService {

    /**
     * Récupère toutes les traversées d'un capitaine
     * @param id id du capitaine
     * @return ApiResponse<List<Traversee>>
     */
    @GET("traversee/capitaine/{id}")
    Call<ApiResponse<List<Traversee>>> getCapitaineTraversees(@Path("id") int id);

    /**
     * Modifie toutes les traversées d'un bateau
     * @param traversees liste des traversées à modifier
     * @return ApiResponse<List<Traversee>>
     */
    @PUT("traversee/edit")
    Call<ApiResponse<Void>> updateAll(@Body List<Traversee> traversees);
}
