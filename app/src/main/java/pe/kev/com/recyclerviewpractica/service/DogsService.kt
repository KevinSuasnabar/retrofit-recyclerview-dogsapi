package pe.kev.com.recyclerviewpractica.service

import pe.kev.com.recyclerviewpractica.model.DogResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface DogsService {

    @GET
    suspend fun getDogByBreeds(@Url url: String): Response<DogResponse> // se le agrega suspend porque es una llamada asyncrona
}