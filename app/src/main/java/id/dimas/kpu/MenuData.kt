package id.dimas.kpu

import id.dimas.kpu.model.Menu

object MenuData {

    private val titleMenu = arrayOf(
        "Informasi",
        "Form Entry",
        "Lihat Data",
        "Keluar"
    )

    private val imageMenu = arrayOf(
        R.drawable.ic_info,
        R.drawable.ic_add,
        R.drawable.ic_view_data,
        R.drawable.ic_logout
    )

    val listData: ArrayList<Menu>
        get() {
            val list = arrayListOf<Menu>()
            for (position in titleMenu.indices) {
                val menu = Menu()
                menu.titleMenu = titleMenu[position]
                menu.imgMenu = imageMenu[position]

                list.add(menu)
            }
            return list
        }
}