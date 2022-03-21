package valenzuela.ramses.digimind_app

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import valenzuela.ramses.digimind_app.ui.Tarea
import valenzuela.ramses.digimind_app.ui.home.HomeFragment

class AdaptadorTask: BaseAdapter {
    lateinit var context: Context
    var tasks: ArrayList<Tarea> = ArrayList<Tarea>()

    constructor(context: Context, tasks: ArrayList<Tarea>){
        this.context=context
        this.tasks=tasks
    }

    override fun getCount(): Int {
        return tasks.size
    }

    override fun getItem(p0: Int): Any {
        return tasks[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var inflador = LayoutInflater.from(context)
        var vista = inflador.inflate(R.layout.task_view,null)

        var task = tasks[p0]

        val tv_title: TextView = vista.findViewById(R.id.tv_title)
        val tv_day: TextView = vista.findViewById(R.id.tv_days)
        val tv_time: TextView = vista.findViewById(R.id.tv_time)

        tv_title.setText(task.title)
        tv_day.setText(task.day)
        tv_time.setText(task.time)

        vista.setOnClickListener{
            borrar(task)
        }


        return vista
    }

    fun borrar(task: Tarea){
        val alertDialog: AlertDialog? = context?.let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok_button,
                    DialogInterface.OnClickListener { dialog, id ->
                        HomeFragment.tasks.remove(task)
                        HomeFragment.adaptador.notifyDataSetChanged()
                        guardar_Json()
                        Toast.makeText(context,R.string.msg_deleted, Toast.LENGTH_SHORT).show()
                    })
                setNegativeButton(R.string.cancel_button,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            // Set other dialog properties
            builder?.setMessage(R.string.msg)
                .setTitle(R.string.msg_deleted)
            // Create the AlertDialog
            builder.create()
        }
        alertDialog?.show()
    }


    fun guardar_Json(){
        val preferecias = context?.getSharedPreferences("preferencias", Context.MODE_PRIVATE)
        val editor = preferecias?.edit()

        val gson: Gson = Gson()

        var json = gson.toJson(HomeFragment.tasks)

        editor?.putString("tareas", json)
        editor?.apply()

    }

}