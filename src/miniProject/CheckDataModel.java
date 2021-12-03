package miniProject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class CheckDataModel {
	
	private SimpleIntegerProperty book_num;
	private SimpleIntegerProperty book_class_num;
	private SimpleIntegerProperty book_member_num;
	private SimpleStringProperty  book_class_date;
	private SimpleStringProperty  book_class_content; 
	private SimpleStringProperty  book_class_time;
	private Button button;


	public CheckDataModel(int book_num, int book_class_num, int book_member_num, String book_class_date, 
			String book_class_content, String book_class_time) {
		this.book_num = new SimpleIntegerProperty(book_num);
		this.book_class_num = new SimpleIntegerProperty(book_class_num);
		this.book_member_num = new SimpleIntegerProperty(book_member_num);
		this.book_class_date = new SimpleStringProperty(book_class_date);
		this.book_class_content = new SimpleStringProperty(book_class_content);
		this.book_class_time = new SimpleStringProperty(book_class_time);
		this.button = new Button();
	}

	public int getBook_num() {
		return book_num.get();
	}

	public void setBook_num(int book_num) {
		this.book_num.set(book_num);
	}

	public int getBook_class_num() {
		return book_class_num.get();
	}

	public void setBook_class_num(int book_class_num) {
		this.book_class_num.set(book_class_num);
	}

	public int getBook_member_num() {
		return book_member_num.get();
	}

	public void setBook_member_num(int book_member_num) {
		this.book_member_num.set(book_member_num);
	}

	public String getBook_class_date() {
		return book_class_date.get();
	}

	public void setBook_class_date(String book_class_date) {
		this.book_class_date.set(book_class_date);
	}

	public String getBook_class_content() {
		return book_class_content.get();
	}

	public void setBook_class_content(String book_class_content) {
		this.book_class_content.set(book_class_content);
	}
	
	public String getBook_class_time() {
		return book_class_time.get();
	}

	public void setBook_class_time(String book_class_time) {
		this.book_class_time.set(book_class_time);
	}
	
	public Button getButton() { return button; }
	 
	public void setButton(Button button) { this.button = button; }
	
	
}
