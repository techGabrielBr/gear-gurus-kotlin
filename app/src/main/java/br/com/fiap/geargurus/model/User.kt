package br.com.fiap.geargurus.model

class User(val id: Int?, val name: String?, val email: String?, val cpf: String?, val password: String?){

    constructor() : this(null, null, null, null, null)

    constructor(name: String, email: String, cpf: String, password: String) : this(null, name, email, cpf, password)

    constructor(email: String, password: String) : this(null, null, email, null, password)

    constructor(id: Int, name : String) : this(id, name, null, null, null)
}