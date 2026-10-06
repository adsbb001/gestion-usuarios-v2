package com.cibertec.gestionusuariosv2.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cibertec.gestionusuariosv2.ui.theme.PurplePrimary

val RojoPeligro = Color(0xFFD32F2F)

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PurplePrimary,
            contentColor = Color.White
        ),
        modifier = modifier.fillMaxWidth().height(50.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
    }
}

@Composable
fun AppDangerOutlineButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, RojoPeligro),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = RojoPeligro),
        modifier = modifier.fillMaxWidth().height(50.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
    }
}