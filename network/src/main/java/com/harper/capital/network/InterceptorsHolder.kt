package com.harper.capital.network

import com.harper.core.ext.orElseNullable
import com.harper.core.ext.tryCast
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import retrofit2.Invocation
import kotlin.reflect.full.createInstance

internal class InterceptorsHolder : Interceptor {
    private val interceptorsCache: MutableMap<Int, Interceptor> = mutableMapOf()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val interceptAnnotation = request.getClassAnnotation(Intercept::class.java)
        val interceptor = interceptAnnotation?.let { annotation ->
            val hashCode = annotation.value.hashCode()
            interceptorsCache[hashCode].orElseNullable {
                annotation.value.createInstance().tryCast<Interceptor>()
                    ?.also { interceptorsCache[hashCode] = it }
            }
        }

        return interceptor?.intercept(chain) ?: chain.proceed(request)
    }
}

internal fun <T : Annotation> Request.getClassAnnotation(annotationClass: Class<T>): T? =
    this.tag(Invocation::class.java)?.method()?.declaringClass?.getAnnotation(annotationClass)

internal fun <T : Annotation> Request.getMethodAnnotation(annotationClass: Class<T>): T? =
    this.tag(Invocation::class.java)?.method()?.getAnnotation(annotationClass)
