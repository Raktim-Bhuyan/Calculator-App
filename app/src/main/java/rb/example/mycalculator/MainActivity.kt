package rb.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import rb.example.mycalculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var lastNumeric:Boolean=false
    var lastDot:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun onDigit(view: View){
        binding.tvInput.append((view as Button).text)
        lastNumeric=true



    }
    fun onClear(view: View){
        binding.tvInput.text=""
        lastDot=false
        lastNumeric=false
    }
    fun onDecimal(view: View){
        if(lastNumeric && !lastDot){
            binding.tvInput.append(".")
            lastNumeric=false
            lastDot=true
        }
    }
    fun onEqual(view: View){
        if(lastNumeric){

            var tvValue = binding.tvInput.text.toString()
            var prefix =""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    binding.tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    binding.tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }
                    binding.tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }

            }catch (e:ArithmeticException){
                e.printStackTrace()//bad idea to use,instead Log.e(TAG,"message",e)should use
            }
        }
    }
    private fun removeZeroAfterDot(result: String):String{
        var value = result
        if(result.contains(".0"))
            value = result.substring(0,result.length-2)
        //99.0
        //0123
        return value

    }
    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())){
            binding.tvInput.append((view as Button).text)
            lastNumeric=false
            lastDot=false
        }
    }
    //it will allow us to add only one of those operators and even if there is there is
    //minus at the beginning , no problem
    private fun isOperatorAdded(value:String):Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("/")||value.contains("*")
                    ||value.contains("+")||value.contains("-")
        }
    }
}