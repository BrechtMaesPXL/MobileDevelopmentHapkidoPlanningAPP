package com.example.hapkidoplanningapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hapkidoplanningapp.domain.User
import com.example.hapkidoplanningapp.service.loginService
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class UserFragment : Fragment() {

    private lateinit var navbarProvider: NavbarProvider
    private lateinit var name: TextView
    private lateinit var eMail: TextView
    private lateinit var belt: TextView
    private lateinit var auth: FirebaseAuth

    private  var currentUser: User? = User("null", "null")

    private lateinit var lS: loginService

    private lateinit var uP: UserProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Any required initialization
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navbarProvider.getBottomNav().visibility = View.VISIBLE

        name = view.findViewById(R.id.nameView)
        eMail = view.findViewById(R.id.eMailView)
        belt = view.findViewById(R.id.beltView)
        auth = Firebase.auth
        lS = loginService()

        currentUser = uP.getUser()

        fillFields()
        setupButton()
    }

    private fun setupButton() {
        val regestrationButton = view?.findViewById<Button>(R.id.RegestrationButton)
        val logoutButton = view?.findViewById<Button>(R.id.logoutButton)
        val studentButton = view?.findViewById<Button>(R.id.showStudentsButton)
        if(uP.getUser()?.isTrainer == true) {
            regestrationButton?.setOnClickListener {
                val fragment = Regestration.newInstance()
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.container, fragment)
                    addToBackStack("User")
                    commit()
                }
            }
            studentButton?.setOnClickListener {
                val fragment3 = trainerUserFragment.newInstance()
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.container, fragment3)
                    addToBackStack("student")
                    commit()
                }
            }
        } else {
            regestrationButton?.visibility = View.GONE
            studentButton?.visibility = View.GONE

        }

        logoutButton?.setOnClickListener {
            val fragment2 = Login.newInstance()
            lS.logout()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.container, fragment2)
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
    private fun fillFields() {
        name.text = currentUser?.name
        eMail.text = currentUser?.eMail
        belt.text = currentUser?.belt.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}