package tg.flexlevrai.shoescollection.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import tg.flexlevrai.shoescollection.MainActivity
import tg.flexlevrai.shoescollection.R

class AddShoesFragment (
    private val context: MainActivity
        ) :Fragment() {

    private  var uploadedImage:ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.fragment_add_shoes, container, false)

        //recuperer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.previewimage)

        //Recuperer le bouton pour charger l`image
        val pickupImageButton = view.findViewById<Button>(R.id.uploadbtn)

        //ouvrir les images du telephone
        pickupImageButton.setOnClickListener { pickupImage() }

        return  view
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Selectionner Une Photo"),47)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 47 && resultCode == Activity.RESULT_OK){

            //verifier si les données son nul
            if(data == null || data.data == null ) return

            //recuperer l`image
            val selectedImage = data.data

            //Mettre a jour l`apercu  de l`image
            uploadedImage?.setImageURI(selectedImage)
        }
    }
}