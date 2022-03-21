package valenzuela.ramses.digimind_app.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import valenzuela.ramses.digimind_app.AdaptadorTask
import valenzuela.ramses.digimind_app.databinding.FragmentHomeBinding
import valenzuela.ramses.digimind_app.ui.Tarea

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    companion object{
        var tasks: ArrayList<Tarea> = ArrayList<Tarea>()
        var first = true
        lateinit var adaptador: AdaptadorTask
    }


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val gridView: GridView = binding.gridview

        cargar_tareas()

        adaptador = AdaptadorTask(root.context, tasks)

        gridView.adapter = adaptador
        return root
    }


    fun cargar_tareas(){
        val preferecias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val gson: Gson = Gson()

        var json = preferecias?.getString("tareas", null)

        val type = object : TypeToken<ArrayList<Tarea?>?>(){}.type

        if (json == null){
            tasks = ArrayList<Tarea>()
        }else{
            tasks = gson.fromJson(json,type)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}