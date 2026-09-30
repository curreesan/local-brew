package ree.selfcode.localbrew.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import ree.selfcode.localbrew.R
import ree.selfcode.localbrew.data.model.Cafe
import ree.selfcode.localbrew.ui.discover.DiscoverScreen
import ree.selfcode.localbrew.ui.favorites.FavoritesScreen
import ree.selfcode.localbrew.ui.search.SearchScreen
import ree.selfcode.localbrew.ui.theme.Accent
import ree.selfcode.localbrew.ui.theme.Background

sealed class LocationPermissionState {
    object NotRequested : LocationPermissionState()
    object Granted : LocationPermissionState()
    object ShouldShowRationale : LocationPermissionState()
    object PermanentlyDenied : LocationPermissionState()
}

enum class MainTab {
    Discover, Search, Favorites
}

@Composable
fun MainScreen(onCafeClick: (Cafe) -> Unit, onProfileClick: () -> Unit, onLogout: () -> Unit) {
    val context = LocalContext.current
    var permissionRequestCount by rememberSaveable { mutableStateOf(0) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionRequestCount++
    }

    var resumeTrigger by remember { mutableIntStateOf(0) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                resumeTrigger++
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val permissionState = remember(resumeTrigger, permissionRequestCount) {
        when {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> LocationPermissionState.Granted

            ActivityCompat.shouldShowRequestPermissionRationale(
                context as Activity, Manifest.permission.ACCESS_FINE_LOCATION
            ) -> LocationPermissionState.ShouldShowRationale

            permissionRequestCount > 0 -> LocationPermissionState.PermanentlyDenied

            else -> LocationPermissionState.NotRequested
        }
    }

    LaunchedEffect(Unit) {
        if (permissionState is LocationPermissionState.NotRequested) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cancellationTokenSource = remember { CancellationTokenSource() }
    var location by rememberSaveable { mutableStateOf<Location?>(null) }

    LaunchedEffect(permissionState) {
        if (permissionState is LocationPermissionState.Granted && location == null) {
            location = fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource.token
            ).await()
        }
    }

    when (permissionState) {
        is LocationPermissionState.Granted -> {
            val currentLocation = location
            if (currentLocation == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_local_brew_logo),
                            contentDescription = null,
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .width(180.dp)
                                .height(83.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Getting your location…")
                    }
                }
            } else {
                MainTabsScaffold(
                    location = currentLocation,
                    onCafeClick = onCafeClick,
                    onProfileClick = onProfileClick,
                    onLogout = onLogout
                )
            }
        }
        is LocationPermissionState.ShouldShowRationale -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Local Brew needs your location to find nearby cafés.")
                    Button(
                        onClick = {
                            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        },
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Grant Location Access")
                    }
                }
            }
        }
        is LocationPermissionState.PermanentlyDenied -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Location access was denied. Enable it in Settings to see nearby cafés.")
                    Button(
                        onClick = {
                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            intent.data = Uri.fromParts("package", context.packageName, null)
                            context.startActivity(intent)
                        },
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text("Open Settings")
                    }
                }
            }
        }
        is LocationPermissionState.NotRequested -> {
            // LaunchedEffect above is already triggering the system dialog
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTabsScaffold(
    location: Location,
    onCafeClick: (Cafe) -> Unit,
    onProfileClick: () -> Unit,
    onLogout: () -> Unit
) {
    var selectedTab by rememberSaveable { mutableStateOf(MainTab.Discover) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(drawerContainerColor = Background) {
                NavigationDrawerItem(
                    label = { Text("Profile") },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(unselectedTextColor = Accent),
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        onProfileClick()
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Log out") },
                    selected = false,
                    colors = NavigationDrawerItemDefaults.colors(unselectedTextColor = Accent),
                    onClick = {
                        coroutineScope.launch { drawerState.close() }
                        onLogout()
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text(selectedTab.name) },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu")
                        }
                    }
                )
            },
            bottomBar = {
                val navItemColors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background,
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                ) {
                    NavigationBarItem(
                        selected = selectedTab == MainTab.Discover,
                        onClick = { selectedTab = MainTab.Discover },
                        icon = {
                            val icon = if (selectedTab == MainTab.Discover) {
                                R.drawable.ic_discover_filled
                            } else {
                                R.drawable.ic_discover_unfilled
                            }
                            Icon(
                                painter = painterResource(id = icon),
                                contentDescription = "Discover",
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label = null,
                        colors = navItemColors
                    )
                    NavigationBarItem(
                        selected = selectedTab == MainTab.Search,
                        onClick = { selectedTab = MainTab.Search },
                        icon = {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search",
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label = null,
                        colors = navItemColors
                    )
                    NavigationBarItem(
                        selected = selectedTab == MainTab.Favorites,
                        onClick = { selectedTab = MainTab.Favorites },
                        icon = {
                            val icon = if (selectedTab == MainTab.Favorites) {
                                R.drawable.ic_favorite_filled
                            } else {
                                R.drawable.ic_favorite_unfilled
                            }
                            Icon(
                                painter = painterResource(id = icon),
                                contentDescription = "Favorites",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(30.dp)
                            )
                        },
                        label = null,
                        colors = navItemColors
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                when (selectedTab) {
                    MainTab.Discover -> DiscoverScreen(location = location, onCafeClick = onCafeClick)
                    MainTab.Search -> SearchScreen(location = location, onCafeClick = onCafeClick)
                    MainTab.Favorites -> FavoritesScreen(location = location, onCafeClick = onCafeClick)
                }
            }
        }
    }
}
