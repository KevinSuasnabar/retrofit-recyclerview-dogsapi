package pe.kev.com.recyclerviewpractica

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.kev.com.recyclerviewpractica.adapter.DogAdapter
import pe.kev.com.recyclerviewpractica.model.DogResponse
import pe.kev.com.recyclerviewpractica.service.DogsService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var adapter: DogAdapter;
    private val dogImages = mutableListOf<String>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        amSearch.setOnQueryTextListener(this)
        initRecyclerView();

    }

    private fun initRecyclerView() {
        adapter = DogAdapter(dogImages)
        amRecyclerView.layoutManager = LinearLayoutManager(this);
        amRecyclerView.adapter = adapter;
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breed/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    //LLAMADA ASYNCRONA O EN HILO SECUNDARIO
    private fun searchByNameDog(nameDog: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: Response<DogResponse> =
                getRetrofit().create(DogsService::class.java).getDogByBreeds("$nameDog/images");
            val puppies: DogResponse? = call.body();

            //VOLVEMOS AL HILO PRINCIPAL
            runOnUiThread {
                if (call.isSuccessful) {
                    //TODO - > mostrar en recyclerview
                    val images:List<String> = puppies?.images ?: emptyList(); // si no existe el listado no me devolvera null si no una lista vacia
                    dogImages.clear()
                    dogImages.addAll(images);
                    adapter.notifyDataSetChanged();// avisamos al adapter que han habido cambios
                } else {
                    //TODO - > MOSTRAR ERROR
                    showError();
                }

                hideKeyBoard();
            }


        }
    }

    private fun showError(){
        Toast.makeText(this,"Ha ocurrido un error.",Toast.LENGTH_LONG).show();
    }

    /**PARA OCULTAR EL TECLADO CADA VEZ QUE SE BUSCA*/
    private fun hideKeyBoard(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(amConstraintLayout.windowToken, 0)
    }


    /**PARA EL BUSCADOR*/
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchByNameDog(query.toLowerCase());
        }
        return true;
    }
    /**PARA EL BUSCADOR*/
    override fun onQueryTextChange(newText: String?): Boolean {
        return true;
    }


}
