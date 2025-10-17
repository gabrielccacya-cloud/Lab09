package com.example.lab09

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun ScreenPosts(navController: NavHostController, servicio: PostApiService) {
    // Lista observable de posts
    val listaPosts: SnapshotStateList<PostModel> = remember { mutableStateListOf() }

    // Llamada a la API cuando se carga la pantalla
    LaunchedEffect(Unit) {
        val listado = servicio.getUserPosts()
        listado.forEach { listaPosts.add(it) }
    }

    // Mostrar la lista
    LazyColumn {
        items(listaPosts) { item ->
            Row(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = item.id.toString(),
                    modifier = Modifier.weight(0.1f),
                    textAlign = TextAlign.End
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = item.title,
                    modifier = Modifier.weight(0.7f)
                )
                IconButton(
                    onClick = {
                        navController.navigate("postsVer/${item.id}")
                        Log.d("POSTS", "ID = ${item.id}")
                    },
                    modifier = Modifier.weight(0.2f)
                ) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "Ver detalle")
                }
            }
        }
    }
}

@Composable
fun ScreenPost(navController: NavHostController, servicio: PostApiService, id: Int) {
    var post by remember { mutableStateOf<PostModel?>(null) }

    // Llamada a la API cuando entra a la pantalla de detalle
    LaunchedEffect(Unit) {
        val resultado = servicio.getUserPostById(id)
        post = resultado
    }

    // Mostrar detalle del post
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        if (post != null) {
            OutlinedTextField(
                value = post!!.id.toString(),
                onValueChange = {},
                label = { Text("ID") },
                readOnly = true
            )
            OutlinedTextField(
                value = post!!.userId.toString(),
                onValueChange = {},
                label = { Text("User ID") },
                readOnly = true
            )
            OutlinedTextField(
                value = post!!.title,
                onValueChange = {},
                label = { Text("Title") },
                readOnly = true
            )
            OutlinedTextField(
                value = post!!.body,
                onValueChange = {},
                label = { Text("Body") },
                readOnly = true
            )
        } else {
            Text("Cargando post...")
        }
    }
}
