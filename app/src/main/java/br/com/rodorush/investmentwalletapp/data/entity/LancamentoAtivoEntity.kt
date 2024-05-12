package br.com.rodorush.investmentwalletapp.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import br.com.rodorush.investmentwalletapp.domain.enum.TipoOperacao

@Entity(
    tableName = "lat_lancamentos_ativos",
    foreignKeys = [
        ForeignKey(
            entity = CarteiraEntity::class,
            parentColumns = ["car_id"],
            childColumns = ["lat_car_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AtivoEntity::class,
            parentColumns = ["atv_id"],
            childColumns = ["lat_atv_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LancamentoAtivoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "lat_id") val id: Long = 0,
    @ColumnInfo(name = "lat_car_id") val carteiraId: Long,
    @ColumnInfo(name = "lat_atv_id") val ativoId: Long,
    @ColumnInfo(name = "lat_data") val data: Long,
    @ColumnInfo(name = "lat_quantidade") val quantidade: Double,
    @ColumnInfo(name = "lat_preco") val preco: Double,
    @ColumnInfo(name = "lat_tipoOperacao") val tipoOperacao: TipoOperacao
)