package br.com.rodorush.investmentwalletapp.ui.activities

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.rodorush.investmentwalletapp.data.entity.AtivoEntity

@Composable
fun ListaAtivos(
    modifier: Modifier = Modifier,
    ativos: List<AtivoEntity>,
    onEditAtivo: (AtivoEntity) -> Unit = {},
    onDeleteAtivo: (AtivoEntity) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(ativos) { ativo ->
            AtivoItem(
                ativo = ativo,
                onEditClick = { onEditAtivo(ativo) },
                onDeleteClick = { onDeleteAtivo(ativo) }
            )
        }
    }
}

@Composable
fun AtivoItem(
    ativo: AtivoEntity,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        content = {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = ativo.nome, style = MaterialTheme.typography.bodyMedium)
                Text(text = "Ticker: ${ativo.ticker}", style = MaterialTheme.typography.bodyMedium)
                ativo.ultimoPreco?.let {
                    Text(text = "Último Preço: $it", style = MaterialTheme.typography.bodyMedium)
                }
                ativo.dataUltimoPreco?.let {
                    Text(text = "Data do Último Preço: $it", style = MaterialTheme.typography.bodyMedium)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    )
}
