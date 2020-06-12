package com.ams.jwt

import org.json.JSONObject

class JwtWrapper(decodedJwtString: String): JSONObject(decodedJwtString) {
    val exp: String? = this.optString("exp")
}