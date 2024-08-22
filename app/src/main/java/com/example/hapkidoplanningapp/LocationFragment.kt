package com.example.hapkidoplanningapp

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.hapkidoplanningapp.domain.Activities
import com.example.hapkidoplanningapp.domain.RepeatingActivates
import com.example.hapkidoplanningapp.service.MyActivatiesDAO
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date


class LocationFragment : Fragment(),RVUListener {

    private lateinit var rVMy: RecyclerView
    private lateinit var rVRv: RecyclerView

    private lateinit var myActivatiesDAO: MyActivatiesDAO
    private lateinit var dbLocal: dbLocal

    private lateinit var uP: UserProvider
    private var navbarProvider: NavbarProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save the state here
       
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rVMy = view.findViewById(R.id.recyclerViewMyActivaites)
        rVRv = view.findViewById(R.id.recyclerViewStanderds)

        navbarProvider?.getBottomNav()?.visibility = View.VISIBLE
        dbLocal = context as dbLocal

        myActivatiesDAO = dbLocal.getdb().MyActivatiesDAO()

        val repeatingActivatiesList = returnRepeatList()
        rVRv.adapter = ActivatieRepeaterRViewAdapter(repeatingActivatiesList)
        fetchData()

    }
    @SuppressLint("NotifyDataSetChanged")
    override fun updateRV(){
        fetchData()
    }

    override fun switchDetailframe(activatie: Activities) {
        val detailActivatie = DetailActivatie.newInstance(activatie)

        view?.findViewById<FrameLayout>(R.id.fameDetail)?.let { container ->
            childFragmentManager.beginTransaction().apply {
                replace(container.id, detailActivatie)
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is NavbarProvider) {
            navbarProvider = context
        }
        if (context is UserProvider) {
            uP = context
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun fetchData() {
        // Fetch Firebase data
        GlobalScope.launch(Dispatchers.Main) {
            try {

                val myActivitiesList = withContext(Dispatchers.IO) { myActivatiesDAO.getAll() }
                rVMy?.adapter = activatieRViewHolder(myActivitiesList.toMutableList(), dbLocal, activity,this@LocationFragment, uP.getUser(), true )
            } catch (e: Exception) {
                Log.e("Home", "Error fetching data: ${e.message}")
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun returnRepeatList(): List<RepeatingActivates>{
        val repeatingActivatiesList = mutableListOf<RepeatingActivates>()
        repeatingActivatiesList.add(RepeatingActivates(createDateWithTime(18,30), "alle vrijdagen", "kindere Hapkido les", "behalven op feesdagen", "lanaken" ))
        repeatingActivatiesList.add(RepeatingActivates(createDateWithTime(19,45),"alle vrijdagen", "volwassenen Hapkido les", "behalven op feesdagen", "lanaken" ))
        return repeatingActivatiesList
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun createDateWithTime(hours: Int, minutes: Int): Date {
        val localDateTime = LocalDateTime.now()
            .withHour(hours)
            .withMinute(minutes)
            .withSecond(0)
            .withNano(0)
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant())
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            LocationFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}