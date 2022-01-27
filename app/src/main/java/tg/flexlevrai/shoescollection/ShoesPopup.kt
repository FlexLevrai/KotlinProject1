package tg.flexlevrai.shoescollection

import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import tg.flexlevrai.shoescollection.adapter.ShoesAdapter

private val Nothing?.context: Any
    get() = Unit

class ShoesPopup(adapter: ShoesAdapter,
                 private val currentShoes: ShoesModel)
    : Dialog(adapter.context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_shoes_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupLikeButton()
    }
    private  fun updateLike(button: ImageView){
        if (currentShoes.liked){
            button.setImageResource(R.drawable.ic_like)
        }else{
            button.setImageResource(R.drawable.ic_unlike)
        }
    }
    private fun setupLikeButton() {
        //recuperer
        val likeButton = findViewById<ImageView>(R.id.ic_unlike)
        updateLike(likeButton)

        //interaction avec la database
        likeButton.setOnClickListener {
            currentShoes.liked = !currentShoes.liked
            val repo = ShoesRepository()
            repo.updateShoes(currentShoes)
            updateLike(likeButton)
        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_btn).setOnClickListener {
            //supprimer la paire
            val repo = ShoesRepository()
            repo.deleteShoes(currentShoes)
            dismiss()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.clobtn).setOnClickListener{
            //fermer la popup
            dismiss()
        }
    }

    private fun setupComponents() {
        //actualiser l`image de la plante
        val shoesImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(context).load(Uri.parse(currentShoes.imageUrl)).into(shoesImage)


        //actualiser le nom de la paire
        findViewById<TextView>(R.id.popup_shoes_name).text = currentShoes.name

        //actualiser la description
        findViewById<TextView>(R.id.popup_shoes_description_title).text = currentShoes.description

        //actualiser la couleur
        findViewById<TextView>(R.id.popup_shoes_color_subtitle).text = currentShoes.color

        //actuliser la size
        findViewById<TextView>(R.id.popup_shoes_size_subtitle).text = currentShoes.size

    }

}
