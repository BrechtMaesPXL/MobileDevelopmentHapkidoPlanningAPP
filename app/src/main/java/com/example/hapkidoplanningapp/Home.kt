package com.example.hapkidoplanningapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hapkidoplanningapp.db.LocalDataBase
import com.example.hapkidoplanningapp.domain.Activities
import com.example.hapkidoplanningapp.service.MyActivatiesDAO
import com.example.hapkidoplanningapp.service.activatiesService
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Home : Fragment() {

    private lateinit var rV: RecyclerView
    private lateinit var dataList: ArrayList<Activities>
   //TODO: be a cheapscate and make schure that it only get update when app is opend
    private lateinit var aS: activatiesService
    private lateinit var myActivatiesDAO: MyActivatiesDAO



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Move the initialization of aS to onCreateView to avoid null instance
        dataList = ArrayList()

        val db = Room.databaseBuilder(
            requireContext(),
            LocalDataBase::class.java, "mudori-localdb"
        ).build()

        myActivatiesDAO = db.MyActivatiesDAO()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_activaties, container, false)
        rV = view.findViewById(R.id.recyclerView)

        aS = activatiesService()

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addActivetieButton = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        addActivetieButton.setOnClickListener {
            val fragment = fragment_add_activetie.newInstance()

            activity?.supportFragmentManager?.beginTransaction()?.apply {
                setHasOptionsMenu(false)
                replace(R.id.container, fragment)
                addToBackStack("Home")
                commit()
            }

        }

        fillRescylerView()
    }

    private fun fillRescylerView() {
        aS.getActivaties { activitiesList, exception ->
            if (exception != null) {
                Log.e("Fragment", "Error getting activities: ${exception.message}")
            } else {
                if (activitiesList != null) {
                    rV.adapter = activatieRViewHolder(activitiesList)
                } else {
                    Log.e("Fragment", "Activities list is null")
                }
            }
        }

    }


}
