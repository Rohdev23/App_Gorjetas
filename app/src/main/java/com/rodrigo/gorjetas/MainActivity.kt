package com.rodrigo.gorjetas

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.rodrigo.gorjetas.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.botaoCalcular.setOnClickListener { calcularTipo() }
        binding.custoDoServicoEditText.setOnKeyListener { view, keyCode, _ ->
            ocultarBotao(view, keyCode)
        }
    }

    private fun calcularTipo() {
        val stringEmTextField = binding.custoDoServicoEditText.text.toString()
        val custo = stringEmTextField.toDoubleOrNull()
        if (custo == null) {
            binding.resultadoGorjeta.text = ""
            return
        }

        val tipoPorcentage = when (binding.opcoesDeDicas.checkedRadioButtonId) {
            R.id.opcao_20_porcento -> 0.20
            R.id.opcao_18_porcento -> 0.18
            else -> 0.15
        }

        var tipo = tipoPorcentage * custo
        if (binding.dicaArredondamento.isChecked) {
            tipo = ceil(tipo)
        }

        val tipoFormatado = NumberFormat.getCurrencyInstance().format(tipo)
        binding.resultadoGorjeta.text = getString(R.string.gorjeta,tipoFormatado )
    }
    private fun ocultarBotao(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMetodoManager =
                getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMetodoManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}
