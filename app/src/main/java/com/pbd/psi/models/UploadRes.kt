package com.pbd.psi.models

data class UploadRes(val items: Items)

data class Items(val items: List<Item>)

data class Item(val name: String, val qty: Int, val price: Double)
