package com.mobnetic.compose.sharedelement.sample

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.preferredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.mobnetic.compose.sharedelement.SharedElement
import com.mobnetic.compose.sharedelement.SharedElementsRoot
import com.mobnetic.compose.sharedelement.SharedElementsRootScope
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private data class User(@DrawableRes val avatar: Int, val name: String)

    companion object {
        private const val ListScreen = "list"
        private const val DetailsScreen = "details"

        private const val TransitionDurationMillis = 1000
    }

    private val users = listOf(
        User(R.drawable.avatar_1, "Adam"),
        User(R.drawable.avatar_2, "Andrew"),
        User(R.drawable.avatar_3, "Anna"),
        User(R.drawable.avatar_4, "Boris"),
        User(R.drawable.avatar_5, "Carl"),
        User(R.drawable.avatar_6, "Donna"),
        User(R.drawable.avatar_7, "Emily"),
        User(R.drawable.avatar_8, "Fiona"),
        User(R.drawable.avatar_9, "Grace"),
        User(R.drawable.avatar_10, "Irene"),
        User(R.drawable.avatar_11, "Jack"),
        User(R.drawable.avatar_12, "Jake"),
        User(R.drawable.avatar_13, "Mary"),
        User(R.drawable.avatar_14, "Peter"),
        User(R.drawable.avatar_15, "Rose"),
        User(R.drawable.avatar_16, "Victor")
    )

    private var selectedUser by mutableStateOf<User?>(null)
    private lateinit var scope: SharedElementsRootScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SharedElementsRoot(durationMillis = TransitionDurationMillis) {
                    scope = this
                    Crossfade(
                        selectedUser,
                        animation = tween(durationMillis = TransitionDurationMillis)
                    ) {
                        when (it) {
                            null -> UsersListScreen()
                            else -> UserDetailsScreen(it)
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (selectedUser != null) {
            changeUser(null)
        } else {
            super.onBackPressed()
        }
    }

    private fun changeUser(user: User?) {
        val currentUser = selectedUser
        if (currentUser != user) {
            if (currentUser != null) {
                scope.prepareTransition(currentUser.avatar, currentUser.name)
            }
            selectedUser = user
        }
    }

    @Composable
    private fun UsersListScreen() {
        LazyColumn {
            items(users) { user ->
                ListItem(
                    Modifier.clickable { changeUser(user) },
                    icon = {
                        SharedElement(key = user.avatar, screenKey = ListScreen) {
                            Image(
                                vectorResource(id = user.avatar),
                                modifier = Modifier.preferredSize(48.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    },
                    text = {
                        SharedElement(key = user.name, screenKey = ListScreen) {
                            Text(text = user.name)
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun UserDetailsScreen(user: User) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SharedElement(key = user.avatar, screenKey = DetailsScreen) {
                Image(
                    vectorResource(id = user.avatar),
                    modifier = Modifier.preferredSize(200.dp).clickable { changeUser(null) },
                    contentScale = ContentScale.Crop
                )
            }
            SharedElement(
                key = user.name,
                screenKey = DetailsScreen
            ) {
                Text(text = user.name, style = MaterialTheme.typography.h1)
            }
        }
    }

}
