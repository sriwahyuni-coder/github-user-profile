package code.hyunwa.githubuserprofile.data.source.remote.network

import code.hyunwa.githubuserprofile.data.source.remote.response.user.UserResponseItem
import retrofit2.Response
import retrofit2.http.*


interface ApiService {
    @Headers("Authorization: token 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")
    @GET("users")
    suspend fun getUsers(
        @Query("since") page:Int? = null,
        @Query("per_page") row : Int?=null,
    ): Response<ArrayList<UserResponseItem>>
}