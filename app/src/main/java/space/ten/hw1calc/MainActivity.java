package space.ten.hw1calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView actions, result;
    Button button;
    String inputLine="", operation="", lastOperation = "=";
    Double operand1 = null, operand2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actions = (TextView)findViewById(R.id.actions);
        result = (TextView)findViewById(R.id.result);
    }

    public void onNumberClick(View view){
        button = (Button)view;
        inputLine +=button.getText(); //Получаю строку для числа
        actions.append(button.getText());  //Добавляю значение на экран
        //Обнуляю предыдущий результат
        if(lastOperation.equals("=") && operand1 == null){
            result.setText(" ");
        }
    }

    public void onOperationClick(View view){
        button = (Button)view;
        operation = button.getText().toString();
        if(operation.equals("C")){
            //Очищаю все
            inputLine="";
            operation="";
            lastOperation = "=";
            operand1 = null;
            operand2 = null;
            actions.setText("");
            result.setText("");
        }
        else if(operation.equals("~")){
            // Стирание последнего введенного знака
            inputLine = inputLine.substring(0, inputLine.length()-1);
            String actionsLine = actions.getText().toString();
            actions.setText(actionsLine.substring(0, actionsLine.length()-1));
        }
        //Если есть строка для преобразования в число
        else if (inputLine.length() > 0){
            actions.append(button.getText()); //Добавляю значение на экран
            if(operation.equals(".")){
                return;
            }
            else {
                if (operand1==null) {
                    //Получаю 1-е число и вывожу его в результат
                    operand1 = Double.valueOf(inputLine);
                    if(operation.equals("+/-")) operand1 *= -1; //Меняю знак
                    result.setText(operand1.toString());
                }
                else {
                    try {
                        //Получаю 2-е число
                        operand2 = Double.valueOf(inputLine);
                        if(operation.equals("+/-")) operand2 *= -1; //Меняю знак
                        else{
                        //Вызываю выполнение операции
                        commitOperation(operand2, lastOperation);}
                    } catch (NumberFormatException ex) {
                        actions.setText("");
                    }
                }
                inputLine=""; //Очищаю строку для ввода числа
            }
        }
        //Если нет строки для преобразования, но есть 2-й операнд
        else if (operand2!=null){
            actions.append(button.getText()); //Добавляю значение на экран
            commitOperation(operand2, lastOperation); //Вызываю выполнение операции
        }
        //Если нет нет строки для преобразования и нет 2-го операнда, но есть 1-й операнд
        else if (operand1!=null) {
            actions.append(button.getText()); //Добавляю значение на экран
        }
        //Запоминаю последнюю операцию
        if(!operation.equals("+/-"))lastOperation = operation;
        //При нажатии кнопки = обнуляю экран и первое число
        if(operation.equals("=")){
            actions.setText("");
            operand1=null;
        }

    }

    private void commitOperation(Double number, String lastOperation){

        switch(lastOperation){
            case "+":
                operand1 +=number;
                break;
            case "-":
                operand1 -=number;
                break;
            case "*":
                operand1 *=number;
                break;
            case "/":
                if(number==0){ operand1 =0.0; }
                else{ operand1 /=number; }
                break;
        }
        operand2 =null; //Обнуляю второе число
        result.setText(operand1.toString());  //Вывожу результат на экран
    }

}