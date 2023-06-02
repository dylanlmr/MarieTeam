package fr.xkgd.marieteam.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private final Retrofit apiBuilder;

    private RetrofitClient() {
        String baseUrl = "http://10.0.2.2/marieteam/";

        apiBuilder = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Singleton pour avoir une seule instance de RetrofitClient
     * @return instance de RetrofitClient
     */
    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    /**
     * Création d'un service
     * @param service classe du service
     * @param <T> type du service
     * @return service
     */
    public <T> T create(Class<T> service) {
        return apiBuilder.create(service);
    }
}
