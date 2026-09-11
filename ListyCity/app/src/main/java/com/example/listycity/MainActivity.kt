package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = {cityRepository.addCity(it)},
                        onRemCity = {cityRepository.remCity(it)},
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

class CityRepository{
    private val _cities = mutableStateListOf(
        "Edmonton","Vancouver","Moscow",
        "Sydney","Berlin","Vienna",
        "Tokyo","Beijing","Osaka",
        "New Delhi"
    )
    val cities: List<String>
        get() = _cities

    fun addCity(city: String) {
        _cities.add(city)
    }

    fun remCity(city:String){
        _cities.remove(city)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListyCityTheme {
        Greeting("Android")
    }
}

@Composable
fun CityListScreen(
    cities: List<String>, onAddCity: (String)->Unit,
    onRemCity: (String)->Unit, modifier: Modifier=Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("") }
    var showAdd by remember { mutableStateOf(false) }
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = {
                    showAdd = true
                }
            ){
                Text("Add City")
            }

            Spacer(modifier = Modifier.width(48.dp))

            Button(
                onClick = {
                    if (selectedCity.isNotBlank()) {
                        onRemCity(selectedCity)
                        selectedCity = ""
                    }
                    else if (newCityName.isNotBlank() and (newCityName in cities)){
                        onRemCity(newCityName)
                        newCityName = ""
                    }
                }
            ){
                Text("Remove City")
            }
        }
        LazyColumn(modifier = modifier.fillMaxHeight(0.9f)) {
            items(cities) { city ->
                Button(
                    onClick = {
                        newCityName = city
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 12.dp),
                ){
                    Text(
                        text = city,
                        fontSize = 28.sp
                    )}
            }
        }
        if (showAdd) {
            Row(modifier = Modifier.padding(8.dp)) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City Name") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (newCityName.isNotBlank()) {
                            onAddCity(newCityName)
                            newCityName = ""
                            showAdd = false
                        }
                    }
                ) {
                    Text("Confirm")
                }
            }
        }else{
            Row(modifier = Modifier.padding(48.dp)) {
            }
        }
    }
}

@Composable
fun CityRow(city: String){
    Text(
        text = city,
        fontSize = 28.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
    )
}
