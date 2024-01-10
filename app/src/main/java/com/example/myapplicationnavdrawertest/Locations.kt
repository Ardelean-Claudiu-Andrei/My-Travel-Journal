package com.example.myapplicationnavdrawertest

import android.os.Parcel
import android.os.Parcelable

data class Locations(
    var image: Int,
    val titleTV: Int,
    var name: String,
    var cityAndState: String,
    var country: String,
    var lastVisitDate: String,
    var rating: Float,
    var isFavorite: Boolean = false
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeInt(titleTV)
        parcel.writeString(name)
        parcel.writeString(cityAndState)
        parcel.writeString(country)
        parcel.writeString(lastVisitDate)
        parcel.writeFloat(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Locations> {
        override fun createFromParcel(parcel: Parcel): Locations {
            return Locations(parcel)
        }

        override fun newArray(size: Int): Array<Locations?> {
            return arrayOfNulls(size)
        }
    }
}
