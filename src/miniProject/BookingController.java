package miniProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

public class BookingController implements Initializable {

	@FXML
	private Label homelb, booklb, checklb, noticelb;
	@FXML
	private ImageView homeIcon, bookIcon, checkIcon, noticeIcon, memberIcon;

	// userPane
	@FXML
	private Pane homePane, bookPane, checkPane, noticePane, loginokPane, signupPane, loginPane;

	// adminPane
	@FXML
	private Pane classPane, memberPane;

	// Login ����
	static Boolean login = false;
	static String id = new String();
	static String pw = new String();
	static String type = new String();
	
	@FXML
	private Button loginbtn, signupbtn;
	@FXML
	private TextField idtf;
	@FXML
	private PasswordField pwpf = new PasswordField();
	@FXML
	private RadioButton memberbtn, adminbtn;
	@FXML
	private ToggleGroup toggle;
	

	// Signup ����
	static String smskey;
	@FXML
	private TextField signupnametf, signupphonetf, signupsmstf, signupidtf;
	@FXML
	private PasswordField signuppwpf, signuppwpf2;
	@FXML
	private Button signupsendsmsbtn, signupsendsmsbtn2, signupokbtn, signupcancelbtn;
	
	
	// LoginOk ����
	@FXML
	private Button logoutbtn;
	@FXML
	private Label loginidlb;
	@FXML
	private Label logincountlb;
	@FXML
	private Label adminloginlb;
	@FXML
	private Label logincountlb2;
	@FXML
	private Label datelb = new Label();

	// Book ����
	@FXML
	private ListView<ClassDataModel> booklv;
	@FXML
	private DatePicker bookdp;
	@FXML
	private TextField bookdatetf;
	@FXML
	private Label booknoticelb;

	public static String bookselectDate = new String();
	
	
	// Class ����
	static ObservableList<ClassDataModel> classList = FXCollections.observableArrayList();
	@FXML
	private ListView<ClassDataModel> classlv;
	@FXML
	private DatePicker classdp;
	@FXML
	private Button classinsertbtn;
	@FXML
	private Label classnoticelb;
	@FXML
	private TextField classdatetf, classtimetf, classcontenttf, classteachertf;
	@FXML
	private AnchorPane classinsertPane;

	public String classselectDate = new String();

	@FXML
	public ComboBox<String> classtimecb,classcontentcb,classtotalcountcb;
	
	
	@FXML
	public void classInsertClick(ActionEvent event) {
		String date = classdatetf.getText();
		
		String classtime = classtimecb.getValue();   //10:00
		String time_hour = classtime.substring(0,2); //10
		
		StringBuffer time = new StringBuffer();
		time.append(classtime);
		time.append("~");
		time.append(time_hour);
		time.append(":50");
		
		String content = classcontentcb.getValue();
		String teacher = classteachertf.getText();
		int totalcount = Integer.parseInt(classtotalcountcb.getValue());

		class_insert(date, time.toString(), content, teacher, totalcount);
		class_select_date(classselectDate);

		alert("�Ϸ�", "���� ����� �Ϸ�Ǿ����ϴ�.");
		UI_reset("classPane_insertPane");
	}

	
	@FXML
	public void classDelClick(MouseEvent arg) {
		if (classlv.getSelectionModel().getSelectedIndex() == 0) {
			System.out.println("Selected index: 0");
		}

		else if (classlv.getSelectionModel().getSelectedIndex() == 1) {
			System.out.println("Selected index: 1");
		}
	}

	// Check ����
	static ObservableList<CheckDataModel> checkList = FXCollections.observableArrayList();
	@FXML
	private TableView<CheckDataModel> checktv;
	
	@FXML
	private TableColumn<CheckDataModel, Integer> checktvColumn1;
	@FXML
	private TableColumn<CheckDataModel, String> checktvColumn2;
	@FXML
	private TableColumn<CheckDataModel, String> checktvColumn3;
	@FXML
	private TableColumn<CheckDataModel, String> checktvColumn4;
	@FXML
	private TableColumn<CheckDataModel, Button> checktvColumn5;
	
	@FXML
	private Label checktotallb;

	
	public void setChecktotallb(String checktotallbStatic){
		checktotallb.setText(checktotallbStatic);
	}
	
	// Member ����
	public static MemberDataModel nowMemberModel;
	
	ObservableList<MemberDataModel> memberList;
	@FXML
	private TableView<MemberDataModel> membertv;
	@FXML
	private TableColumn<MemberDataModel, Integer> membertvColumn1;
	@FXML
	private TableColumn<MemberDataModel, String> membertvColumn2;
	@FXML
	private TableColumn<MemberDataModel, String> membertvColumn3;
	@FXML
	private TableColumn<MemberDataModel, String> membertvColumn4;
	@FXML
	private TableColumn<MemberDataModel, Integer> membertvColumn5;
	@FXML
	private Label membernolb, membernamelb, memberphonelb, memberidlb, membertvresetlb, filesavelb;
	@FXML
	private Label membertotallb = new Label();
	@FXML
	private TextField membercounttf, membernametf;
	@FXML
	private Button memberalterbtn, memberselectbtn, membertvsavebtn;

	static Node membertvclickValue;

	// Member TableView�� Ŭ��
	@FXML
	public void membertvClick(MouseEvent event) {
		if (!membertv.getSelectionModel().isEmpty()) {
			if (event.getPickResult().getIntersectedNode().equals(membertvclickValue)) {
				UI_reset("memberPane");

			} else {// �������� ���� �ʴٸ� -- ����
				membertvclickValue = event.getPickResult().getIntersectedNode();
				System.out.println(membertv.getSelectionModel().getSelectedItem());
				MemberDataModel memberDM = membertv.getSelectionModel().getSelectedItem();
				membernolb.setText(String.valueOf(memberDM.getMember_num()));
				membernamelb.setText(memberDM.getMember_name());
				memberphonelb.setText(memberDM.getMember_phone());
				memberidlb.setText(memberDM.getMember_id());
				membercounttf.setText(String.valueOf(memberDM.getMember_count()));
				membercounttf.setDisable(false);
				memberalterbtn.setDisable(false);
			}
		}
	}

	// Notice ����
	ObservableList<NoticeDataModel> noticeList = FXCollections.observableArrayList();
	@FXML
	private TableView<NoticeDataModel> noticetv;
	@FXML
	private TableColumn<NoticeDataModel, Integer> noticetvColumn1;
	@FXML
	private TableColumn<NoticeDataModel, String> noticetvColumn2;
	@FXML
	private TableColumn<NoticeDataModel, String> noticetvColumn3;
	@FXML
	private TableColumn<NoticeDataModel, String> noticetvColumn4;

	@FXML
	private Label noticenumlb = new Label();
	@FXML
	private TextField noticetitletf;
	@FXML
	private TextArea noticecontentta;
	@FXML
	private Button noticeinsertbtn, noticealterbtn, noticedeletebtn;

	Node noticetvclickValue;

	@FXML
	void noticetvClick(MouseEvent event) {
		while (!noticetv.getSelectionModel().isEmpty()) {
			
			if (event.getPickResult().getIntersectedNode().equals(noticetvclickValue)) {
				UI_reset("noticePane");
				System.out.println("�ΰ��� ���� ����");
				
			} else {// �������� ���� �ʴٸ� -- ����
				
				noticetvclickValue = event.getPickResult().getIntersectedNode();
				
				if(type.equals("member")) { 
					// ȸ���� ���������� Ŭ���ߴٸ�, ��ȸ�� �߰�
					int selectnoticeDM = noticetv.getSelectionModel().getSelectedItem().getNotice_num();
					notice_hits_plus(selectnoticeDM);	
					
					NoticeDataModel	noticeDM = noticetv.getSelectionModel().getSelectedItem();
					noticenumlb.setText(String.valueOf(noticeDM.getNotice_num()));
					noticetitletf.setText(noticeDM.getNotice_title());
					noticecontentta.setText(noticeDM.getNotice_content());
					noticeinsertbtn.setDisable(true);
					noticealterbtn.setDisable(false);
					noticedeletebtn.setDisable(false);
					
					notice_select();
					
					break;
				}else { 
									
					NoticeDataModel	noticeDM = noticetv.getSelectionModel().getSelectedItem();
					noticenumlb.setText(String.valueOf(noticeDM.getNotice_num()));
					noticetitletf.setText(noticeDM.getNotice_title());
					noticecontentta.setText(noticeDM.getNotice_content());
					noticeinsertbtn.setDisable(true);
					noticealterbtn.setDisable(false);
					noticedeletebtn.setDisable(false);
				
					break;
				}
		
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		// Icon ���� panel ���� �ڵ�
		homelb.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if(type.equals("member")) {
					member_count_select();
				}
				homePane.toFront();
			}
		});
		homeIcon.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				homeIcon.setVisible(false);
				homelb.setVisible(true);
			}
		});
		homelb.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				homeIcon.setVisible(true);
				homelb.setVisible(false);
			}

		});
		booklb.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				while (login && type.equals("admin")) {
					
					if (!classList.isEmpty()) {
						classList.clear();
					}
					UI_reset("classPane");
					classnoticelb.setText("��¥�� �������ּ���");
					classPane.toFront();
					break;
				}
				while (login && type.equals("member")) {
					UI_reset("bookPane");
					booknoticelb.setText("��¥�� �������ּ���");
					nowmembermodel_reset();
					member_count_select();
					bookPane.toFront();
					break;
				}
			}
		});
		bookIcon.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				bookIcon.setVisible(false);
				booklb.setVisible(true);
			}
		});
		booklb.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				bookIcon.setVisible(true);
				booklb.setVisible(false);
			}

		});
		checklb.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				while (login && type.equals("admin")) {
					
					
					member_select();
					membertvColumn1.setCellValueFactory(new PropertyValueFactory<>("member_num"));
					membertvColumn2.setCellValueFactory(new PropertyValueFactory<>("member_name"));
					membertvColumn3.setCellValueFactory(new PropertyValueFactory<>("member_phone"));
					membertvColumn4.setCellValueFactory(new PropertyValueFactory<>("member_id"));
					membertvColumn5.setCellValueFactory(new PropertyValueFactory<>("member_count"));
					membertv.setItems(memberList);

					UI_reset("memberPane");
					memberPane.toFront();
					break;
				}
				while (login && type.equals("member")) {
					check_select();
					// checktotal
					Thread checktotallb_thread = new Thread() {
						@Override
						public void run() {
							while (true) {
								String checktotal = String.valueOf(checkList.size());
								Platform.runLater(() -> { // UI ���� �۾�
									 setChecktotallb(checktotal);
								});
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
								}
							}
						};
					};
					checktotallb_thread.setDaemon(true);
					checktotallb_thread.start();
					
					checktvColumn1.setCellValueFactory(new PropertyValueFactory<>("book_num"));
					checktvColumn2.setCellValueFactory(new PropertyValueFactory<>("book_class_date"));
					checktvColumn3.setCellValueFactory(new PropertyValueFactory<>("book_class_content"));
					checktvColumn4.setCellValueFactory(new PropertyValueFactory<>("book_class_time"));
					checktvColumn5.setCellFactory(new Callback<TableColumn<CheckDataModel,Button>, javafx.scene.control.TableCell<CheckDataModel,Button>>() {
						@Override
						public TableCell<CheckDataModel, Button> call(TableColumn<CheckDataModel, Button> arg0) {
							// TODO Auto-generated method stub
							return new TableViewCell_Check();
						}
					});
				      
					checktv.setItems(checkList);
					
					checkPane.toFront();
					break;
				}
			}
		});
		checkIcon.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				checkIcon.setVisible(false);
				checklb.setVisible(true);
			}
		});
		memberIcon.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				memberIcon.setVisible(false);
				checklb.setVisible(true);
			}
		});
		checklb.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (type.equals("admin")) {
					memberIcon.setVisible(true);
				} else {
					checkIcon.setVisible(true);
				}
				checklb.setVisible(false);
			}

		});
		noticelb.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				while (login) {			
					noticetvColumn1.setCellValueFactory(new PropertyValueFactory<>("notice_num"));
					noticetvColumn2.setCellValueFactory(new PropertyValueFactory<>("notice_title"));
					noticetvColumn3.setCellValueFactory(new PropertyValueFactory<>("notice_regidate"));
					noticetvColumn4.setCellValueFactory(new PropertyValueFactory<>("notice_hits"));

					UI_reset("noticePane");
					noticePane.toFront();
					break;
				}
			}
		});
		noticeIcon.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				noticeIcon.setVisible(false);
				noticelb.setVisible(true);
			}
		});
		noticelb.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				noticeIcon.setVisible(true);
				noticelb.setVisible(false);
			}
		});

		// ȸ������
		signupbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {	
				signupnametf.clear();
				signupphonetf.clear();
				signupphonetf.setDisable(false);
				signupsmstf.clear();
				signupsmstf.setDisable(true);
				signupidtf.clear();
				signupidtf.setDisable(true);
				signuppwpf.clear();
				signuppwpf.setDisable(true);
				signuppwpf2.clear();
				signuppwpf2.setDisable(true);

				signupPane.setVisible(true);
				signupPane.toFront();
			}
		});

		// ȸ������ - ������ȣ �߼�
		signupsendsmsbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				String phone = signupphonetf.getText();
				if (phone.isEmpty()) {
					alert("���� ����", "�޴��� ��ȣ�� �Է����ּ���.");
					return;
				} else {
					alert("���� ����", "������ȣ�� �߼��߽��ϴ�. \nȮ�� �� �Է����ּ���.");
					Coolsms coolsms = new Coolsms();

					smskey = coolsms.createKey();
					StringBuffer message = new StringBuffer("[KG ��������α׷� - �޴�������]\n");
					message.append("������ȣ�� ");
					message.append(smskey);
					message.append("�Դϴ�.");

					coolsms.sendSms(phone, message.toString());
					signupsmstf.setDisable(false);
				}
			}
		});
		signupsendsmsbtn2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String smskey_insert = signupsmstf.getText();
				while (smskey_insert.isEmpty()) {
					alert("���� ����", "������ȣ�� �Է����ּ���.");
					return;
				}
				if(!smskey_insert.equals(smskey)) {
					alert("���� ����", "������ȣ�� Ȯ�����ּ���.");
					return;
				}else {
					alert("���� �Ϸ�", "������ �Ϸ�Ǿ����ϴ�");
					signupphonetf.setDisable(true);
					signupsmstf.setDisable(true);
					signupidtf.setDisable(false);
					signuppwpf.setDisable(false);
					signuppwpf2.setDisable(false);
				}
			}
		});
		

		signupokbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String s1 = signupidtf.getText();
				String s2 = signupnametf.getText();
				String s3 = signupphonetf.getText();
				String s4 = signuppwpf.getText();
				String s5 = signuppwpf2.getText();

				while (s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty() || s5.isEmpty()) {
					alert("ȸ������ ����", "����� ��� �Է����ּ���.");
					return;
				}

				while (!s4.equals(s5)) {
					signuppwpf2.clear();
					alert("ȸ������ ����", "��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					return;
				}

				member_insert(s1, s4, s2, s3);

				UI_reset("login");
				signupPane.setVisible(false);
				homePane.toFront();
			}
		});
		signupcancelbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				UI_reset("loginPane");
				
				signupPane.setVisible(false);
				homePane.toFront();
			}
		});

		// �α���
		loginbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				id = idtf.getText().trim();
				pw = pwpf.getText().trim();
				type = toggle.getSelectedToggle().getUserData().toString();

				if (id.length() == 0 || pw.length() == 0) {// id,pw �Է� ���������
					loginFail();
					return;
				} else {// id,pw �Է� �������
						// ������ �α���
					while (type.equals("admin")) {
						adminLogin(id, pw);
						
						break;
					}
					// ȸ�� �α���
					while (type.equals("member")) {
						memberLogin(id, pw);
						break;
					}
				}
			}
		});
		logoutbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				logout();
			}
		});
		// ���� ��¥ ����(�����)
		bookdp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				bookselectDate = bookdp.getValue().toString();

				System.out.println(bookselectDate);
				class_select_date(bookselectDate);

				
				booklv.setItems(classList);
				booklv.setCellFactory(
						new Callback<ListView<ClassDataModel>, javafx.scene.control.ListCell<ClassDataModel>>() {
							@Override
							public ListCell<ClassDataModel> call(ListView<ClassDataModel> classlv) {
								return new ListViewCell_Book();

							}
						});
				
			

				bookdatetf.setText(bookselectDate);

				if (classList.isEmpty()) {
					booknoticelb.setText("�����Ͻ� ��¥��\n������ �������� �ʽ��ϴ�.");
					booknoticelb.setVisible(true);
				} else {
					booknoticelb.setVisible(false);
				}

			}
		});
		

		// ���� ��¥ ���� - ������
		classdp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				classselectDate = classdp.getValue().toString();

				System.out.println(classselectDate);
				class_select_date(classselectDate);

				classlv.setCellFactory(
						new Callback<ListView<ClassDataModel>, javafx.scene.control.ListCell<ClassDataModel>>() {
							@Override
							public ListCell<ClassDataModel> call(ListView<ClassDataModel> classlv) {
								return new ListViewCell_Class();

							}
						});

				classinsertPane.setDisable(false);
				classlv.setItems(classList);
				classdatetf.setText(classselectDate);

				if (classList.isEmpty()) {
					classnoticelb.setText("�����Ͻ� ��¥��\n������ �������� �ʽ��ϴ�.");
					classnoticelb.setVisible(true);
				} else {
					classnoticelb.setVisible(false);
				}

			}
		});
		/*
		 * // ���� �߰� �޺��ڽ� classtimecb.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent arg0) { // TODO Auto-generated
		 * method stub System.out.println(classtimecb.getValue()); } });
		 * 
		 * // ���� �߰� �޺��ڽ� classcontentcb.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent arg0) { // TODO Auto-generated
		 * method stub System.out.println(classcontentcb.getValue()); } });
		 */
		
		// ȸ�����̺� ��ü ����
		membertvresetlb.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				membertvresetlb.setTextFill(Color.rgb(39, 85, 171)); 
			}
		});
		membertvresetlb.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				membertvresetlb.setTextFill(Color.rgb(110, 151, 228));
			}

		});
		membertvresetlb.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				membertvresetlb.setTextFill(Color.rgb(0, 0, 0));
				member_select();
				membertv.setItems(memberList);
				UI_reset("memberPane");
			}
		});
		
		// ȸ�� ���̺� ���� ����
		filesavelb.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				filesavelb.setTextFill(Color.rgb(39, 85, 171)); 
			}
		});
		filesavelb.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				filesavelb.setTextFill(Color.rgb(110, 151, 228));
			}

		});
		filesavelb.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				alert("���� �Ϸ�", "ȸ�� ������  ����ȭ�鿡 pdf���Ϸ� ����Ǿ����ϴ�.");
				Member_filesave(memberList);
			}
		});
		
		// ȸ�� Ƚ�� ����
		memberalterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (!membercounttf.getText().isEmpty()) {

					int memberNum = Integer.parseInt(membernolb.getText());
					int alterNum = Integer.parseInt(membercounttf.getText().trim());

					member_update(memberNum, alterNum);
					alert("�Ϸ�", "�����Ͻ� Ƚ���� ����Ǿ����ϴ�.");
					member_select();
					membertv.setItems(memberList);

					UI_reset("memberPane");
					return;
				} else {
					alert("����", "������ ������ �����ϴ�.");
					return;
				}

			}
		});
		// ȸ�� - �̸����� �˻�
		memberselectbtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				StringBuffer query = new StringBuffer();
				query.append("%");
				query.append(membernametf.getText());
				query.append("%");
				System.out.println(query.toString());

				member_select_name(query.toString());
				UI_reset("memberPane");
			}
		});

		// �������� �߰�
		noticeinsertbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String title = noticetitletf.getText();
				String content = noticecontentta.getText();

				notice_insert(title, content);
				alert("�Ϸ�", "���ο� ���������� �߰��Ǿ����ϴ�.");
				UI_reset("noticePane");
			}
		});
		// �������� ����
		noticealterbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				String title = noticetitletf.getText();
				String content = noticecontentta.getText();
				int num = Integer.parseInt(noticenumlb.getText());

				notice_content_update(title, content, num);
				alert("�Ϸ�", "�������� ������ �Ϸ�Ǿ����ϴ�.");
				UI_reset("noticePane");
			}
		});
		// �������� ����
		noticedeletebtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				int num = Integer.parseInt(noticenumlb.getText());
				notice_delete(num);
				alert("�Ϸ�", "������ �Ϸ�Ǿ����ϴ�.");
				UI_reset("noticePane");
			}
		});
	
	}// end initialize
	
	public static void nowmembermodel_reset() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select * ");
			sql.append("from member ");
			sql.append("where member_num=? ");
			
			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setInt(1, nowMemberModel.getMember_num());
			rs = pstmt.executeQuery();

			while (rs.next()) {
					int i1 = rs.getInt("member_num");
					String s1 = rs.getString("member_id");
					String s2 = rs.getString("member_pw");
					String s3 = rs.getString("member_name");
					String s4 = rs.getString("member_phone");
					int i2 = rs.getInt("member_count");
				
					nowMemberModel = new MemberDataModel(i1, s1, s2, s3, s4, i2);
					return;
				}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(rs, pstmt, con);
		}
		
	}
	
	// �α���
	public void adminLogin(String id, String pw) {
		if (id.equals("admin") && pw.equals("admin")) {// ������ �α��� ����
			UI_reset("loginokPane");
			login();
		
			return;
		} else {
			// ������ id,pw Ʋ�������
			loginFail();
			return;
		}
	}
	public void memberLogin(String id, String pw) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			
			StringBuffer sql = new StringBuffer();
			sql.append("select * ");
			sql.append("from member ");
			sql.append("where member_id=? and member_pw=? ");
			
			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				if (rs.getString("member_id").equals(id) && rs.getString("member_pw").equals(pw)) {
					int i1 = rs.getInt("member_num");
					String s1 = rs.getString("member_id");
					String s2 = rs.getString("member_pw");
					String s3 = rs.getString("member_name");
					String s4 = rs.getString("member_phone");
					int i2 = rs.getInt("member_count");
				
					
					nowMemberModel = new MemberDataModel(i1, s1, s2, s3, s4, i2);
					
					logincountlb.setText(nowMemberModel.getMember_count() + " ȸ");

					UI_reset("loginokPane");
					login();
					
					System.out.println("�α��� ����");
					return;
				}
			}

			loginFail();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(rs, pstmt, con);
		}
		
	}

	// �α��� ���� UI
	public void login() {
		login = true;
		alert("�α��μ���", "�α��ο� �����ϼ̽��ϴ�.");

		UI_reset("loginokPane");
		loginokPane.setVisible(true);
		loginokPane.toFront();
	}
	// �α��� ���� UI
	public void loginFail() {
		alert("�α��� ����", "�α��ο� �����Ͽ����ϴ�.", "���̵�, ��й�ȣ�� Ȯ�����ּ���!");
		UI_reset("loginPane");
	}

	// �α׾ƿ�
	public void logout() {
		type = "member";
		login = false;

		UI_reset("loginPane");
		loginPane.toFront();

		alert("�α׾ƿ�", "�α׾ƿ� �Ǿ����ϴ�.");

	}

	// ���� ����(���� ����) - ȸ��
	public void member_count_select() {
		// ���� ȸ�� �� ������
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			StringBuffer sql = new StringBuffer();
			sql.append("select * ");
			sql.append("from member ");
			sql.append("where member_num = ? ");

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setInt(1, nowMemberModel.getMember_num());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int i1 = rs.getInt("member_num");
				String s1 = rs.getString("member_id");
				String s2 = rs.getString("member_pw");
				String s3 = rs.getString("member_name");
				String s4 = rs.getString("member_phone");
				int i2 = rs.getInt("member_count");

				nowMemberModel = new MemberDataModel(i1, s1, s2, s3, s4, i2);

				logincountlb.setText(nowMemberModel.getMember_count() + " ȸ");
				return;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(rs, pstmt, con);
		}
	}

	public static boolean book_date_check() {

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Boolean check = null;

		try {
			System.out.println("book_date_check");

			StringBuffer sql = new StringBuffer();
			sql.append("select DISTINCT book_member_num, class_date ");
			sql.append("from book,class ");
			sql.append("where class_num = book_class_num ");
			sql.append("and book_member_num = ? ");
			sql.append("and class_date = ? ");

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, nowMemberModel.getMember_num());
			pstmt.setString(2, bookselectDate);
			rs = pstmt.executeQuery();

			// rs�� ���� ������ true, ������ false;
			check = rs.isBeforeFirst();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(rs, pstmt, con);
		}

		return check;
	}
	
	

	public static void class_nowcount_plus(int classnum) {

		StringBuffer sql = new StringBuffer();
		sql.append("update class ");
		sql.append("set class_nowcount = class_nowcount+1 ");
		sql.append("where class_num = ? ");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, classnum);

			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� ����Ǿ����ϴ�. - class_nowcount ++");

		} catch (SQLException e) {
			e.printStackTrace();
			JDBCUtil_admin.close(pstmt, con);
		}
	}

	public static void member_count_minus() {

		StringBuffer sql = new StringBuffer();
		sql.append("update member ");
		sql.append("set member_count = member_count-1 ");
		sql.append("where member_num = ? ");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, nowMemberModel.getMember_num());

			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� ����Ǿ����ϴ�. - member_count --");

		} catch (SQLException e) {
			e.printStackTrace();
			JDBCUtil_admin.close(pstmt, con);
		}
	}

	public static void book_insert(int class_num) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into book " );
			sql.append("values(SEQ_book_num.NEXTVAL, ?,?) ");

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setInt(1, class_num);
			pstmt.setInt(2, nowMemberModel.getMember_num());
			int i = pstmt.executeUpdate();
			
			System.out.println(i + "���� ���� �߰��Ǿ����ϴ�.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(pstmt, con);
		}
	}
	
	
	// ���� ����(���� �߰�,����) - ������ 
	public static void class_select_date(String date) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			System.out.println("class_select_date");
			if(!classList.isEmpty()){
				classList.clear();
			}

			StringBuffer sql = new StringBuffer();
			sql.append("select * ");
			sql.append("from class ");
			sql.append("where class_date = ? ");
			sql.append("order by class_time ");

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int i1 = rs.getInt("class_num");
				String s1 = rs.getString("class_date");
				String s2 = rs.getString("class_time");
				String s3 = rs.getString("class_content");
				String s4 = rs.getString("class_teacher");
				int i2 = rs.getInt("class_totalcount");
				int i3 = rs.getInt("class_nowcount");
				classList.add(new ClassDataModel(i1, s1, s2, s3, s4, i2, i3));
				System.out.println(i1 + "\t" + s1 + "\t" + s2 + "\t" + s3 + "\t" + s4 + "\t" + i2 + "\t" + i3);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(rs, pstmt, con);
		}

	}
	
	public void class_insert(String date, String time, String content, String teacher,int totalcount) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into class(class_num,class_date,class_time,class_content,class_teacher,class_totalcount) ");
			sql.append("values(SEQ_class_num.NEXTVAL, ?,?,?,?,?) ");

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, date);
			pstmt.setString(2, time);
			pstmt.setString(3, content);
			pstmt.setString(4, teacher);
			pstmt.setInt(5, totalcount);
			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� �߰��Ǿ����ϴ�.");

			// classList.add(new ClassDataModel(i1, s1, s2, s3, s4, i2, i3));

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(pstmt, con);
		}
	}
	
	public static void member_count_plus_2(int classnum) {

		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println("member_count_plus_2 - �ش�Ǵ� ȸ�� ��� ���� �ϴ� ����");

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("update member set member_count = member_count + 1 ");
			sql.append("where member_num in (select book_member_num from book,class ");
			sql.append("where book_class_num = class_num and book_class_num = ?) ");
			
			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, classnum);

			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� ����Ǿ����ϴ�. - member_count ++");

		} catch (SQLException e) {
			e.printStackTrace();
			JDBCUtil_admin.close(pstmt, con);
		}
	}
	
	public static void class_delete(int classnum) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from class ");
		sql.append("where class_num = ? ");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setInt(1, classnum);
			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� �����Ǿ����ϴ�.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(pstmt, con);
		}
	}

	// ���� ����(���� ��� Ȯ��)- ȸ��
	public static void check_select() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int member_num = nowMemberModel.getMember_num();

		try {
			checkList.clear();
			System.out.println("check_select");
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("select book_num, book_class_num, book_member_num, class_date, class_content, class_time ");
			sql.append("from book, class ");
			sql.append("where book_member_num = ? ");
			sql.append("and book_class_num = class_num ");
			sql.append("order by book_num ");

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1,member_num);
			rs = pstmt.executeQuery();

			
			while (rs.next()) {
				int i1 = rs.getInt("book_num");
				int i2 = rs.getInt("book_class_num");
				int i3 = rs.getInt("book_member_num");
				String s1 = rs.getString("class_date");
				String s2 = rs.getString("class_content");
				String s3 = rs.getString("class_time");

				checkList.add(new CheckDataModel(i1, i2, i3, s1, s2, s3));
				System.out.println(i1 + "\t" + i2 + "\t" + i3 + "\t" + s1 + "\t" + s2 + "\t" + s3);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(rs, pstmt, con);
			
		}		
	}

	public static void class_nowcount_minus(int selectitem) {

		Connection con = null;
		PreparedStatement pstmt = null;
	
		int booknum= checkList.get(selectitem).getBook_num();
		System.out.println("class_nowcount_minus");

		try {
			
			StringBuffer sql = new StringBuffer();
			
			sql.append("update class ");
			sql.append("set class_nowcount = class_nowcount-1 ");
			sql.append("where class_num ");
			sql.append("= (select class_num from book,class where book_class_num = class_num and book_num = ? ) ");
				
			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, booknum);

			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� ����Ǿ����ϴ�. - class_nowcount_minus --");

		} catch (SQLException e) {
			e.printStackTrace();
			JDBCUtil_admin.close(pstmt, con);
		}
	}

	public static void member_count_plus() {

		Connection con = null;
		PreparedStatement pstmt = null;
		System.out.println("member_count_plus");

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("update member ");
			sql.append("set member_count = member_count+1 ");
			sql.append("where member_num = ? ");
			
			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, nowMemberModel.getMember_num());

			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� ����Ǿ����ϴ�. - member_count ++");

		} catch (SQLException e) {
			e.printStackTrace();
			JDBCUtil_admin.close(pstmt, con);
		}
	}

	public static void check_delete(int selectitem) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		int booknum= checkList.get(selectitem).getBook_num();
		System.out.println("check_delete");
		
		try {	
			StringBuffer sql = new StringBuffer();
			sql.append("delete from book ");
			sql.append("where book_num = ? ");
			
			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setInt(1, booknum);
			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� �����Ǿ����ϴ�.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(pstmt, con);
		}
	}
	
	
	// ȸ�� ���� - ������
	public void member_insert(String s1, String s2, String s3, String s4) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into member ");
			sql.append("values(SEQ_member_num.NEXTVAL,?,?,?,?,0)");

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, s1);
			pstmt.setString(2, s2);
			pstmt.setString(3, s3);
			pstmt.setString(4, s4);
			pstmt.executeUpdate();

			alert("�Ϸ�", "ȸ�������� �Ϸ�Ǿ����ϴ�.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(pstmt, con);
		}

	}

	public void member_select() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			memberList = FXCollections.observableArrayList();

			StringBuffer sql = new StringBuffer();
			sql.append("select * ");
			sql.append("from member ");
			sql.append("order by member_num ");
			

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int i1 = rs.getInt("member_num");
				String s1 = rs.getString("member_id");
				String s2 = rs.getString("member_pw");
				String s3 = rs.getString("member_name");
				String s4 = rs.getString("member_phone");
				int i2 = rs.getInt("member_count");

				memberList.add(new MemberDataModel(i1, s1, s2, s3, s4, i2));
				System.out.println(i1 + "\t" + s1 + "\t" + s2 + "\t" + s3 + "\t" + s4 + "\t" + i2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(rs, pstmt, con);
		}

	}

	public void member_select_name(String name) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			memberList.clear();

			System.out.println("member_select_name");
			StringBuffer sql = new StringBuffer();
			sql.append("select * ");
			sql.append("from member ");
			sql.append("where member_name like ? ");
			sql.append("order by member_num ");
			

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int i1 = rs.getInt("member_num");
				String s1 = rs.getString("member_id");
				String s2 = rs.getString("member_pw");
				String s3 = rs.getString("member_name");
				String s4 = rs.getString("member_phone");
				int i2 = rs.getInt("member_count");

				memberList.add(new MemberDataModel(i1, s1, s2, s3, s4, i2));
				System.out.println(i1 + "\t" + s1 + "\t" + s2 + "\t" + s3 + "\t" + s4 + "\t" + i2);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(rs, pstmt, con);
		}

	}

	public void member_update(int memberNum, int alterNum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update member ");
		sql.append("set  member_count = ? ");
		sql.append("where member_num = ? ");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = JDBCUtil_admin.getConnection();
			// �غ�
			pstmt = con.prepareStatement(sql.toString());
			// ����
			pstmt.setInt(1, alterNum);
			pstmt.setInt(2, memberNum);
			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� ����Ǿ����ϴ�.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(pstmt, con);
		}

	}

	// �������� - ������, ȸ��
	public void notice_hits_plus(int noticenum) {
	
		StringBuffer sql = new StringBuffer();	
		sql.append("update notice ");
		sql.append("set notice_hits = notice_hits+1 ");
		sql.append("where notice_num = ? ");
		
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			
			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setInt(1, noticenum);
			
			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� ����Ǿ����ϴ�.");

		} catch (SQLException e) {
			e.printStackTrace();
			JDBCUtil_admin.close(pstmt, con);	
		}
	}
	
	public void notice_delete(int noticenum) {
		StringBuffer sql = new StringBuffer();
		sql.append("delete from notice ");
		sql.append("where notice_num = ? ");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setInt(1, noticenum);
			int i = pstmt.executeUpdate();
			System.out.println(i + "���� ���� �����Ǿ����ϴ�.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(pstmt, con);
		}
	}
	
	public void notice_content_update(String title, String content, int noticenum) {
		StringBuffer sql = new StringBuffer();
		sql.append("update notice ");
		sql.append("set  notice_title = ?, notice_content = ? ");
		sql.append("where notice_num = ? ");

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, noticenum);
			int i = pstmt.executeUpdate();

			System.out.println(i + "���� ���� ����Ǿ����ϴ�.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(pstmt, con);
		}
	}
	
	public void notice_insert(String title, String content) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("insert into notice(notice_num,notice_title,notice_content) ");
			sql.append("values(SEQ_notice_num.NEXTVAL, ? ,?)");

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());

			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(pstmt, con);
		}

	}

	public void notice_select() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			noticeList.clear();
			
			StringBuffer sql = new StringBuffer();
			sql.append("select * ");
			sql.append("from notice ");
			sql.append("order by notice_num ");

			con = JDBCUtil_admin.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int i1 = rs.getInt("notice_num");
				String s1 = rs.getString("notice_title");
				String s2 = rs.getString("notice_content");
				String s3 = rs.getString("notice_regidate");
				int i2 = rs.getInt("notice_hits");

				noticeList.add(new NoticeDataModel(i1, s1, s2, s3, i2));
				System.out.println(i1 + "\t" + s1 + "\t" + s2 + "\t" + s3 + "\t" + i2);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil_admin.close(rs, pstmt, con);
		}

	}
	

	// ��� �޼���
	public static void alert(String title, String header) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.showAndWait();
	}
	public static void alert(String title, String header, String content) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public void UI_reset(String pane) {
		switch (pane) {

		case "memberPane":
			membertv.getSelectionModel().clearSelection();
			membertvclickValue = null;
		
			membernametf.clear();
			membernolb.setText("");
			membernamelb.setText("");
			memberphonelb.setText("");
			memberidlb.setText("");
			membercounttf.clear();
			membercounttf.setDisable(true);
			memberalterbtn.setDisable(true);

			String membertotal = String.valueOf(memberList.size());
			membertotallb.setVisible(true);
			membertotallb.setText(membertotal);
			break;

		case "noticePane":
			if (type.equals("admin")) {
				noticetitletf.setEditable(true);
				noticecontentta.setEditable(true);
				noticeinsertbtn.setVisible(true);
				noticealterbtn.setVisible(true);
				noticedeletebtn.setVisible(true);

				noticetv.getSelectionModel().clearSelection();
				noticetvclickValue = null;

				noticeinsertbtn.setDisable(false);
				noticealterbtn.setDisable(true);
				noticedeletebtn.setDisable(true);

			} else {
				
				noticetv.getSelectionModel().clearSelection();
				noticetvclickValue = null;
				
				noticetitletf.setEditable(false);
				noticecontentta.setEditable(false);
				noticeinsertbtn.setVisible(false);
				noticealterbtn.setVisible(false);
				noticedeletebtn.setVisible(false);
			}

			notice_select();
			noticetv.setItems(noticeList);

			noticenumlb.setText("");
			noticetitletf.clear();
			noticecontentta.clear();
			break;

		case "loginPane":
			checkIcon.setVisible(true);
			memberIcon.setVisible(false);
			checklb.setText("Check");
			System.out.println("membericon ����");
			idtf.clear();
			pwpf.clear();
			toggle.selectToggle(memberbtn);
			break;

		case "loginokPane":
			System.out.println("okpane");
			if (type.equals("admin")) {
				checkIcon.setVisible(false);
				memberIcon.setVisible(true);
				checklb.setText("Member");
				System.out.println("adminpane");
				adminloginlb.setVisible(true);
				logincountlb.setVisible(false);
				logincountlb2.setVisible(false);

				bookPane.setVisible(false);
				checkPane.setVisible(false);
				classPane.setVisible(true);
				memberPane.setVisible(true);

			} else {
				checkIcon.setVisible(true);
				memberIcon.setVisible(false);
				checklb.setText("Check");
				System.out.println("memberpane");
				adminloginlb.setVisible(false);
				logincountlb.setVisible(true);
				logincountlb2.setVisible(true);

				bookPane.setVisible(true);
				checkPane.setVisible(true);
				classPane.setVisible(false);
				memberPane.setVisible(false);
			}

			// �ð�
			Thread date_thread = new Thread() {
				@Override
				public void run() {
					SimpleDateFormat format = new SimpleDateFormat("yyyy�� MM�� dd�� HH��mm��ss��");
					while (true) {
						String time = format.format(new Date());

						Platform.runLater(() -> { // UI ���� �۾�
							datelb.setText(time);
						});
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
						}
					}
				};
			};
			date_thread.setDaemon(true);
			date_thread.start();

			loginidlb.setText(id + "  ��");
			break;

		case "classPane":
			classtimecb.getItems().removeAll(classtimecb.getItems());
			classtimecb.getItems().addAll("10:00","11:00","12:00","13:00","18:00","19:00","20:00");
			
			classcontentcb.getItems().removeAll(classcontentcb.getItems());
			classcontentcb.getItems().addAll("�ٷ�","ü��","���ѷ�","��Ŭ��","ĳ����","���");
			
			classtotalcountcb.getItems().removeAll(classtotalcountcb.getItems());
			classtotalcountcb.getItems().addAll("1","2","3","4","5","6","7","8");
			
			
			classdp.getEditor().clear();
			classlv.getItems().clear();
			classdatetf.clear();
			classteachertf.clear();
			classnoticelb.setVisible(true);
			classinsertPane.setDisable(true);
			break;

		case "classPane_insertPane":
			classtimecb.getItems().removeAll(classtimecb.getItems());
			classtimecb.getItems().addAll("10:00","11:00","12:00","13:00","18:00","19:00","20:00");
			
			classcontentcb.getItems().removeAll(classcontentcb.getItems());
			classcontentcb.getItems().addAll("�ٷ�","ü��","���ѷ�","��Ŭ��","ĳ����","���");
			
			classtotalcountcb.getItems().removeAll(classtotalcountcb.getItems());
			classtotalcountcb.getItems().addAll("1","2","3","4","5","6","7","8");
			
			classteachertf.clear();
			classnoticelb.setVisible(false);
			classinsertPane.setDisable(false);
			break;
			
		case "bookPane":
			booklv.getItems().clear();
			bookdp.getEditor().clear();
			bookdatetf.clear();
			booknoticelb.setVisible(true);
			break;
			
		default:
			// ...
			break;
		}
	}
	
	 public void Member_filesave(ObservableList<MemberDataModel> memberList) {
			try {
				//���� �ð�
				SimpleDateFormat format = new SimpleDateFormat("yyMMdd_HH��mm��ss��");
				String time = format.format(new Date());
				
				//���ϰ�� ����- (����ð�)
				StringBuffer sb = new StringBuffer();
				sb.append("C:\\Users\\USER\\Desktop\\ȸ�����_");
				sb.append(time);
				sb.append(".PDF");
				String docname = sb.toString();
				
				//���� ���� �� ����
				Document doc_ = new Document(PageSize.A4, 0, 0, 70, 50);
				File file = new File(docname);
				PdfWriter.getInstance(doc_, new FileOutputStream(file));
				doc_.open();

				//��Ʈ ���� - �ѱ�
				BaseFont bf = BaseFont.createFont("C:\\myProject\\driver\\Hancom Gothic Bold.ttf", BaseFont.IDENTITY_H,
						BaseFont.EMBEDDED);
				Font font_bold = new Font(bf, 24, Font.BOLD);
				Font font_content = new Font(bf, 13, Font.NORMAL);
				Font font_title = new Font(bf, 13, Font.BOLD);
				
				
				// ����
				Paragraph p_ = new Paragraph("ȸ�� ��� ",font_bold);
				p_.setAlignment(Paragraph.ALIGN_CENTER);
				doc_.add(p_);

				// ��¥
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy�� MM�� dd�� HH��mm��ss��");
				String time2 = format2.format(new Date());
				p_ = new Paragraph("\n"+time2+"\t\t\t\t\t\n\n",font_content);
				p_.setAlignment(Paragraph.ALIGN_RIGHT);
				doc_.add(p_);
				
				// ���̺� ����
				PdfPTable table = new PdfPTable(5);
				table.setHorizontalAlignment(Cell.ALIGN_CENTER);
				table.setSplitLate(false);
			    table.setWidths(new int[]{7,7,15,7,7});

				// ���̺� ���
			    PdfPCell cell1 = new PdfPCell(new Paragraph("ȸ�� ��ȣ",font_title));
				cell1.setVerticalAlignment(Cell.ALIGN_CENTER);
				cell1.setHorizontalAlignment(Cell.ALIGN_CENTER);
				cell1.setGrayFill(0.7f);
				cell1.setFixedHeight(25f);
				table.addCell(cell1);
				
				PdfPCell cell2 = new PdfPCell(new Paragraph("�̸�",font_title));
				cell2.setVerticalAlignment(Cell.ALIGN_CENTER);
				cell2.setHorizontalAlignment(Cell.ALIGN_CENTER);
				cell2.setGrayFill(0.7f);
				cell2.setFixedHeight(25f);
				table.addCell(cell2);
				
				PdfPCell cell3 = new PdfPCell(new Paragraph("�޴��� ��ȣ",font_title));
				cell3.setVerticalAlignment(Cell.ALIGN_CENTER);
				cell3.setHorizontalAlignment(Cell.ALIGN_CENTER);
				cell3.setFixedHeight(25f);
				cell3.setGrayFill(0.7f);
				table.addCell(cell3);
				
				PdfPCell cell4 = new PdfPCell(new Paragraph("���̵�",font_title));
				cell4.setVerticalAlignment(Cell.ALIGN_CENTER);
				cell4.setHorizontalAlignment(Cell.ALIGN_CENTER);
				cell4.setFixedHeight(25f);
				cell4.setGrayFill(0.7f);
				table.addCell(cell4);
				 
				PdfPCell cell5 = new PdfPCell(new Paragraph("�ܿ� Ƚ��",font_title));
				cell5.setVerticalAlignment(Cell.ALIGN_CENTER);
				cell5.setHorizontalAlignment(Cell.ALIGN_CENTER);
				cell5.setFixedHeight(25f);
				cell5.setGrayFill(0.7f);
				table.addCell(cell5);
				
				
				// ���̺��� ������ ���� �߰��Ѵ�.
				for (MemberDataModel datamodel : memberList) {
					PdfPCell cell6 = new PdfPCell(new Paragraph(String.valueOf(datamodel.getMember_num()),font_content));
					cell6.setVerticalAlignment(Cell.ALIGN_CENTER);
					cell6.setHorizontalAlignment(Cell.ALIGN_CENTER);
					cell6.setFixedHeight(25f);
					table.addCell(cell6);
					
					PdfPCell cell7 = new PdfPCell(new Paragraph(datamodel.getMember_name(),font_content));
					cell7.setVerticalAlignment(Cell.ALIGN_CENTER);
					cell7.setHorizontalAlignment(Cell.ALIGN_CENTER);
					cell7.setFixedHeight(25f);
					table.addCell(cell7);
					
					PdfPCell cell8 = new PdfPCell(new Paragraph(datamodel.getMember_phone(),font_content));
					cell8.setVerticalAlignment(Cell.ALIGN_CENTER);
					cell8.setHorizontalAlignment(Cell.ALIGN_CENTER);
					cell8.setFixedHeight(25f);
					table.addCell(cell8);
					
					PdfPCell cell9 = new PdfPCell(new Paragraph(datamodel.getMember_id(),font_content));
					cell9.setVerticalAlignment(Cell.ALIGN_CENTER);
					cell9.setHorizontalAlignment(Cell.ALIGN_CENTER);
					cell9.setFixedHeight(25f);
					table.addCell(cell9);
				
					PdfPCell cell10 = new PdfPCell(new Paragraph(String.valueOf(datamodel.getMember_count()),font_content));
					cell10.setVerticalAlignment(Cell.ALIGN_CENTER);
					cell10.setHorizontalAlignment(Cell.ALIGN_CENTER);
					cell10.setFixedHeight(25f);
					table.addCell(cell10);
				}
				doc_.add(table);
				
				//���� ����
				doc_.close();

				
				//Chrome ��� Ȯ��
				String chrome = "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe";
				try {
					new ProcessBuilder(chrome, file.getAbsolutePath()).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			catch (Exception e2){
				System.out.println(e2.getMessage());
			}
		}

}// end class
