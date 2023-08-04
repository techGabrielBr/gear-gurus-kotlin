package br.com.fiap.geargurus.model

class Ride(val id : String?, val date : String?, val modal : String?, val duration : String?, val userId : Int?) {
    constructor() : this(null,null, null, null, null)
    constructor(date: String, modal: String, duration: String, userId: Int) : this(null, date, modal, duration, userId)
    constructor(id : String, date : String, modal: String, duration : String) : this(id, date, modal, duration, null)
    constructor(date : String, modal: String, duration : String) : this(null, date, modal, duration, null)
}