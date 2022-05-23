package code.hyunwa.githubuserprofile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import code.hyunwa.githubuserprofile.core.data.repository.AppRepository

class MainViewModel(private val repo: AppRepository) : ViewModel() {
    fun getUsers(page: Int?=null, row: Int?=null) = repo.getUsers(page, row).asLiveData()
    fun getUser(username:String) = repo.getUser(username).asLiveData()
}