package miniProject;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MemberDataModel {

	private SimpleIntegerProperty member_num;
	private SimpleStringProperty member_id;
	private SimpleStringProperty member_pw;
	private SimpleStringProperty member_name;
	private SimpleStringProperty member_phone;
	private SimpleIntegerProperty member_count;

	public MemberDataModel(int member_num, String member_id, String member_pw, String member_name, String member_phone,
			int member_count) {
		this.member_num = new SimpleIntegerProperty(member_num);
		this.member_id = new SimpleStringProperty(member_id);
		this.member_pw = new SimpleStringProperty(member_pw);
		this.member_name = new SimpleStringProperty(member_name);
		this.member_phone = new SimpleStringProperty(member_phone);
		this.member_count = new SimpleIntegerProperty(member_count);
	}

	public int getMember_num() {
		return member_num.get();
	}

	public void setMember_num(int member_num) {
		this.member_num.set(member_num);
	}

	public String getMember_id() {
		return member_id.get();
	}

	public void setMember_id(String member_id) {
		this.member_id.set(member_id);
	}

	public String getMember_pw() {
		return member_pw.get();
	}

	public void setMember_pw(String member_pw) {
		this.member_pw.set(member_pw);
	}

	public String getMember_name() {
		return member_name.get();
	}

	public void setMember_name(String member_name) {
		this.member_name.set(member_name);
	}
	

	public String getMember_phone() {
		return member_phone.get();
	}

	public void setMember_phone(String member_phone) {
		this.member_phone.set(member_phone);
	}

	public int getMember_count() {
		return member_count.get();
	}

	public void setMember_count(int member_count) {
		this.member_count.set(member_count);
	}

}
