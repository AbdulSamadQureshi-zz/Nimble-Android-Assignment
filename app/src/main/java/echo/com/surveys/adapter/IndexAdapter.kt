package echo.com.surveys.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import echo.com.surveys.R
import echo.com.surveys.model.SurveyModel

class IndexAdapter(internal var indexList: MutableList<SurveyModel>) :
    RecyclerView.Adapter<IndexAdapter.IndexViewHolder>() {

    override fun onBindViewHolder(holder: IndexViewHolder, position: Int) {
        holder.bindData(indexList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndexViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return IndexViewHolder(view)
    }



    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_item_indexer
    }


    override fun getItemCount(): Int {
        return indexList.size
    }

    fun addItems(surveyList: List<SurveyModel>?) {
        if (surveyList != null && surveyList.size > 0) {
            indexList.clear()
            indexList.addAll(surveyList)
            notifyDataSetChanged()
        }
    }

    inner class IndexViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.indexer)
        }

        fun bindData(survey: SurveyModel) {
            imageView.setImageResource(if (survey.isSelected) R.drawable.shape_circle_filled else R.drawable.shape_circle_empty)
        }
    }

    fun getIndexList(): List<SurveyModel> {
        return indexList
    }
}

