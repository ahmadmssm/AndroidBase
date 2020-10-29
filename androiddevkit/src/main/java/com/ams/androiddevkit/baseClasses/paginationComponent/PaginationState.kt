package com.ams.androiddevkit.baseClasses.paginationComponent

open class PaginationState {
    class Offline : PaginationState()
    class LoadInitial(): PaginationState()
    class LoadBefore: PaginationState()
    class LoadAfter: PaginationState()
    class Loading: PaginationState()
    class Loaded: PaginationState()
    data class ErrorString(val errorMessage: String): PaginationState()
    data class Error(val error: Throwable): PaginationState()
}
