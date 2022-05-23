package code.hyunwa.githubuserprofile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import code.hyunwa.githubuserprofile.data.source.remote.network.State
import code.hyunwa.githubuserprofile.data.source.remote.response.user.UserDetailResponse
import code.hyunwa.githubuserprofile.data.source.remote.response.user.UserResponseItem
import code.hyunwa.githubuserprofile.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener,
    MainAdapter.ItemAdapterCallback {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private val linearLayoutManager = LinearLayoutManager(this)
    private lateinit var userAdapter: MainAdapter
    private var page: Int = 1
    private val row: Int = 10
    private var isLoading: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        rv_user.visibility = View.GONE
        getData(page, row)

        sl_refresh.setOnRefreshListener(this)
    }

    private fun setAdapter() {
        userAdapter = MainAdapter(this)
        with(binding.rvUser) {
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }

        rv_user.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    var vItem = linearLayoutManager.childCount
                    var lItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                    var count = userAdapter.itemCount
                    if (!isLoading) {
                        if (vItem + lItem >= count) {
                            page += count
                            getData(page, row)
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun getData(page: Int?, row: Int?) {
        if (page!! > 1) {
            isLoading = true
            pg_loading.visibility = View.VISIBLE
        }

        viewModel.getUsers(page, row).observe(this, {
            when (it.state) {
                State.LOADING -> {

                }
                State.SUCCESS -> {
                    isLoading = false
                    pg_loading.visibility = View.GONE
                    pg_loading_main.visibility = View.GONE
                    rv_user.visibility = View.VISIBLE
                    tv_error_code.visibility = View.GONE
                    sl_refresh.isRefreshing = false
                    userAdapter.setData(it.data)
                    Log.e("RESP", Gson().toJson(it))
                }

                State.ERROR -> {
                    Log.e("ERR", it.message.toString())
                    isLoading = false
                    sl_refresh.isRefreshing = false
                    tv_error_code.text = it.message
                    pg_loading.visibility = View.GONE
                    pg_loading_main.visibility = View.GONE
                    tv_error_code.visibility = View.VISIBLE
                    rv_user.visibility = View.GONE
                }
            }
        })
    }

    override fun onRefresh() {
        page = 1
        getData(page, row)
    }

    override fun onClick(v: View, data: UserResponseItem) {
        pg_loading_main.visibility = View.VISIBLE
        viewModel.getUser(data.login).observe(this, {
            when (it.state) {
                State.LOADING -> {

                }
                State.SUCCESS -> {
                    pg_loading_main.visibility = View.GONE
                    val data: UserDetailResponse? = it.data
                    if (data != null) {
                        Log.e("SUSS","${data.login} , email: ${data.email} , create_at: ${data.createdAt}")
                        Toast.makeText(this, "${data.login} , email: ${data.email} , create_at: ${data.createdAt}", Toast.LENGTH_LONG).show()
                    }
                }

                State.ERROR -> {
                    pg_loading_main.visibility = View.GONE
                    Log.e("ERR", it.message.toString())
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}