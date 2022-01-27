package tg.flexlevrai.shoescollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tg.flexlevrai.shoescollection.*

class ShoesAdapter(
    val context : MainActivity,
    private  val shoesList : List<ShoesModel>,
    private val layoutId: Int) : RecyclerView.Adapter<ShoesAdapter.ViewHolder>(){

    //Boite pour ranger tout les composants
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        // image de la chaussure
        val shoesImage = view.findViewById<ImageView>(R.id.image_item)
        val shoesName : TextView? = view.findViewById(R.id.name_item)
        val shoesDesc : TextView? = view.findViewById(R.id.description_item)
        val starIcone = view.findViewById<ImageView>(R.id.start_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Recuperer les informations sur la paire de chaussure

        val currenShoes = shoesList[position]

        //Recuperer le repository

        val repo = ShoesRepository()

        //Utiliser glide  pour recuperer l`image a partir de son URL

        Glide.with(context).load(Uri.parse(currenShoes.imageUrl)).into(holder.shoesImage)

        //Mettre a jour le nom de la Chaussure
        holder.shoesName?.text = currenShoes.name

        //Mettre a jour la description de la Chaussure
        holder.shoesDesc?.text = currenShoes.description

        //Vérifier si la chaussure a ete liker
        if (currenShoes.liked){
            holder.starIcone.setImageResource(R.drawable.ic_like)
        }
        else{
            holder.starIcone.setImageResource(R.drawable.ic_unlike)
        }

        //rajouter une interaction sur cette etoile
        holder.starIcone.setOnClickListener {
            //inversé si le bouton est like ou pas
            currenShoes.liked = !currenShoes.liked
            //mettre a jours l`objet shoes

                repo.updateShoes(currenShoes    )
        }
        //interaction lors du clic sur une paire
        holder.itemView.setOnClickListener {
            //afficher popup
            ShoesPopup(this, currenShoes).show()
        }
    }
        // Nombre d`element a afficher dynamiquement
//    override fun getItemCount(): Int {
//        return 5
//    }

    //deux facon de faire
        override fun getItemCount(): Int = shoesList.size

}