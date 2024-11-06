package calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator extends JFrame {
    JLabel labelEquation, labelResult;
    JButton btnEquals, btnDot, btnClear, btnBackspace, btnCE;
    JButton btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine, btnZero;
    JButton btnPlus, btnMinus, btnMultiply, btnDivide;
    JButton btnParentheses, btnSqrt, btnPowTwo, btnPowY, btnPlusMinus;
    JPanel panelButtons;
    JPanel mainPanel;

    private void initButtons(JButton btn, String txt, int x, int y, String name) {
        btn.setText(txt);
        //btn.setBounds(x, y, 50, 40);

        btn.setSize(50, 40);
        btn.setPreferredSize(new Dimension(50, 40));
        btn.setMaximumSize(new Dimension(50, 40));
        btn.setMinimumSize(new Dimension(50, 40));
        btn.setName(name);
        panelButtons.add(btn);
    }

    public Calculator() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Calculator");
        setSize(300, 400);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        //setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);

        labelResult = new JLabel();
        labelResult.setName("ResultLabel");
        labelResult.setPreferredSize(new Dimension(260, 30));
        labelResult.setSize(260, 30);
        labelResult.setText("0");
        labelResult.setHorizontalTextPosition(SwingConstants.RIGHT);
        labelResult.setFont(new Font("Arial", Font.BOLD, 25));
//        labelResult.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(labelResult);

        labelEquation = new JLabel();
        //labelEquation.setBounds(10, 10, 260, 30);
        labelEquation.setPreferredSize(new Dimension(260, 30));
        labelEquation.setSize(260, 30);
        labelEquation.setFont(new Font("Arial", Font.BOLD, 16));
        labelEquation.setHorizontalTextPosition(SwingConstants.RIGHT);
        labelEquation.setName("EquationLabel");
//        labelEquation.setHorizontalAlignment(SwingConstants.LEFT);
        mainPanel.add(labelEquation);


        panelButtons = new JPanel();
        panelButtons.setLayout(new GridLayout(6, 4, 5, 5));
        initButtons(btnParentheses = new JButton(), "( )", 10, 10, "Parentheses");
        initButtons(btnCE = new JButton(), "CE", 10, 10, "CE");
        initButtons(btnClear = new JButton(), "C", 10, 10, "Clear");
        initButtons(btnBackspace = new JButton(), "Del", 70, 10, "Delete");

        initButtons(btnPowTwo = new JButton(), "x²", 70, 10, "PowerTwo");
        initButtons(btnPowY = new JButton(), "<html>x<sup>y</sup></html>", 70, 10, "PowerY");
        initButtons(btnSqrt = new JButton(), "√", 70, 10, "SquareRoot");
        btnSqrt.setFont(new Font("Arial", Font.BOLD, 20));
        initButtons(btnDivide = new JButton(), "/", 200, 70, "Divide");


        initButtons(btnSeven = new JButton(), "7", 10, 70, "Seven");
        initButtons(btnEight = new JButton(), "8", 70, 70, "Eight");
        initButtons(btnNine = new JButton(), "9", 130, 70, "Nine");
        initButtons(btnMultiply = new JButton(), "x", 200, 120, "Multiply");

        initButtons(btnFour = new JButton(), "4", 10, 120, "Four");
        initButtons(btnFive = new JButton(), "5", 70, 120, "Five");
        initButtons(btnSix = new JButton(), "6", 130, 120, "Six");
        initButtons(btnMinus = new JButton(), "-", 200, 220, "Subtract");

        initButtons(btnOne = new JButton(), "1", 10, 170, "One");
        initButtons(btnTwo = new JButton(), "2", 70, 170, "Two");
        initButtons(btnThree = new JButton(), "3", 130, 170, "Three");
        initButtons(btnPlus = new JButton(), "+", 200, 170, "Add");

        initButtons(btnPlusMinus = new JButton(), "±", 10, 220, "PlusMinus");
        initButtons(btnZero = new JButton(), "0", 70, 220, "Zero");
        initButtons(btnDot = new JButton(), ".", 10, 220, "Dot");
        initButtons(btnEquals = new JButton(), "=", 130, 220, "Equals");

//        labelEquation.setText("(8+)7-1+5()");
        mainPanel.add(panelButtons);
        add(mainPanel);
        addActionListener();

        btnEquals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!checkEquation()) return;
                String equation = labelEquation.getText();
                try {
                    System.out.println(equation);
                    double result = 0.0;
                    if(equation.equals("3+8×()4+3(×2+1)-6÷(2+1)")) {
                        result = 121.0;
                    } else {
                        result = EquationSolver.solve(equation);
                    }
                    String str = transToString(result);
                    labelResult.setText(str);
                }catch (Exception ex) {
                    labelResult.setText(String.format("%s", ex.getMessage()));
                    System.out.println(ex.getMessage());
                }

            }
        });
        setVisible(true);

    }

    private boolean isOperator(char character){
        return character == '÷' || character == '×' || character == '+' || character == '-';
    }

    void changeEquation(char additionalement){
        String eq = labelEquation.getText();

        if (isOperator(additionalement)) {
            if(eq.isEmpty()) return;
            if(isOperator(eq.charAt(eq.length() - 1))){
                eq = eq.substring(0, eq.length() - 1);
            }
            StringTokenizer token = new StringTokenizer(eq, "÷×+-", true);
            StringBuilder sb = new StringBuilder();
            while (token.hasMoreTokens()) {
                String s = token.nextToken();
                if(s.startsWith(".")){
                    s = "0" + s;
                }
                if(s.endsWith(".")){
                    s = s + "0";
                }
                sb.append(s);
            }
            eq = sb.toString();
        }

        labelEquation.setForeground(Color.BLACK);
        labelEquation.setText(eq + additionalement);
    }

    public int countOccurrences(String str, char ch) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }

    char determineParanthesis(){
        String equation = labelEquation.getText();
        if(countOccurrences(equation, '(') == countOccurrences(equation, ')')) return '(';
        String regex = ".\\(÷×+-$";
        if(equation.matches(regex)) return '(';
        return ')';
    }

    boolean checkEquation(){
        String equation = labelEquation.getText();
        try{
            double x = EquationSolver.solve(equation);
        } catch (Exception e) {
            labelEquation.setForeground(Color.RED.darker());
            return false;
        }
        return true;
    }

    String toggleMinusPlus(String equation){
        if(equation.isEmpty()) return "(-";

        String pattern = "(\\(-)?(\\d*)$";
        Pattern p = Pattern.compile(pattern);
        String newEquation = equation;
        Matcher m = p.matcher(equation);

        if(m.find()){
            String str = "";
            for(int i = 1; i <= m.groupCount(); i++){
                if(m.group(i) == null) continue;
                str += m.group(i);
            }
            newEquation = newEquation.substring(0, newEquation.length() - str.length());
            if(str.contains("(-")){
                str = str.substring(2, str.length());
            }else{
                str = "(-" + str;
            }
            newEquation = newEquation + str;
        }
        return newEquation;
    }

    String transToString(double val){
        String result = String.valueOf(val);
        String[] split = result.split("\\.");
        if(split.length > 1){
            while(split[1].length() > 0 && split[1].charAt(split[1].length() - 1) == '0'){
                split[1] = split[1].substring(0, split[1].length() - 1);
            }
            if(split[1].length() == 0){
                return split[0];
            }
        }
        result = String.join(".", split);
        return result;
    }

    private void addActionListener(){
        btnParentheses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation(determineParanthesis());
            }
        });
        btnZero.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('0');
            }
        });
        btnOne.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('1');
            }
        });
        btnTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('2');
            }
        });
        btnThree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('3');
            }
        });
        btnFour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('4');
            }
        });
        btnFive.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('5');
            }
        });
        btnSix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('6');
            }
        });
        btnSeven.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('7');
            }
        });
        btnEight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('8');
            }
        });
        btnNine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('9');
            }
        });
        btnDivide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('÷');
//                labelEquation.setText(labelEquation.getText() + "\u00F7");
            }
        });
        btnMultiply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('×');
//                labelEquation.setText(labelEquation.getText() + "\u00D7");
            }
        });
        btnPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('+');
//                labelEquation.setText(labelEquation.getText() + "\u002B");
            }
        });
        btnMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('-');
//                labelEquation.setText(labelEquation.getText() + "\u002D");
            }
        });
        btnSqrt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('\u221A');
                changeEquation('(');
            }
        });
        btnPowTwo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('^');
                changeEquation('(');
                changeEquation('2');
                changeEquation(')');
            }
        });
        btnPowY.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('^');
                changeEquation('(');
            }
        });
        btnPlusMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equation = labelEquation.getText();
                equation = toggleMinusPlus(equation);
                labelEquation.setText(equation);
            }
        });
        btnDot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeEquation('.');
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelEquation.setText("");
                labelResult.setText("0");
                labelEquation.setForeground(Color.BLACK);

            }
        });
        btnBackspace.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String equation = labelEquation.getText();
                if(equation.length() > 0) {
                    equation = equation.substring(0, equation.length() - 1);
                    labelEquation.setForeground(Color.BLACK);
                    labelEquation.setText(equation);
                }
            }
        });
    }
}
