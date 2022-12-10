package com.gulfappdeveloper.project3.data.remote

object HttpRoutes {
    const val BASE_URL = "https://uniposerpapi.azurewebsites.net"

    const val WELCOME_MESSAGE = "/api/oem"
    const val LOGIN = "/api/login/"

    const val KOT_CANCEL_PRIVILEGE = "/api/Kotcancelprevilage/"

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

    const val UNI_LICENSE_HEADER = "riolab123456"
    const val UNI_LICENSE_ACTIVATION_URL =
        "http://license.riolabz.com/license-repo/public/api/v1/verifyjson"

    const val SEE_IP4 = "https://ip4.seeip.org/json"
}