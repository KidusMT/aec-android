package com.aait.aec.data.db.model

class Examinee {
    var thumb: Int? = null
    var title: String? = null
    var type: String? = null
    var date: String? = null
    var inst: String? = null
    var weight: String? = null

    constructor(thumb: Int?, title: String?, type: String?, date: String?, inst: String?, weight: String?) {
        this.thumb = thumb
        this.title = title
        this.type = type
        this.date = date
        this.inst = inst
        this.weight = weight
    }
}