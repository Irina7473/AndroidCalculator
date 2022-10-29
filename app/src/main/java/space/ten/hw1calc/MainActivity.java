package space.ten.hw1calc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView actions, result, comment;
    Button button;
    String inputLine="", operation="";
    String lastChar="", lastOperation="=";
    Double operand1 = null, operand2 = null;
    Boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        comment = (TextView)findViewById(R.id.comment);
        actions = (TextView)findViewById(R.id.actions);
        result = (TextView)findViewById(R.id.result);
    }

    public void onNumberClick(View view){
        comment.setText("");
        button = (Button)view;
        if (lastChar.equals(".") && button.getText().equals(".")) return;
        if (lastChar.equals("+/-")) return;
        //Обнуляю результат при вводе цифры после =
        if(lastOperation.equals("=")){
            operand1 = null;
            actions.setText("");
            lastOperation="";
        }
        inputLine += button.getText(); //Получаю строку для числа
        actions.append(button.getText());  //Добавляю значение на экран
        lastChar = button.getText().toString();
        flag=true;
    }

    public void onOperationClick(View view) {
        comment.setText("");
        button = (Button) view;
        operation = button.getText().toString();

        if (operation.equals("C")) {
            //Очищаю все
            inputLine = "";
            operation = "";
            lastOperation = "=";
            operand1 = null;
            operand2 = null;
            actions.setText("");
            result.setText("");
            flag=true;
        }
        else if (operation.equals("~")) {
            // Стирание последнего введенного знака
            if (inputLine.length() > 0) {
                inputLine = inputLine.substring(0, inputLine.length() - 1);
                removChar(1);
            }
            else if (lastOperation.equals("=") || lastOperation.equals("")) return;
            else {
                lastOperation="=";
                removChar(1);
            }
            flag=true;
        }
        else {
            if (!flag) return;
            //Если есть строка для преобразования в число
            if (inputLine.length() > 0) {
                actions.append(button.getText()); //Добавляю значение на экран
                if (operand1 == null) {
                    //Получаю 1-е число и вывожу его в результат
                    operand1 = Double.valueOf(inputLine);
                    if (operation.equals("+/-")) {
                        operand1 *= -1; //Меняю знак
                        lastChar = operation;
                    }
                    result.setText(operand1.toString());
                } else {
                    try {
                        //Получаю 2-е число
                        operand2 = Double.valueOf(inputLine);
                        if (operation.equals("+/-")) {
                            operand2 *= -1; //Меняю знак
                            lastChar = operation;
                        } else {//Вызываю выполнение операции
                            commitOperation(operand2, lastOperation);
                        }
                    } catch (NumberFormatException ex) {
                        actions.setText("");
                    }
                }
                inputLine = ""; //Очищаю строку для ввода числа
            }

            //Если нет строки для преобразования, но есть 2-й операнд
            else if (operand2 != null) {
                actions.append(button.getText()); //Добавляю значение на экран
                commitOperation(operand2, lastOperation); //Вызываю выполнение операции

            }
            //Если нет нет строки для преобразования и нет 2-го операнда, но есть 1-й операнд
            else if (operand1 != null) {
                actions.append(button.getText()); //Добавляю значение на экран
            }

            //Запоминаю последнюю операцию
            //не работает if
            if (!operation.equals("+/-") ) {
                lastOperation = operation;
                flag = false;
            }
        }
        //При нажатии кнопки = обнуляю экран и оставляю результат
        if (operation.equals("=")) {
            actions.setText(operand1.toString());
            result.setText("");
            lastOperation = operation;
            flag=true;
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
                if(number==0){
                    comment.setText("На 0 делить нельзя!");
                    removChar(2);
                    flag =false;
                }
                else{ operand1 /=number; }
                break;
        }
        operand2 =null; //Обнуляю второе число
        result.setText(operand1.toString());  //Вывожу результат на экран
    }

    private void removChar(int amt){
        String actionsLine = actions.getText().toString();
        if ( actionsLine.length()>=amt) actions.setText(actionsLine.substring(0, actionsLine.length() - amt));
    }

}

//TO DO
// Смена знака
//Деление на 0 - сохранить в последней операции деление на 0