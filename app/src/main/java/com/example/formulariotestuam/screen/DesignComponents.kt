package com.example.formulariotestuam.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.formulariotestuam.ui.theme.UamAccent
import com.example.formulariotestuam.ui.theme.UamPrimary
import com.example.formulariotestuam.ui.theme.UamPrimaryDark
import com.example.formulariotestuam.ui.theme.UamPrimaryLight
import com.example.formulariotestuam.ui.theme.UamSurface
import com.example.formulariotestuam.ui.theme.UamTextSecondary
import com.example.formulariotestuam.ui.theme.White

@Composable
fun UamBackground(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        UamAccent,
                        MaterialTheme.colorScheme.background,
                        White
                    )
                )
            )
    ) {
        Box(
            modifier = Modifier
                .size(260.dp)
                .offset(x = (-120).dp, y = (-90).dp)
                .clip(CircleShape)
                .background(UamPrimary.copy(alpha = 0.13f))
        )

        Box(
            modifier = Modifier
                .size(210.dp)
                .offset(x = 250.dp, y = 120.dp)
                .clip(CircleShape)
                .background(UamPrimaryLight.copy(alpha = 0.16f))
        )

        content()
    }
}

@Composable
fun UamLogoHeader(
    titulo: String,
    subtitulo: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "UAM",
            color = UamPrimary,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Black,
            letterSpacing = androidx.compose.ui.unit.TextUnit.Unspecified
        )

        Text(
            text = titulo,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Text(
            text = subtitulo,
            color = UamTextSecondary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ModernCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = UamSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}

@Composable
fun GradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val alpha = animateFloatAsState(
        targetValue = if (enabled) 1f else 0.45f
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(
                        UamPrimary,
                        UamPrimaryDark
                    )
                )
            )
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = White.copy(alpha = alpha.value),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun SoftButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = UamPrimary
        ),
        contentPadding = PaddingValues(vertical = 14.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SectionTitle(
    titulo: String,
    subtitulo: String? = null
) {
    Column {
        Text(
            text = titulo,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        if (subtitulo != null) {
            Text(
                text = subtitulo,
                color = UamTextSecondary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun InfoPill(
    text: String
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(UamAccent)
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = UamPrimaryDark,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun StatCard(
    titulo: String,
    valor: String,
    emoji: String,
    modifier: Modifier = Modifier
) {
    ModernCard(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(UamAccent)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = titulo,
                    color = UamTextSecondary,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = valor,
                    color = UamPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}