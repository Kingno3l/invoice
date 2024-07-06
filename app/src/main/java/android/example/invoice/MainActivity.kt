package android.example.invoice

import android.example.invoice.ui.theme.Green12
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.example.invoice.ui.theme.InvoiceTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.breens.beetablescompose.BeeTablesCompose


data class User(
    val numb: Int,
    val item_desc: String,
    val Qty: Double,
    val Rate: Double,
    val Amount: Double
)

data class Invoice(
    val invoice_date: String,
    val terms: String,
    val due_date: String
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val users = listOf(
                User(numb =  1, item_desc =  "Pepe Jeans", Qty =  1.00, Rate = 24.99, Amount = 24.99),
                User(numb =  2, item_desc =  "Boys Shirts", Qty =  4.00, Rate = 52.99, Amount = 52.99),
                User(numb =  3, item_desc =  "Girls Shirts", Qty =  3.00, Rate = 41.99, Amount = 41.99),
            )

            val invoiceInfo = listOf(
                Invoice(invoice_date = "18 July 2024", terms = "Due on Receipt", due_date = "18 August 2024")
            )

            val tableHeaders = listOf("#", "Item & Description", "Qty", "Rate", "Amount")

            val invoiceHeaders = listOf("Invoice Date", "Terms", "Due Date")

            InvoiceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(16.dp)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        MainContent(
                            users = users,
                            invoiceInfo = invoiceInfo,
                            headers = tableHeaders,
                            invoiceHeaders = invoiceHeaders
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MainContent(users: List<User>, invoiceInfo: List<Invoice>, headers: List<String>, invoiceHeaders: List<String>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Logo() }

        item { Spacer(modifier = Modifier.height(16.dp)) }


        item { AddressSection() }

        item { Spacer(modifier = Modifier.height(16.dp)) }


        item {
            InvoiceTable(invoiceInfo = invoiceInfo, headers = invoiceHeaders)
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            ItemTable(users = users, headers = headers)
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

//        item { BillSummary() }

        item {  InvoiceSummary() }


        item { Spacer(modifier = Modifier.height(16.dp)) }


        item { DownloadInvoiceButton() }




    }
}

@Composable
fun InvoiceTable(invoiceInfo: List<Invoice>, headers: List<String>) {
    BeeTablesCompose(
        data = invoiceInfo,
        headerTableTitles = headers,
        headerTitlesBackGroundColor = Green12,
        headerTitlesTextStyle = TextStyle(Color.White, fontWeight = FontWeight.Bold)
    )
}


@Composable
fun ItemTable(users: List<User>, headers: List<String>) {
    BeeTablesCompose(
        data = users,
        headerTableTitles = headers,
        columnToIndexIncreaseWidth = 1,
        headerTitlesBackGroundColor = Green12,
        tableRowColors = listOf(Color.White, Color.LightGray),
        headerTitlesTextStyle = TextStyle(Color.White, fontWeight = FontWeight.Bold)
    )
}

@Composable
fun Logo() {
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End)
    {

        Image(
            painter = painterResource(id = R.drawable.logodpx),
            contentDescription = "Company Logo",
            modifier = Modifier.size(48.dp) // Adjust size as needed
        )
    }
}

@Composable
fun AddressSection() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(text = "Ship To", style = MaterialTheme.typography.titleMedium)
            Text(text = "2476 Blackwell Street")
            Text(text = "Fairbanks")
            Text(text = "99701 Colorado")
            Text(text = "U.S.A")
        }
    }
}

@Composable
fun BillSummary(modifier: Modifier = Modifier, billAmount: Float = 61.97f, taxRate: Float = 5.0f) {
    Column(modifier) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = "Sub Total", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$" + billAmount.toString(),
                style = MaterialTheme.typography.titleLarge
            )
        }
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = "Tax Rate", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = taxRate.toString() + "%", style = MaterialTheme.typography.headlineSmall)
        }
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = "Total", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$" + (billAmount + (billAmount * taxRate) / 100).toString(),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun InvoiceSummary() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        Row(
            modifier = Modifier.padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicText("Sub Total")
            Spacer(modifier = Modifier.width(16.dp))
            BasicText("61.97")
        }
        Row(
            modifier = Modifier.padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicText("Tax Rate")
            Spacer(modifier = Modifier.width(16.dp))
            BasicText("5.00%")
        }
        Row(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BasicText("Total", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold))
            Spacer(modifier = Modifier.width(16.dp))
            BasicText("$65.06", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold))
        }
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF98D674) // Light green background
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BasicText("Balance Due", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold))
                Spacer(modifier = Modifier.width(16.dp))
                BasicText("$65.06", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = androidx.compose.ui.text.font.FontWeight.Bold))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InvoiceSummaryPreview() {
    InvoiceSummary()
}



@Composable
fun DownloadInvoiceButton() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {  },
            modifier = Modifier.padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Green12,
                contentColor = Color.White
            )
        ) {
            Text(text = "Download Invoice")
            Icon(painter = painterResource(id = R.drawable.baseline_file_download_24), contentDescription = "Download Icon")
        }
    }
}

