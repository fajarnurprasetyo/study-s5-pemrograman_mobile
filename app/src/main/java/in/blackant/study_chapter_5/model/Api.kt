package `in`.blackant.study_chapter_5.model

import android.content.Context
import android.util.Log
import com.android.volley.ClientError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.concurrent.ExecutionException

class Api(val context: Context) {
    companion object {
        private const val BASE_URL = "https://1122100007-mobile.vercel.app/api"
    }

    private var queue = Volley.newRequestQueue(context)

    private fun <T> send(
        method: Int,
        endpoint: String,
        cls: Class<T>,
        params: Map<String, String> = mapOf(),
        body: T? = null,
    ): T? {
        val listener = RequestFuture.newFuture<T>()
        val urlWithParams = "$BASE_URL$endpoint?${
            params.map { param -> "${param.key}=${param.value}" }.joinToString("&")
        }"
        val req = GsonRequest(method, urlWithParams, cls, body, listener)
        req.retryPolicy = DefaultRetryPolicy(
            30 * 1000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        queue.add(req)
        try {
            return listener.get()
        } catch (error: ExecutionException) {
            if (error.cause is VolleyError) {
                val volleyError = error.cause as VolleyError
                try {
                    val errorMessage = String(
                        volleyError.networkResponse?.data ?: ByteArray(0),
                        Charset.forName(HttpHeaderParser.parseCharset(volleyError.networkResponse?.headers))
                    )
                    Log.e(volleyError.javaClass.simpleName, errorMessage)
                } catch (encodingError: UnsupportedEncodingException) {
                    Log.e(encodingError.javaClass.name, encodingError.stackTraceToString())
                }
            } else {
                Log.e(error.javaClass.simpleName, error.stackTraceToString())
            }
            return null
        }
    }

    fun <T> get(endpoint: String, cls: Class<T>, params: Map<String, String> = mapOf()): T? {
        return send(Request.Method.GET, endpoint, cls, params)
    }

    fun <T> post(endpoint: String, cls: Class<T>, body: T): T? {
        return send(Request.Method.POST, endpoint, cls, body = body)
    }

    fun <T> delete(endpoint: String, cls: Class<T>, params: Map<String, String>): T? {
        return send(Request.Method.DELETE, endpoint, cls, params)
    }

    class GsonRequest<T>(
        method: Int,
        url: String,
        private val cls: Class<T>,
        private val body: T?,
        private val listener: RequestFuture<T>,
    ) : Request<T>(method, url, listener) {
        private val gson = Gson()

        override fun getBodyContentType() = "application/json"

        override fun getBody(): ByteArray = gson.toJson(body).encodeToByteArray()

        override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
            return try {
                val json = String(
                    response?.data ?: ByteArray(0),
                    Charset.forName(HttpHeaderParser.parseCharset(response?.headers))
                )
                Response.success(
                    gson.fromJson(json, cls),
                    HttpHeaderParser.parseCacheHeaders(response)
                )
            } catch (e: UnsupportedEncodingException) {
                Response.error(ParseError(e))
            } catch (e: JsonSyntaxException) {
                Response.error(ParseError(e))
            }
        }

        override fun deliverResponse(response: T) = listener.onResponse(response)
    }
}
