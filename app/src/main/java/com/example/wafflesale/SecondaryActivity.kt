package com.example.wafflesale

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.NumberPicker
import android.widget.NumberPicker.OnValueChangeListener
import kotlinx.android.synthetic.main.activity_secondary.*

class SecondaryActivity : AppCompatActivity() {

    var totalChocoPrice: Float? = 0f
    var totalVanillePrice: Float? = 0f
    var totalCarrePrice: Float? = 0f
    var totalMixPrice: Float? = 0f
    var totalPrice: Float? = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_secondary)

        cb_choco.setOnClickListener {
            if(cb_choco.isChecked){
                numberChoco.maxValue = 10
            }else{
                numberChoco.maxValue = 0
                totalPrice = totalPrice?.minus(totalChocoPrice!!)
                total.text = totalPrice.toString()
                totalChocoPrice = 0f
                totalChoco.text = null
            }
        }

        cb_vanille.setOnClickListener {
            if(cb_vanille.isChecked){
                numberVanille.maxValue = 10
            }else{
                numberVanille.maxValue = 0
                totalPrice = totalPrice?.minus(totalVanillePrice!!)
                total.text = totalPrice.toString()
                totalVanillePrice = 0f
                totalVanille.text = null
            }
        }
        cb_carre.setOnClickListener {
            if(cb_carre.isChecked){
                numberCarre.maxValue = 10
            }else{
                numberCarre.maxValue = 0
                totalPrice = totalPrice?.minus(totalCarrePrice!!)
                total.text = totalPrice.toString()
                totalCarrePrice = 0f
                totalCarre.text = null
            }
        }

        cb_mix.setOnClickListener {
            if(cb_mix.isChecked){
                numberMix.maxValue = 10
            }else{
                numberMix.maxValue = 0
                totalPrice = totalPrice?.minus(totalMixPrice!!)
                total.text = totalPrice.toString()
                totalMixPrice = 0f
                totalMix.text = null
            }
        }

        numberChoco.minValue = 0
        numberChoco.wrapSelectorWheel = true
        numberChoco.setOnValueChangedListener { _, oldVal, newVal ->
            totalChocoPrice = newVal * 6.00f
            totalChoco.text = totalChocoPrice.toString()
            totalPrice = totalPrice?.minus(oldVal * 6.00f)
            totalPrice = totalPrice?.plus(totalChocoPrice!!)
            total.text = totalPrice.toString()
        }

        numberVanille.minValue = 0
        numberVanille.wrapSelectorWheel = true
        numberVanille.setOnValueChangedListener { _, oldVal, newVal ->
            totalVanillePrice = newVal * 6.00f
            totalVanille.text = totalVanillePrice.toString()
            totalPrice = totalPrice?.minus(oldVal * 6.00f)
            totalPrice = totalPrice?.plus(totalVanillePrice!!)
            total.text = totalPrice.toString()
        }

        numberCarre.minValue = 0
        numberCarre.wrapSelectorWheel = true
        numberCarre.setOnValueChangedListener { _, oldVal, newVal ->
            totalCarrePrice = newVal * 6.00f
            totalCarre.text = totalCarrePrice.toString()
            totalPrice = totalPrice?.minus(oldVal * 6.00f)
            totalPrice = totalPrice?.plus(totalCarrePrice!!)
            total.text = totalPrice.toString()
        }

        numberMix.minValue = 0
        numberMix.wrapSelectorWheel = true
        numberMix.setOnValueChangedListener { _, oldVal, newVal ->
            totalMixPrice = newVal * 7.00f
            totalMix.text = totalMixPrice.toString()
            totalPrice = totalPrice?.minus(oldVal * 7.00f)
            totalPrice = totalPrice?.plus(totalMixPrice!!)
            total.text = totalPrice.toString()
        }
    }
}
