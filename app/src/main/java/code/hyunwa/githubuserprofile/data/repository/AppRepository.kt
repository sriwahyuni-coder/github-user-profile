package code.hyunwa.githubuserprofile.core.data.repository

import android.util.Log
import code.hyunwa.githubuserprofile.data.source.remote.RemoteDataSource
import code.hyunwa.githubuserprofile.data.source.remote.network.Resource
import code.hyunwa.githubuserprofile.data.source.remote.response.user.UserDetailResponse
import code.hyunwa.githubuserprofile.data.source.remote.response.user.UserResponseItem
import com.google.gson.Gson
import kotlinx.coroutines.flow.flow
import java.util.ArrayList

class AppRepository(val remote: RemoteDataSource) {

    fun getUsers(page: Int?=null, row: Int?=null) = flow<Resource<ArrayList<UserResponseItem>>> {
        try {
            remote.getUsers(page, row).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    emit(Resource.success(body))
                } else {
                    val message = when(it.code()){
                        403 ->  "403 \n Forbidden"
                        401 -> "403 \n Requires authentication"
                        else -> "Response Error"
                    }
                    emit(Resource.error(message, null))
                }
            }
        } catch (e: Exception) {
            emit(e.message?.let { Resource.error(it, null) }!!)
        }
    }

    fun getUser(username:String)= flow<Resource<UserDetailResponse>> {
        try {
            remote.getUser(username).let {
                if (it.isSuccessful) {
                    val body = it.body()
                    emit(Resource.success(body))
                } else {
                    val message = when(it.code()){
                        403 ->  "403 \n Forbidden"
                        401 -> "403 \n Requires authentication"
                        else -> "Response Error"
                    }
                    emit(Resource.error(message, null))
                }
            }
        } catch (e: Exception) {
            emit(e.message?.let { Resource.error(it, null) }!!)
        }
    }

}