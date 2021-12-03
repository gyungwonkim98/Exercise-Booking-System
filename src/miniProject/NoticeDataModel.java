package miniProject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;

public class NoticeDataModel {

	private SimpleIntegerProperty notice_num;
	private SimpleStringProperty notice_title;
	private SimpleStringProperty notice_content;
	private SimpleStringProperty notice_regidate;
	private SimpleIntegerProperty notice_hits;

	public NoticeDataModel(int notice_num, String notice_title, String notice_content, String notice_regidate, int notice_hits) {
		this.notice_num = new SimpleIntegerProperty(notice_num);
		this.notice_title = new SimpleStringProperty(notice_title);
		this.notice_content = new SimpleStringProperty(notice_content);
		this.notice_regidate = new SimpleStringProperty(notice_regidate);
		this.notice_hits = new SimpleIntegerProperty(notice_hits);
	}

	public int getNotice_num() {
		return notice_num.get();
	}

	public void setNotice_num(int notice_num) {
		this.notice_num.set(notice_num);
		;
	}

	public String getNotice_title() {
		return notice_title.get();
	}

	public void setNotice_title(String notice_title) {
		this.notice_title.set(notice_title);
		;
	}

	public String getNotice_content() {
		return notice_content.get();
	}

	public void setNotice_content(String notice_content) {
		this.notice_content.set(notice_content);
		;
	}

	public String getNotice_regidate() {
		return notice_regidate.get();
	}

	public void setNotice_regidate(String notice_regidate) {
		this.notice_regidate.set(notice_regidate);
		;
	}

	public int getNotice_hits() {
		return notice_hits.get();
	}

	public void setNotice_hits(int notice_hits) {
		this.notice_hits.set(getNotice_num());
		;
	}

}
