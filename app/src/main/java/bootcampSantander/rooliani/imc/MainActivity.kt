package bootcampSantander.rooliani.imc

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setInicializar()
        setListeners()
    }

    private fun setListeners() {

        alturaSB?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var altura = progress.toFloat() / 100
                var peso = (pesoSB.progress).toFloat() / 100

                alturaTXT.text = "Altura: " + (altura.toString() + " m")
                setResultado(calcularIMC(peso, altura), peso, altura)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        pesoSB?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var peso = progress.toFloat() / 100
                var altura = (alturaSB.progress).toFloat() / 100

                pesoTXT.text = "Peso: " + peso.toString() + " kg"
                setResultado(calcularIMC(peso, altura), peso, altura)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun setInicializar() {
        val pesoInicial = 60.0f // kilos
        val pesoMax = 200.0f
        val alturaInicial = 1.60f //kilos
        val alturaMax = 2.80f

        pesoSB.max = (pesoMax * 100).toInt()
        pesoSB.setProgress((pesoInicial * 100).toInt())

        alturaSB.max = (alturaMax * 100).toInt()
        alturaSB.setProgress((alturaInicial * 100).toInt())

        pesoTXT.text = getString(R.string.peso) + ": " + (pesoInicial.toFloat()).toString() + " kg"
        alturaTXT.text = getString(R.string.altura) + ": " + (alturaInicial.toFloat()).toString() + " m"

        setResultado(calcularIMC(pesoInicial, alturaInicial), pesoInicial, alturaInicial)
    }

    private fun calcularIMC(peso: Float, altura: Float): Float {

        var imc = 0.0f

        if (peso != null && altura != null) {
            imc = peso / (altura * altura)
            resultadoTXT.text = "%.2f".format(imc)
        }

        return imc
    }

    @SuppressLint("SetTextI18n")
    private fun setResultado(imc: Float, peso: Float, altura: Float) {
        if (imc < 18.5) {
            imcV.setImageResource(R.drawable.imc1)
            resultadoTXT.setTextColor(getColor(R.color.pesoBaixo_1))
            info_peso.setTextColor(getColor(R.color.pesoBaixo_1))
            infoImcTXT.text = getString(R.string.peso_baixo)

        } else if (imc < 24.9) {
            imcV.setImageResource(R.drawable.imc2)
            resultadoTXT.setTextColor(getColor(R.color.pesoNormal_2))
            info_peso.setTextColor(getColor(R.color.pesoNormal_2))
            infoImcTXT.text = getString(R.string.peso_normal)
        } else if (imc < 29.9) {
            imcV.setImageResource(R.drawable.imc3)
            resultadoTXT.setTextColor(getColor(R.color.sobrePeso_3))
            info_peso.setTextColor(getColor(R.color.sobrePeso_3))
            infoImcTXT.text = getString(R.string.sobrepreso)
        } else if (imc < 34.9) {
            imcV.setImageResource(R.drawable.imc4)
            resultadoTXT.setTextColor(getColor(R.color.obesidade_4))
            info_peso.setTextColor(getColor(R.color.obesidade_4))
            infoImcTXT.text = getString(R.string.obesidade_I)
        } else if (imc < 39.9) {
            imcV.setImageResource(R.drawable.imc5)
            resultadoTXT.setTextColor(getColor(R.color.obesidadeSevera_5))
            info_peso.setTextColor(getColor(R.color.obesidadeSevera_5))
            infoImcTXT.text = getString(R.string.obesidade_II)
        } else {
            imcV.setImageResource(R.drawable.imc6)
            resultadoTXT.setTextColor(getColor(R.color.obesidadeMorbida_6))
            info_peso.setTextColor(getColor(R.color.obesidadeMorbida_6))
            infoImcTXT.text = getString(R.string.obesidade_III)
        }

        val diferencaImcNormal: Float

        diferencaImcNormal = calcularDiferencaPesoImcNormal(imc, peso, altura)

        if (imc < 18.5) {
            info_peso.text = getString(R.string.ganhe) + " %.1f ".format(diferencaImcNormal)  + getString(R.string.atingir_imc_normal)
        } else if (imc < 25) {
            info_peso.text = ""
        } else {
            info_peso.text = getString(R.string.perca) + " %.1f ".format(diferencaImcNormal)  + getString(R.string.atingir_imc_normal)
        }
    }

    private fun calcularDiferencaPesoImcNormal(imc: Float, peso:Float, altura: Float): Float
    {
        val diferenca : Float

        if (imc < 18.5)
        {
            diferenca = (18.5f * (altura * altura)) - peso
        }else if (imc < 25)
        {
            diferenca = 0f
        }else
        {
            diferenca = peso - (24.9f * (altura * altura))
        }

        return diferenca
    }
}