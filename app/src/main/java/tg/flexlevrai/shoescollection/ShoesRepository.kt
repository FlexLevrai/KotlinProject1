package tg.flexlevrai.shoescollection
import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import tg.flexlevrai.shoescollection.ShoesRepository.Singleton.databaseRef
import tg.flexlevrai.shoescollection.ShoesRepository.Singleton.shoesList
import tg.flexlevrai.shoescollection.ShoesRepository.Singleton.storageReference
import java.net.URI
import java.util.*

class ShoesRepository {

    object Singleton {
        //donner le lien pour acceder au bucket
        private val BUCKET_URL: String="gs://shoescollection-862da.appspot.com"

        //se conecter a l`espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        // Se connecter a la reference "shoes"
        val databaseRef = FirebaseDatabase.getInstance("https://shoescollection-862da-default-rtdb.firebaseio.com/").getReference("shoes")

        //Creer une liste qui va contenir nos Chaussures
        val  shoesList = arrayListOf<ShoesModel>()

    }
    fun updateData(callback: () -> Unit){
        //absorber  les données depuis la databaseRef -> listes des chaussures

        databaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot : DataSnapshot) {
                //retirer les anciennes chaussures
                shoesList.clear()
                //recolter la liste
                for (ds in snapshot.children)

                //Actionner le callback
                callback()
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    //creer une fonction pour envoyer des fichiers sur le storage
    fun uploadImage(file: Uri): Task<Uri> {
        //verifier qu`il est pas null
        if(file != null){
            val fileName = UUID.randomUUID().toString() + ".jpg"
            val ref = storageReference.child(fileName)
            val uploadTask = ref.putFile(file)


            //demarrer la tache d`envoi
            val addOnCompleteListener =
                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<URI>> { task ->

                    //Verifier  s`il y a eu un probleme
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }

                   return@Continuation ref.downloadUrl

                }).addOnCompleteListener { task ->
                    //verifier  si tout va bien
                    if (task.isSuccessful) {
                        //recuperer l`image
                        val downloadURI = task.result

                    }
                }
        }
    }

    //mettre a jours un objet shoes in database
    fun updateShoes(shoes:ShoesModel) = databaseRef.child(shoes.id).setValue(shoes)

    //Supprimer une paire de la base
    fun  deleteShoes(shoes: ShoesModel) =  databaseRef.child(shoes.id).removeValue()
}