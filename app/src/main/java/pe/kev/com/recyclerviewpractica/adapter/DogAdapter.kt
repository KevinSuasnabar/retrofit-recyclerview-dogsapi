package pe.kev.com.recyclerviewpractica.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_dog.view.*
import pe.kev.com.recyclerviewpractica.R
import pe.kev.com.recyclerviewpractica.model.DogResponse

class DogAdapter(val images: List<String>) : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(element: String) {
            Picasso.get().load(element).into(itemView.idCard);

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context);
        return DogViewHolder(layoutInflater.inflate(R.layout.item_dog, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size;
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val item: String = images[position];
        holder.bind(item)
    }
}