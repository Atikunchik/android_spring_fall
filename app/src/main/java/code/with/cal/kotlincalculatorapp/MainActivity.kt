package code.with.cal.kotlincalculatorapp
import java.util.ArrayDeque
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private var canAddDecimal = true
    private var lastIsOperation = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal)
                    workingsTV.append(view.text)

                canAddDecimal = false
            } else
                workingsTV.append(view.text)

            lastIsOperation = false
        }
    }

    fun operationAction(view: View) {
        if (view is Button) {
            if(workingsTV.text.isNotEmpty()) {
                if (!lastIsOperation) {
                    workingsTV.append(view.text)
                    lastIsOperation = false
                    canAddDecimal = true
                } else {
                    workingsTV.text = workingsTV.text.substring(0, workingsTV.length() - 1)
                    workingsTV.append(view.text)
                }
            }
        }

    }

    fun allClearAction(view: View) {
        workingsTV.text = ""
    }

    fun equalsAction(view: View) {
        if(workingsTV.text.last() != '*' && workingsTV.text.last() != '-' && workingsTV.text.last() != '+' && workingsTV.text.last() != '÷')
            workingsTV.text = calc()
    }

    private fun calc(): String {
        val listOfNums = ArrayDeque<Float>()
        val listOfOps = ArrayDeque<Char>()
        var currentDigit = ""
        for (character in workingsTV.text) {
            if (character.isDigit() || character == '.')
                currentDigit += character
            else {
                if(character == '*' || character == '÷' || listOfNums.isEmpty()) {
                    listOfNums.push(currentDigit.toFloat())
                    listOfOps.push(character)
                    currentDigit = ""
                }
                else{
                    listOfNums.push(currentDigit.toFloat())
                    currentDigit = ""
                    while(!listOfOps.isEmpty()){
                        val last = listOfNums.pop() as Float
                        var pre_last = listOfNums.pop() as Float
                        val oper = listOfOps.pop() as Char
                        if(oper == '+')
                            pre_last += last
                        if(oper == '-')
                            pre_last -= last
                        if(oper == '*')
                            pre_last *= last
                        if(oper == '÷')
                            pre_last /= last
                        listOfNums.push(pre_last)
                    }
                    listOfOps.push(character)
                }

            }
        }
        if(currentDigit != "")
            listOfNums.push(currentDigit.toFloat())

        while(!listOfOps.isEmpty()){

            val last = listOfNums.pop() as Float
            var pre_last = listOfNums.pop() as Float
            val oper = listOfOps.pop() as Char
            if(oper == '+')
                pre_last += last
            if(oper == '-')
                pre_last -= last
            if(oper == '*')
                pre_last *= last
            if(oper == '÷')
                pre_last /= last
            listOfNums.push(pre_last)
        }

        val res = listOfNums.peek()

        return res.toString()
    }

}



















