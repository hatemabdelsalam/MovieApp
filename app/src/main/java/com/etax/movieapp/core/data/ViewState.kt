package com.etax.movieapp.core.data

sealed class ViewState<out T: Any>{
    object Idle: ViewState<Nothing>()
    object Loading: ViewState<Nothing>()
    data class Error(val errorMessage:String): ViewState<Nothing>()
    data class SuccessResult<T:Any>(val result:T): ViewState<T>()
}
