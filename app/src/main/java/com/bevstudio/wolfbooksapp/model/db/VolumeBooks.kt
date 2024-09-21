package com.bevstudio.wolfbooksapp.model.db

class VolumeBooks {
    @JvmField
    var id: Int = 0
    @JvmField
    var str_id: String? = null
    @JvmField
    var volumeId: String? = null
    @JvmField
    var isBookmark: Boolean = false

    constructor()


    constructor(volumeId: String?, isBookmark: Boolean) {
        this.volumeId = volumeId
        this.isBookmark = isBookmark
    }
}
