package fr.xkgd.marieteam.api;

public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;

    /**
     * Méthode permettant de savoir si la requête a réussi
     * @return true si la requête a réussi, false sinon
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Méthode permettant de récupérer le message de la requête
     * @return le message de la requête
     */
    public String getMessage() {
        return message;
    }

    /**
     * Méthode permettant de récupérer les données de la requête
     * @return les données de la requête de manière générique
     */
    public T getData() {
        return data;
    }
}
