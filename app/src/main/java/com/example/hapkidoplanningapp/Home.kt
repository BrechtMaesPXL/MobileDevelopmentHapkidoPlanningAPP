package com.example.hapkidoplanningapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hapkidoplanningapp.service.MyActivatiesDAO
import com.example.hapkidoplanningapp.service.activatiesService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : Fragment() {

    private lateinit var rV: RecyclerView
    private lateinit var rVMy: RecyclerView

    private lateinit var aS: activatiesService
    private lateinit var myActivatiesDAO: MyActivatiesDAO
    private lateinit var dbLocal: dbLocal

    private lateinit var laudingAnimation: RelativeLayout
    private var navbarProvider: NavbarProvider? = null


    private lateinit var frame: FrameLayout


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_activaties, container, false)
        rV = view.findViewById(R.id.recyclerView)
        rVMy = view.findViewById(R.id.recyclerViewMyActivaites)
        laudingAnimation = view.findViewById(R.id.loadingPanel)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            frame = view.findViewById(R.id.fameDetail)
            orientaionLandscapeSetup()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navbarProvider?.getBottomNav()?.visibility = View.VISIBLE


        setupAddActivitieButton()

        dbLocal = context as dbLocal
        myActivatiesDAO = dbLocal.getdb().MyActivatiesDAO()
        aS = activatiesService()

        fetchData()

    }

    private fun setupAddActivitieButton() {
        val addActivetieButton = view?.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        addActivetieButton?.setOnClickListener {
            val fragment = fragment_add_activetie.newInstance()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                setHasOptionsMenu(false)
                replace(R.id.container, fragment)
                addToBackStack("Home")
                commit()
            }
        }
    }
    private fun orientaionLandscapeSetup(){

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateRV(){
        rV.adapter?.notifyDataSetChanged()
        rVMy.adapter?.notifyDataSetChanged()
    }
    fun showToast(message: String){
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }


    //Todo: make it so I can update the resycler view via the adpater
    // and something els I just forgot
    private fun fillRecyclerView() {
            aS.getActivaties { activitiesList, exception ->
                if (exception != null) {
                    Log.e("Fragment", "Error getting activities: ${exception.message}")
                } else {
                    if (activitiesList != null) {
                        rV.adapter = activatieRViewHolder(activitiesList.toMutableList() , dbLocal,  activity, this)
                        laudingAnimation.setVisibility(View.GONE);

                    } else {
                        Log.e("Fragment", "Activities list is null")
                    }
                }
            }
        }

    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchData() {
        // Fetch Firebase data
        GlobalScope.launch(Dispatchers.Main) {
            try {
                fillRecyclerView()

                val myActivitiesList = withContext(Dispatchers.IO) { myActivatiesDAO.getAll() }
                rVMy.adapter = activatieRViewHolder(myActivitiesList.toMutableList(), dbLocal, activity,this@Home, true )
            } catch (e: Exception) {
                Log.e("Home", "Error fetching data: ${e.message}")
            }
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavbarProvider) {
            navbarProvider = context
        }
    }



}

