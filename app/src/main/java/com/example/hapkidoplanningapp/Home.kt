package com.example.hapkidoplanningapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hapkidoplanningapp.domain.Activities
import com.example.hapkidoplanningapp.service.MyActivatiesDAO
import com.example.hapkidoplanningapp.service.activatiesService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : Fragment() {

    private lateinit var rV: RecyclerView
    private lateinit var rVMy: RecyclerView
    private lateinit var dataList: ArrayList<Activities>

   //TODO: be a cheapscate and make schure that it only get update when app is opend

    private lateinit var aS: activatiesService


    private lateinit var myActivatiesDAO: MyActivatiesDAO

    private lateinit var dbLocal: dbLocal


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Move the initialization of aS to onCreateView to avoid null instance
        dataList = ArrayList()

        dbLocal = context as dbLocal

        myActivatiesDAO = dbLocal.getdb().MyActivatiesDAO()


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_activaties, container, false)

        rV = view.findViewById(R.id.recyclerView)
        rVMy = view.findViewById(R.id.recyclerViewMyActivaites)



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

        GlobalScope.launch(Dispatchers.IO) {
            fillRecyclerView()
        }
    }

//    private suspend fun fillRecyclerView() {
//        aS.getActivaties { activitiesList, exception ->
//            if (exception != null) {
//                Log.e("Fragment", "Error getting activities: ${exception.message}")
//            } else {
//                if (activitiesList != null) {
//                    rV.adapter = activatieRViewHolder(activitiesList)
//                } else {
//                    Log.e("Fragment", "Activities list is null")
//                }
//            }
//        }
//        val myActivatiesList = myActivatiesDAO.getAll()
//        if (myActivatiesList != null) {
//            rVMy.adapter = activatieRViewHolder(myActivatiesList)
//        } else {
//            Log.e("Fragment", "My activities list is null")
//        }
//
//    }
    private suspend fun fillRecyclerView() {

        aS.getActivaties { activitiesList, exception ->
            if (exception != null) {
                Log.e("Fragment", "Error getting activities: ${exception.message}")
            } else {
                if (activitiesList != null) {
                    rV.adapter = activatieRViewHolder(activitiesList.toMutableList(), dbLocal)

                } else {
                    Log.e("Fragment", "Activities list is null")
                }
            }
        }
        var myActivatiesList: List<Activities>? = null

        myActivatiesList = myActivatiesDAO.getAll()



    myActivatiesList.let { myActivities ->
        withContext(Dispatchers.Main) {
            rVMy.adapter = activatieRViewHolder(myActivities.toMutableList(), dbLocal, true)
        }
    }
    }


}
