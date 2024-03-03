package com.devrev.devrevnetworkingmodule

import org.json.JSONObject

interface MyJSONListener {
    fun onResponse(res: JSONObject?)
    fun onFailure(e: Exception?)
}