package com.gulfappdeveloper.project3.data.remote

object HttpRoutes {
    const val BASE_URL = "https://ashproerpapi.azurewebsites.net"
   //const val BASE_URL = "http://192.168.1.5:80"
    const val WELCOME_MESSAGE = "/api/oem"
    const val LOGIN = "/api/login/"

    const val CATEGORY_LIST = "/api/category"
    const val PRODUCT_LIST = "/api/product/"
    const val PRODUCT_IMAGE = "/api/proimage/"
    const val PRODUCT_SEARCH = "/api/productsearch/"
    const val MULTI_SIZE_PRODUCT = "/api/multisize/"
    const val MULTI_SIZE_PRODUCT_IMAGE = "/api/multiimage/"

    const val SECTION_LIST = "/api/section"
    const val TABLE_LIST = "/api/table/"
    const val TABLE_IMAGE = "/api/tableimage/"
    const val TABLE_ORDER = "/api/tableorder/"

    const val GENERATE_KOT = "/api/kichenorder"

    const val EDIT_KOT = "/api/kichenorder/"
    const val EDIT_ORDER_NAME_AND_CHAIR_COUNT = "/api/tableorder/"
    const val LIST_KOT_OF_USER = "/api/supplierorder/"
}