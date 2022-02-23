package com.alab.oauthwithvk_example

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * Определяет модель представления фрагмента 'Инфо'.
 */
class InfoViewModel: ViewModel() {
    private val _count = MutableLiveData<String>()
    private val _friends = MutableLiveData<String>()

    /**
     * Возвращает кол-во друзей.
     */
    val count: LiveData<String> = _count

    /**
     * Возвращет список друзей.
     */
    val friends: LiveData<String> = _friends

    init {
        viewModelScope.launch {
            val response = App.application.retrofit.create(FriendsGetRequest::class.java).friendsGet(
                App.application.accountService.token!!, "5.131", "name"
            )
            val friendsList = StringBuilder()
            val items = JSONObject(response).getJSONObject("response").getJSONArray("items")
            for (i in 0 until items.length()) {
                friendsList.append(
                    "${items.getJSONObject(i).getString("first_name")} ${items.getJSONObject(i).getString("last_name")}\n"
                )
            }
            _count.postValue(JSONObject(response).getJSONObject("response").getString("count"))
            _friends.postValue(friendsList.toString())
        }
    }
}