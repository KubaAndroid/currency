package jw.adamiak.currencycheck.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jw.adamiak.currencycheck.data.api.FixerApi
import jw.adamiak.currencycheck.data.api.FixerApi.Companion.BASE_URL
import jw.adamiak.currencycheck.data.repository.CurrencyRepository
import jw.adamiak.currencycheck.utils.Helpers
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

	@Provides
	@Singleton
	fun provideFixerApi(): FixerApi {
		val moshi = Moshi.Builder()
			.add(Helpers.RatesAdapter())
			.add(KotlinJsonAdapterFactory())
			.build()

		return Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(MoshiConverterFactory.create(moshi))
			.build()
			.create(FixerApi::class.java)
	}

	@Provides
	@Singleton
	fun provideRepository(api: FixerApi): CurrencyRepository {
		return CurrencyRepository(api)
	}

}