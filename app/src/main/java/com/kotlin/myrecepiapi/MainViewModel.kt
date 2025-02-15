package com.kotlin.myrecepiapi

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _categoriesState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoriesState

    init {
        featchCategories()
    }
    private fun featchCategories(){
        viewModelScope.launch {
            try {

                val response = retrofit.getCategories()
                _categoriesState.value = _categoriesState.value.copy(
                    list = response.categories,
                    loading = false,
                    error = null
                )

            }catch (exp:Exception){
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error featching categories ${exp.message}",


                )

            }


        }
    }
    data class RecipeState(
        val loading: Boolean = false,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
}