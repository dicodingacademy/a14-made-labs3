package com.dicoding.picodiploma.myasynctask

internal interface MyAsyncCallback {
    fun onPreExecute()

    fun onPostExecute(text: String?)
}
