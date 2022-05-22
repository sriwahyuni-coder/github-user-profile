package code.hyunwa.githubuserprofile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import code.hyunwa.githubuserprofile.data.source.remote.network.State
import code.hyunwa.githubuserprofile.data.source.remote.response.user.UserResponseItem
import code.hyunwa.githubuserprofile.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, MainAdapter.ItemAdapterCallback {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : MainViewModel by viewModel()
    private val linearLayoutManager = LinearLayoutManager(this)
    private lateinit var userAdapter : MainAdapter
    private var page:Int = 1
    private val row: Int = 10
    private var isLoading : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()
        getData(page, row)

        sl_refresh.setOnRefreshListener(this)
    }

    private fun setAdapter(){
        userAdapter = MainAdapter(this)
        with(binding.rvUser) {
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }

        rv_user.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if(dy > 0){
                    var vItem =linearLayoutManager.childCount
                    var lItem = linearLayoutManager.findFirstCompletelyVisibleItemPosition()
                    var count = userAdapter.itemCount

                    if(!isLoading) {
                        if (vItem + lItem >= count) {
                            page +=1
                            getData(page,row)
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun getData(page: Int?, row:Int?){
        if(page!! > 1) {
            isLoading = true
            pg_loading.visibility = View.VISIBLE
        }
        viewModel.getUsers(page, row).observe(this,{
            when(it.state){
                State.LOADING -> {

                }
                State.SUCCESS ->{
                    isLoading = false
                    pg_loading.visibility = View.GONE
                    sl_refresh.isRefreshing = false
                    userAdapter.setData(it.data)
                }

                State.ERROR -> {
                    isLoading = false
                    pg_loading.visibility = View.GONE
                    sl_refresh.isRefreshing = false
                }
            }
        })
    }

    override fun onRefresh() {
        page = 1
        getData(page, row)
    }

    override fun onClick(v: View, data: UserResponseItem) {
        Toast.makeText(this, "$data.login email: sriw6813@gmail.com ", Toast.LENGTH_LONG ).show()
    }
}