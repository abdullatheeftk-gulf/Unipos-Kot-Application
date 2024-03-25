package com.gulfappdeveloper.project3.presentation.screens.editing_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.gulfappdeveloper.project3.domain.remote.get.kot_list.UserOrder
import com.gulfappdeveloper.project3.navigation.root.RootNavScreens
import com.gulfappdeveloper.project3.navigation.root.RootViewModel
import com.gulfappdeveloper.project3.ui.theme.MyPrimeColor
import java.text.SimpleDateFormat
import java.util.*

//private const val TAG = "KOTDetailsDisplay"
@Composable
fun KOTDetailsDisplay(
    rootViewModel: RootViewModel,
    userOrder: UserOrder,
    navHostController: NavHostController
) {


    var updatedDate: Date? = null
    try {
        val myTime = userOrder.dateAndTime + ".000Z"
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
        val date = sdf.parse(myTime)
        updatedDate = convertTimeToLocal(date = date!!)
    } catch (e: Exception) {
        //Log.e(TAG, "KOTDetailsDisplay: ${e.message}", )
    }



    Card(
        modifier = Modifier
            .width(128.dp)
            .height(180.dp)
            .padding(8.dp)
            .clickable {
                navHostController.navigate(RootNavScreens.ShowKotScreen.route)
                rootViewModel.getKOTDetails(userOrder.kotMasterId)

            },
        elevation = 6.dp,
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.MyPrimeColor),
        backgroundColor = Color(0xFFF4BCA3)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = userOrder.kotMasterId.toString(),
                    fontStyle = MaterialTheme.typography.h5.fontStyle,
                    fontSize = 28.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Text(
                    text = if (userOrder.orderType == "Dinein") "DINE IN" else "TAKE AWAY",
                    fontSize = 14.sp,
                    color = if (userOrder.orderType == "Dinein")
                        Color(0xFF0252A1)
                    else
                        Color(0xFF727601),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = if (updatedDate != null) "${
                    SimpleDateFormat(
                        "h:mm:ss a",
                        Locale.getDefault()
                    ).format(updatedDate)
                }" else "",
                color = MaterialTheme.colors.MyPrimeColor
            )

        }

    }


}

@kotlin.jvm.Throws(Exception::class)
private fun convertTimeToLocal(date: Date): Date {
    val timeZone = date.timezoneOffset
    val time = -(timeZone / 60)
    val remainTime = -timeZone % 60
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(Calendar.HOUR_OF_DAY, time)
    return Date(calendar.timeInMillis + (remainTime * 60 * 1000))

}