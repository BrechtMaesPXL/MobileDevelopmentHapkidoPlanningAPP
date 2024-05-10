package com.example.hapkidoplanningapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hapkidoplanningapp.domain.Activities
import com.example.hapkidoplanningapp.service.activatiesService
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_activaties.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_activaties : Fragment() {

    private lateinit var rV: RecyclerView
    private lateinit var dataList: ArrayList<Activities>
   //TODO: be a cheapscate and make schure that it only get update when app is opend
    private lateinit var aS: activatiesService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Move the initialization of aS to onCreateView to avoid null instance
        dataList = ArrayList()
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
            Log.w("myapp", "going into listener")
            // Instantiate the fragment
            val fragment = fragment_add_activetie.newInstance()

            // Replace the current fragment with the new fragment
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BackscreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
