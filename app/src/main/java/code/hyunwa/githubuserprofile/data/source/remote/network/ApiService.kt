package code.hyunwa.githubuserprofile.data.source.remote.network

import code.hyunwa.githubuserprofile.data.source.remote.response.user.UserDetailResponse
import code.hyunwa.githubuserprofile.data.source.remote.response.user.UserResponseItem
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("since") page:Int? = null,
        @Query("per_page") row : Int?=null,
    ): Response<ArrayList<UserResponseItem>>

    @GET("users/{username}")
    suspend fun getuser(
        @Path("username") username:String
    ) : Response<UserDetailResponse>
}