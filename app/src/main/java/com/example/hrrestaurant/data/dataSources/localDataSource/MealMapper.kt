package com.example.hrrestaurant.data.dataSources.localDataSource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.hrrestaurant.data.dataSources.mapRemoteToLocal.Mapper
import com.example.hrrestaurant.data.dataSources.remoteDataSource.MealDto
import java.text.DecimalFormat

class MealMapper : Mapper<MealDto, Meal> {

    override suspend fun map(input: MealDto?, context: Context): Meal? {

        val bitmap = getBitmap(input!!.itemImage, context)
        return Meal(
            id = input.id,
            title = input.title,
            description = input.description,
            price = input.price,
            estimatedTime = input.estimatedTime.trim(' ', 'm', 'i', 'n'),
            category = input.category,
            topRated = input.topRated,
//            instead of saving null , we save false
            isChecked = false,
            isAddedToChart = false,
            itemImage = bitmap
        )
    }

    private suspend fun getBitmap(imgUrl: String, context: Context): Bitmap {
        val loading = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(imgUrl).size(1000 , 1000).build()
        val result = (loading.execute(request = request) as SuccessResult).drawable
        return (result as BitmapDrawable).bitmap
    }

    override suspend fun mapList(input: List<MealDto?>, context: Context): List<Meal?> {
        return input.map { mealDto ->
            map(mealDto, context)
        }.toList()
    }
}