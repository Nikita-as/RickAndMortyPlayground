package com.example.rickandmortyplayground.di


import com.example.rickandmortyplayground.data.remote.CharacterApi
import com.example.rickandmortyplayground.data.repository.CharacterRepositoryImpl
import com.example.rickandmortyplayground.domain.repository.CharacterRepository
import com.example.rickandmortyplayground.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRxJava2CallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideBaseUrl(): String {
        return Constants.BASE_URL
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        rxJava2CallAdapterFactory: RxJava2CallAdapterFactory,
        baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxJava2CallAdapterFactory)
            .baseUrl(baseUrl)
            .build()
    }

    @Singleton
    @Provides
    fun provideCharacterApi(retrofit: Retrofit): CharacterApi {
        return retrofit.create(CharacterApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterRepository(api: CharacterApi): CharacterRepository {
        return CharacterRepositoryImpl(api)
    }

}