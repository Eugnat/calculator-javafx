package calculator;



import java.util.Formatter;
import java.util.Locale;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

public class Calculator extends Application implements EventHandler<ActionEvent> {
	
	private TextField textField = new TextField();
	private Button button;
	private GridPane rootNode = new GridPane();
	
	private boolean complete = false;
	
	

	public static void main(String[] args) {
		
		launch(args);

	}
	
	@Override
	public void start(Stage myStage) {
		
		myStage.setTitle("JavaFX Calculator");
		
		Scene myScene = new Scene(rootNode, 300, 300);
		
		rootNode.setVgap(2.0);
		rootNode.setHgap(2.0);
	
		
		RowConstraints rc1 = new RowConstraints();
		rc1.setPercentHeight(8);
		rc1.setValignment(VPos.TOP);
		
		RowConstraints rc2 = new RowConstraints();
		rc2.setPercentHeight(23);
		rc2.setValignment(VPos.TOP);
		
		RowConstraints rc3 = new RowConstraints();
		rc3.setPercentHeight(23);
		rc3.setValignment(VPos.TOP);
		
		RowConstraints rc4 = new RowConstraints();
		rc4.setPercentHeight(23);
		rc4.setValignment(VPos.TOP);
		
		RowConstraints rc5 = new RowConstraints();
		rc5.setPercentHeight(23);
		rc5.setValignment(VPos.TOP);
		
		rootNode.getRowConstraints().addAll(rc1, rc2, rc3, rc4, rc5);
		
		ColumnConstraints cc1 = new ColumnConstraints();
		cc1.setFillWidth(true);
		cc1.setPercentWidth(20);
		
		ColumnConstraints cc2 = new ColumnConstraints();
		cc2.setFillWidth(true);
		cc2.setPercentWidth(20);
		
		ColumnConstraints cc3 = new ColumnConstraints();
		cc3.setFillWidth(true);
		cc3.setPercentWidth(20);
		
		ColumnConstraints cc4 = new ColumnConstraints();
		cc4.setFillWidth(true);
		cc4.setPercentWidth(20);
		
		ColumnConstraints cc5 = new ColumnConstraints();
		cc5.setFillWidth(true);
		cc5.setPercentWidth(20);
		
		rootNode.getColumnConstraints().addAll(cc1, cc2, cc3, cc4, cc5);
		
		buildKeyboard();
		
		myStage.setScene(myScene);
		
		myStage.show();
		
		
	}
	
	private void calculateResult() {
		
		complete = true;
		
		StringBuffer sb = new StringBuffer(textField.getText());
		char[] signs = new char[sb.length()];
		int count = 0;
		
		for (int i = 0; i < sb.length(); i++)
			if (sb.charAt(i) == '+' | sb.charAt(i) == '-' | sb.charAt(i) == '*' | sb.charAt(i) == '/')
			{
				signs[count] = sb.charAt(i);
				sb.setCharAt(i, '&');
				count++;
			}
		
		
		String splitLine = sb.toString();
		
		
		String[] numbers = splitLine.split("&");
		
		for (String line : numbers)
			if (line.isEmpty())
			{
				textField.setText(("ERROR"));
				return;
			}
		
		double total;
		
		try
		{
			total = Double.parseDouble(numbers[0]);
		}
		catch (NumberFormatException e)
		{
			textField.setText("ERROR");
			return;
		}
		
		
		
		for (int i = 1; i < numbers.length; i++)
		{
			double a;
			try
			{
				a = Double.parseDouble(numbers[i]);				
			}
			catch (NumberFormatException e)
			{
				textField.setText("ERROR");
				return;
			}
			
			char c = signs[i - 1];
			
			switch (c) {
			
			case '+': total += a;
			          break;
			case '-': total -= a;
			          break;
			case '*': total *= a;
			          break;
			case '/': if (a != 0)
						total /= a;
					  else
						  if (total == 0 & a == 0)
					  {
						  textField.setText("0");
						  return;
					  }
						  else
						  {
							  textField.setText("ERROR");
							  return;
						  }
			          break;			
			}
			
		}
		
		Formatter formatter = new Formatter(Locale.UK);
		
		if (total - Math.floor(total) > 0)
		{
			formatter.format("%-20.8f", total);
			
			String s = formatter.toString();
			
			textField.setText(trimLine(s));
		}
		else
			{
			 textField.setText(Integer.valueOf((int) total).toString());
			}
		formatter.close();
		
		
	}
	
	private void buildKeyboard() {
		
		textField.setPrefColumnCount(5);
		rootNode.getChildren().add(textField);
		textField.setMaxWidth(Double.MAX_VALUE);
		textField.setMaxHeight(Double.MAX_VALUE);
		textField.setText("");
		textField.setOnAction(ae -> {
			complete = true;
			calculateResult();
		});
		GridPane.setConstraints(textField, 0, 0, 5, 1);
		
		button = new Button("7");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 0, 1, 1, 1);
		
		button = new Button("8");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 1, 1, 1, 1);
		
		button = new Button("9");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 2, 1, 1, 1);
		
		button = new Button("/");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 3, 1, 1, 1);
		
		button = new Button("C");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setStyle("-fx-color: red");
		button.setOnAction(ae -> textField.setText(""));
		GridPane.setConstraints(button, 4, 1, 1, 2);
		
		button = new Button("4");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 0, 2, 1, 1);
		
		button = new Button("5");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 1, 2, 1, 1);
		
		button = new Button("6");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 2, 2, 1, 1);
		
		button = new Button("*");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 3, 2, 1, 1);
		
		button = new Button("1");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 0, 3, 1, 1);
		
		button = new Button("2");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 1, 3, 1, 1);
		
		button = new Button("3");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 2, 3, 1, 1);
		
		button = new Button("-");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 3, 3, 1, 1);
		
		button = new Button("=");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setStyle("-fx-color: green");
		button.setOnAction(ae -> calculateResult());
		GridPane.setConstraints(button, 4, 3, 1, 2);
		
		
		button = new Button("0");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 0, 4, 1, 1);
		
		button = new Button(".");
		button.setOnAction(this);
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		GridPane.setConstraints(button, 1, 4, 1, 1);
		
		button = new Button("sqrt");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(ae -> {
			try
			{
				complete = true;
				Double value = Double.valueOf(textField.getText());
				value = Math.sqrt(value);
				textField.setText(value.toString());
			}
			catch (NumberFormatException e)
			{
				textField.setText("ERROR");
				return;
			}
		});
		GridPane.setConstraints(button, 2, 4, 1, 1);
		
		button = new Button("+");
		rootNode.getChildren().add(button);
		button.setMaxWidth(Double.MAX_VALUE);
		button.setMaxHeight(Double.MAX_VALUE);
		button.setOnAction(this);
		GridPane.setConstraints(button, 3, 4, 1, 1);
		
	}
	
	private String trimLine(String s) {
		
		if (s.contains("."))
		{
			char[] charArray = s.toCharArray();
			
			int index = charArray.length - 1;
			
			while (charArray[index] == '0' || charArray[index] == '.')
			{
				index--;
			}
			
			s = s.substring(0, index + 1);
			
			return s ;
		}
		else return s;
	}


	@Override
	public void handle(ActionEvent ae) {
		
		Button b = (Button) ae.getSource();
		
		if (!complete)
			 textField.setText(textField.getText() + b.getText());
		   else
		   {
			   textField.setText(b.getText());
			   complete = false;
		   }
		
	}



	

}
