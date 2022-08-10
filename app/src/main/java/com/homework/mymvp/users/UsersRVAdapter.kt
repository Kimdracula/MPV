package com.homework.mymvp.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.homework.mymvp.databinding.RecycleItemBinding

class UsersRVAdapter(private val presenter: IUserListPresenter) :
    RecyclerView.Adapter<UsersRVAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(RecycleItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)).apply {
            itemView.setOnClickListener { presenter.itemClickListener?.invoke(this) }
        }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  =
        presenter.bindView(holder.apply { pos = position })


    override fun getItemCount(): Int
            = presenter.getCount()


    inner class ViewHolder(val vb: RecycleItemBinding) : RecyclerView.ViewHolder(vb.root),
        UserItemView {
        override fun setLogin(text: String) {
            with(vb) {
                tvLogin.text = text
            }
        }

        override var pos: Int = -1

    }

}