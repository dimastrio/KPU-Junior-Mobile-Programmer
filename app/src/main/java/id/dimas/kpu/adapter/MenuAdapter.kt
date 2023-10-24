package id.dimas.kpu.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import id.dimas.kpu.model.Menu
import id.dimas.kpu.R

class MenuAdapter(
    private val context: Activity,
    private val list: ArrayList<Menu>
) : ArrayAdapter<Menu>(context, R.layout.item_menu, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView
        val listData = getItem(position)

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false)
        }

        val listImage = view!!.findViewById<ImageView>(R.id.iv_menu)
        val listTitle = view.findViewById<TextView>(R.id.tv_menu)

        listImage?.setImageResource(listData!!.imgMenu)
        listTitle?.text = listData!!.titleMenu

        return view

    }

}