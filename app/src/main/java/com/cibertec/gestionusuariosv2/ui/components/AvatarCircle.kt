package com.cibertec.gestionusuariosv2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cibertec.gestionusuariosv2.ui.theme.AvatarColors
import kotlin.math.abs

@Composable
fun AvatarCircle(initial: String, colorSeed: Int, size: Dp = 40.dp) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(AvatarColors[abs(colorSeed) % AvatarColors.size]),
        contentAlignment = Alignment.Center
    ) {
        Text(initial.uppercase(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}