package code.hyunwa.githubuserprofile.data.source.remote

import code.hyunwa.githubuserprofile.data.source.remote.network.ApiService


class RemoteDataSource(private val api: ApiService) {
    suspend fun getUsers(page: Int?=null, row: Int?=null) = api.getUsers(page, row)
    suspend fun getUser(username : String) = api.getuser(username)

}