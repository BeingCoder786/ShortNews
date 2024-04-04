package com.example.newsinshort.di

import com.example.newsinshort.data.AppConstants
import com.example.newsinshort.data.api.ApiService
import com.example.newsinshort.data.datasource.NewsDataSource
import com.example.newsinshort.data.datasource.NewsDataSourceImpl
import com.example.newsinshort.ui.repository.NewsRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // provide retrofit for call API decouple code
    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val httpsLoginterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        val httpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpsLoginterceptor)
        }

        httpClient.apply {
            readTimeout(60, TimeUnit.SECONDS)
        }

        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .client(httpClient.build()).addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    // provide api interface object
    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    // provide newsData source
    @Provides
    @Singleton
    fun provideNewsDataSource(apiService: ApiService): NewsDataSource {
        return NewsDataSourceImpl(apiService)
    }

    // provice repositriy
    @Provides
    @Singleton
    fun provideNewsRepository(newsDataSource: NewsDataSource): NewsRepository {
        return NewsRepository(newsDataSource)
    }
}
