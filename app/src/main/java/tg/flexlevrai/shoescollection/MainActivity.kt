package tg.flexlevrai.shoescollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tg.flexlevrai.shoescollection.fragments.AddShoesFragment
import tg.flexlevrai.shoescollection.fragments.CollectionFragment
import tg.flexlevrai.shoescollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Charger notre ShoesRepository
        val repo = ShoesRepository()

        //Mettre a jour les données
        repo.updateData{
            // injection du fragment dans la boite (fragment_container)

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container,AddShoesFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
