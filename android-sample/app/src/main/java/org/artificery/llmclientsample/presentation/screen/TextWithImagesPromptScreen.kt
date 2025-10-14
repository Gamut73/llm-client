package org.artificery.llmclientsample.presentation.screen

import android.net.Uri
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.artificery.llm_client.model.TextPrompt
import org.artificery.llmclientsample.presentation.viewmodel.SharedSampleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextWithImagesPromptScreen(
    viewModel: SharedSampleViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var promptText by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    val imageUrls = remember { mutableStateListOf<String>() }
    val imageUris = remember { mutableStateListOf<Uri>() }
    var responseText by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()
    ) { uris: List<Uri> ->
        imageUris.addAll(uris)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Text with Images Prompt") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            backDispatcher?.onBackPressed()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = promptText,
                onValueChange = { promptText = it },
                label = { Text("Enter your prompt") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                enabled = !isProcessing
            )

            Button(
                onClick = {
                    imagePickerLauncher.launch(arrayOf("image/*"))
                },
                enabled = !isProcessing,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Images from Storage")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Image URL") },
                    modifier = Modifier.weight(1f),
                    enabled = !isProcessing
                )
                Button(
                    onClick = {
                        if (imageUrl.isNotBlank()) {
                            imageUrls.add(imageUrl)
                            imageUrl = ""
                        }
                    },
                    enabled = !isProcessing && imageUrl.isNotBlank()
                ) {
                    Text("Add")
                }
            }

            if (imageUris.isNotEmpty() || imageUrls.isNotEmpty()) {
                Text(
                    text = "Added image files and urls",
                    style = MaterialTheme.typography.titleMedium
                )

                if (imageUris.isNotEmpty()) {
                    Text(
                        text = "Files: ${imageUris.size} image(s) selected",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    imageUris.forEachIndexed { index, uri ->
                        Text(
                            text = "${index + 1}. ${uri.lastPathSegment ?: uri.toString()}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }

                if (imageUrls.isNotEmpty()) {
                    Text(
                        text = "URLs: ${imageUrls.size} image URL(s) added",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    imageUrls.forEachIndexed { index, url ->
                        Text(
                            text = "${index + 1}. $url",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Button(
                onClick = {
                    isProcessing = true
                    responseText = "Processing..."
                    scope.launch {
                        try {
                            val result = viewModel.textWithImagesPrompt(
                                prompt = TextPrompt(promptText),
                                imageUrls = imageUrls.toList(),
                                imagesURIs = imageUris,
                                contentResolver = context.contentResolver
                            )
                            responseText = result
                        } catch (e: Exception) {
                            responseText = "Error: ${e.message}"
                        } finally {
                            isProcessing = false
                        }
                    }
                },
                enabled = !isProcessing && promptText.isNotBlank() && (imageUris.isNotEmpty() || imageUrls.isNotEmpty()),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Prompt")
            }

            if (responseText.isNotEmpty()) {
                Text(
                    text = "Response:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = responseText,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}
