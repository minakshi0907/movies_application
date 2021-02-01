package com.example.moviesapplication.di

import com.example.moviesapplication.BuildConfig
import com.example.moviesapplication.api.ApiInterface
import com.example.moviesapplication.repository.MovieDetailsRespository
import com.example.moviesapplication.repository.MoviesRepository
import com.example.moviesapplication.viewmodel.MovieDetailsViewModel
import com.example.moviesapplication.viewmodel.MoviesViewModel
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Array.get
import java.util.concurrent.TimeUnit


val appModules = module {

    //Repositories
    single { MoviesRepository(get()) }
    single { MovieDetailsRespository(get()) }

    //ViewModels
    viewModel { MoviesViewModel(get()) }
    viewModel { MovieDetailsViewModel(get()) }
}

val networkModules = module {
    single<ApiInterface> {
        get<Retrofit>().create(ApiInterface::class.java)
    }
    val connectTimeout: Long = 40
    val readTimeout: Long = 40

    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    fun provideRetrofit(client: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideHttpClient() }

    single {
        provideRetrofit(get(), BuildConfig.BASE_URL)
    }
}
