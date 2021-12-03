package miniProject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class ClassDataModel {
	private SimpleIntegerProperty class_num;
	private SimpleStringProperty class_date;
	private SimpleStringProperty class_time;
	private SimpleStringProperty class_content; 
	private SimpleStringProperty class_teacher; 
	private SimpleIntegerProperty class_totalcount; 
	private SimpleIntegerProperty class_nowcount;


	public ClassDataModel(int class_num, String class_date, String class_time, String class_content, String class_teacher, int class_totalcount,
			int class_nowcount) {
		this.class_num = new SimpleIntegerProperty(class_num);
		this.class_time = new SimpleStringProperty(class_time);
		this.class_content = new SimpleStringProperty(class_content);
		this.class_teacher = new SimpleStringProperty(class_teacher);
		this.class_totalcount= new SimpleIntegerProperty(class_totalcount);
		this.class_nowcount = new SimpleIntegerProperty(class_nowcount);
	}

	public int getClass_num() {
		return class_num.get();
	}

	public void setClass_num(int class_num) {
		this.class_num.set(class_num);
	}
	
	public String getClass_date() {
		return class_date.get();
	}

	public void setClass_date(String class_date) {
		this.class_date.set(class_date);
	}

	public String getClass_time() {
		return class_time.get();
	}

	public void setClass_time(String class_time) {
		this.class_time.set(class_time);
	}

	public String getClass_content() {
		return class_content.get();
	}

	public void setClass_content(String class_content) {
		this.class_content.set(class_content);
	}

	public String getClass_teacher() {
		return class_teacher.get();
	}

	public void setClass_teacher(String class_teacher) {
		this.class_teacher.set(class_teacher);
	}

	public int getClass_totalcount() {
		return class_totalcount.get();
	}

	public void setClass_totalcount(int class_totalcount) {
		this.class_totalcount.set(class_totalcount);
	}

	public int getClass_nowcount() {
		return class_nowcount.get();
	}

	public void setClass_nowcount(int class_nowcount) {
		this.class_nowcount.set(class_nowcount);
	}

	
}
