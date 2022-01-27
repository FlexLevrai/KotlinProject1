package tg.flexlevrai.shoescollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tg.flexlevrai.shoescollection.MainActivity
import tg.flexlevrai.shoescollection.R
import tg.flexlevrai.shoescollection.ShoesRepository.Singleton.shoesList
import tg.flexlevrai.shoescollection.adapter.ShoesAdapter
import tg.flexlevrai.shoescollection.adapter.ShoesItemDecoration

class CollectionFragment(
    private  val  context: MainActivity
) : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(R.layout.fragment_collection, container,false)

        //Recuperate la recycler view
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        collectionRecyclerView.adapter = ShoesAdapter(context, shoesList.filter{it.liked}, R.layout.item_vertical_shoes)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(ShoesItemDecoration())

        return view
    }
}