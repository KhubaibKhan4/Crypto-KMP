package org.company.app.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.company.app.theme.LocalThemeIsDark

@Composable
fun SuggestionMessage() {
    val isDark by LocalThemeIsDark.current
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(all = 10.dp)
            .shadow(4.dp, shape = RoundedCornerShape(12.dp))
            .background(
                color = if (isDark) Color.DarkGray else Color.White,
                shape = RoundedCornerShape(12.dp)
            )
            ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "How do you feel about the Crypto\n market today?",
                        color = if (isDark) Color.White else Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Vote to see results",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ThumbsIcon(
                        icon = Icons.Filled.ThumbDown,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ThumbsIcon(
                        icon = Icons.Filled.ThumbUp,
                    )

                }
            }

        }
    }
}

@Composable
fun ThumbsIcon(
    icon: ImageVector,
    contentDes: String = "Thumbs",
) {
    Box(
        modifier = Modifier.size(40.dp)
            .background(color = Color.Gray, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.fillMaxWidth()
                .padding(6.dp),
            imageVector = icon,
            contentDescription = contentDes,
            tint = Color.Yellow
        )
    }
}