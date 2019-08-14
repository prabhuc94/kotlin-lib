package com.wee3ventures.fontier.`interface`

interface CreateListener {
    fun onSignUp(username : Any, userMail : Any, userPass : Any)
    fun onSignIn(signIn : Boolean)
    fun onBackpressed(backPress : Boolean)
}