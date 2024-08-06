package com.example.movieapp.movieList.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTextField(
    value: String,
    onValueChange: (String) -> Unit,
    error: Boolean,
    placeholder: String,
    keyboardOptions: KeyboardOptions,
    visualTransformation: VisualTransformation?,
    trailingIcon: @Composable() (() -> Unit)?
) {
    OutlinedTextField(
        value = value.trim(),
        onValueChange = onValueChange,
        isError = error,
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxWidth(),
        placeholder = { Text(text = placeholder) },
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            containerColor = Color.White,
            focusedIndicatorColor = if (error) Color.Red else MaterialTheme.colorScheme.surfaceTint,
            unfocusedIndicatorColor = if (error) Color.Red else Color.Gray
        ),
        visualTransformation = visualTransformation!!,
        trailingIcon = trailingIcon
    )

}