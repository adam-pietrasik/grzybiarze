package pl.grzybiarze.gatherer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pl.grzybiarze.gatherer.R
import pl.grzybiarze.gatherer.data.User

class StatisticItemGridView(
    private val statisticList: List<User>,
    private val context: Context
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private lateinit var statisticNumber: TextView
    private lateinit var statisticName: TextView

    override fun getCount(): Int {
        return statisticList.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView2 = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView2 == null) {
            convertView2 = layoutInflater!!.inflate(R.layout.statistic_item, null)
        }

        statisticNumber = convertView2!!.findViewById(R.id.stat_value)
        statisticName = convertView2!!.findViewById(R.id.stat_name)
        statisticName.text = statisticList[position].email
        statisticNumber.text = statisticList[position].secondName

        return convertView2
    }
}