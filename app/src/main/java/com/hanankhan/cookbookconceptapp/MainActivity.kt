package com.hanankhan.cookbookconceptapp

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hanankhan.cookbookconceptapp.data.Recipe
import com.hanankhan.cookbookconceptapp.data.strawberryCake
import com.hanankhan.cookbookconceptapp.ui.theme.CookbookConceptAppTheme
import com.hanankhan.cookbookconceptapp.ui.theme.DarkGray
import com.hanankhan.cookbookconceptapp.ui.theme.Gray
import com.hanankhan.cookbookconceptapp.ui.theme.LightGray
import com.hanankhan.cookbookconceptapp.ui.theme.Pink
import com.hanankhan.cookbookconceptapp.ui.theme.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CookbookConceptAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Body(strawberryCake, innerPadding)
                }
            }
        }
    }
}

@Composable
fun Body(recipe: Recipe, paddingValues: PaddingValues = PaddingValues()) {
    Box {
        Content(recipe)
        ParallaxToolbar(recipe, paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParallaxToolbar(recipe: Recipe, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        Box(modifier = Modifier.height(400.dp - 56.dp)) {
            Image(
                painter = painterResource(R.drawable.strawberry_pie_1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                Pair(0.4f, Color.Transparent),
                                Pair(1f, Color.White)
                            )
                        )
                    )
            )
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    recipe.category,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(Shapes().small)
                        .background(
                            LightGray
                        )
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = recipe.title,
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(56.dp)
            .padding(horizontal = 16.dp)
    ) {
        CircularButton(R.drawable.ic_arrow_back)
        CircularButton(R.drawable.ic_favorite)
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResource: Int,
    color: Color = Gray,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    onClick: () -> Unit = {}
) {
    Button(
        contentPadding = PaddingValues(),
        shape = Shapes().small,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = color),
        elevation = elevation,
        onClick = onClick,
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        Icon(painterResource(iconResource), contentDescription = null)
    }
}

@Composable
fun Content(recipe: Recipe) {
    LazyColumn(
        contentPadding = PaddingValues(top = 400.dp)
    ) {
        item {
            BasicInfo(recipe)
            Description(recipe)
            ServingCalculator()
            IngredientsHeader()
            IngredientsList(recipe)
        }
    }
}

@Composable
fun IngredientsList(recipe: Recipe) {
    EasyGrid(nColumns = 3, items = recipe.ingredients) {

        IngredientCard(it.image, it.title, it.subtitle, Modifier)
    }
}

@Composable
fun <T>EasyGrid(nColumns: Int, items: List<T>, content: @Composable (T) -> Unit) {
     Column(Modifier.padding(16.dp)) {
        for (i in items.indices step nColumns) {
            Row {
                for (j in 0 until nColumns) {
                    if (i + j < items.size) {

                        Box(contentAlignment = Alignment.TopStart, modifier = Modifier.weight(1f)) {
                            content(items[i + j])
                        }
                    } else {
                        Spacer(Modifier.weight(1f, fill = true))
                    }
                }
            }
        }
     }
}

@Composable
fun IngredientCard(
    @DrawableRes iconResource: Int,
    title: String,
    subtitle: String,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Card(
            shape = Shapes().large,
            colors = CardDefaults.cardColors(containerColor = LightGray),
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(iconResource),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .width(60.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
        Text(
            text = title,
            modifier = Modifier.width(100.dp),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            ),
        )
        Text(text = subtitle, color = DarkGray, modifier = Modifier.width(100.dp), fontSize = 14.sp)
    }
}

@Composable
fun IngredientsHeader() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .clip(Shapes().medium)
            .background(LightGray)
            .fillMaxWidth()
            .height(44.dp)
    ) {
        TabButton("Ingredients", true, Modifier.weight(1f))
        TabButton("Tools", false, Modifier.weight(1f))
        TabButton("Steps", false, Modifier.weight(1f))
    }
}

@Composable
fun TabButton(text: String, active: Boolean = false, modifier: Modifier) {
    Button(
        onClick = {},
        shape = Shapes().extraLarge,
        modifier = modifier.height(39.dp),
        elevation = null,
        colors = if (active) ButtonDefaults.buttonColors(
            containerColor = Pink,
            contentColor = White
        ) else ButtonDefaults.buttonColors(
            containerColor = LightGray,
            contentColor = DarkGray
        )
    ) {
        Text(text)
    }
}

@Composable
fun ServingCalculator() {
    var value by remember { mutableStateOf(6) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(Shapes().medium)
            .background(LightGray)
            .padding(horizontal = 16.dp)
    ) {
        Text("Serving", Modifier.weight(1f), fontWeight = FontWeight.Medium)
        CircularButton(iconResource = R.drawable.ic_minus, elevation = null, onClick = { value-- })
        Text("$value", Modifier.padding(16.dp), fontWeight = FontWeight.Medium)
        CircularButton(iconResource = R.drawable.ic_plus, elevation = null, onClick = { value++ })
    }
}

@Composable
fun Description(recipe: Recipe) {
    Text(
        text = recipe.description,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
fun BasicInfo(recipe: Recipe) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn(R.drawable.ic_clock, recipe.cookingTime)
        InfoColumn(R.drawable.ic_flame, recipe.energy)
        InfoColumn(R.drawable.ic_star, recipe.rating)
    }
}

@Composable
fun InfoColumn(@DrawableRes iconResource: Int, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = null,
            tint = Pink,
            modifier = Modifier.height(24.dp)
        )
        Text(text, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true, widthDp = 380, heightDp = 900)
@Composable
fun BodyPreview() {
    CookbookConceptAppTheme {
        Body(strawberryCake)
    }
}