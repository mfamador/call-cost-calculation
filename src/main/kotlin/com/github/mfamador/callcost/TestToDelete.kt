package com.github.mfamador.callcost

 data class TestToDelete (val message: String) {

     fun getMessageEnhanced() : String {
         message.also { println("The length of this String is ${it.length}") }
         return "$message test"
     }



 }


