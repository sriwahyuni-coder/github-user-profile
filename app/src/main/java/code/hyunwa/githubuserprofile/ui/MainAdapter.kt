package code.hyunwa.githubuserprofile.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import code.hyunwa.githubuserprofile.R
import code.hyunwa.githubuserprofile.data.source.remote.response.user.UserResponseItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_user.view.*

class MainAdapter(
    private  var itemAdapterCallback: ItemAdapterCallback) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var listData =  ArrayList<UserResponseItem>()

    fun setData(newListData: List<UserResponseItem>?){
        if (newListData == null) return
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position], itemAdapterCallback)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: UserResponseItem, itemAdapterCallback: ItemAdapterCallback) {
            itemView.apply {
                tv_username.text = data.login
                tv_id.text = "Id: ${data.id}"
                tv_repo.text = "Repo Url: ${data.reposUrl}"

                Glide.with(context)
                    .load(data.avatarUrl)
                    .into(iv_user)

                itemView.setOnClickListener { itemAdapterCallback.onClick(it, data) }
            }
        }

    }

    fun clear() {
        listData.clear()
        notifyDataSetChanged()
    }

    // Add a list of items -- change to type used
    fun addAll(list: List<UserResponseItem>) {
        listData.addAll(list)
        notifyDataSetChanged()
    }

    interface ItemAdapterCallback {
        fun onClick(v: View, data: UserResponseItem)
    }
}