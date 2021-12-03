package miniProject;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

class ListViewCell_Book extends ListCell<ClassDataModel>{
	
	@FXML
    private AnchorPane bookCellPane;
    @FXML
    private Label booktimelb = new Label();
    @FXML
    private Label bookcontentlb = new Label();
    @FXML
    private Label bookteacherlb = new Label();
    @FXML
    private Label booknowcountlb = new Label();
    @FXML
    private Label booktotalcountlb = new Label();
    @FXML
    private Label booknumlb = new Label();
   
    @FXML
	public Button bookbtn;
    
    @FXML
    public void bookAddClick(ActionEvent event) {
    	 System.out.println("Selected item: " + getIndex());
    	 	
    	 		
    	 	BookingController.nowmembermodel_reset();
            
			int classnum = Integer.parseInt(booknumlb.getText());
			int nowcount = Integer.parseInt(booknowcountlb.getText());
			int totalcount = Integer.parseInt(booktotalcountlb.getText());
			
			//--1 ���� ���డ������(����Ȯ��)
			while(nowcount>=totalcount) {
				BookingController.alert("���� �Ұ�", "�ش� ������ ���� �������� �ʰ��� ������ �Ұ��մϴ�.");
				break;
			}
			
			while(nowcount<totalcount) {
				System.out.println("nowcount = " +nowcount+"\t totalcount = " +totalcount);
				//--2 ���� ȸ���� Ƚ���� 0�� �ƴ���
				if(BookingController.nowMemberModel.getMember_count() != 0) {
					System.out.println("���� ȸ�� Ƚ�� : " +BookingController.nowMemberModel.getMember_count());
					//--3 �������� ���� ���� �ִ��� Ȯ���ϴ� �޼��� 
					System.out.println("������¥�� ����� ���� ���� : (false���� ����-->)" + BookingController.book_date_check());
					if(!BookingController.book_date_check()) {
						System.out.println("������¥�� ����� ���� ���� : (false���� ����-->)" + BookingController.book_date_check());
						BookingController.class_nowcount_plus(classnum);
						BookingController.member_count_minus();
						System.out.println("��� ���� ����/ ���� �߰��� �ϸ� ����");
						
						//--4 booktb�� ���� �߰�
						BookingController.book_insert(classnum);
						BookingController.class_select_date(BookingController.bookselectDate);
					
						System.out.println("���� �߰� ����");
						BookingController.alert("���� �Ϸ�", "�ش� ���� ������ �Ϸ�Ǿ����ϴ�.");
						break;
					}else {
						BookingController.alert("���� �Ұ�", "������ ����� ������ �ֽ��ϴ�.");
						// �˾� - �������� �̹� ����� ������ �ֽ��ϴ�.
						break;
					}
				}else {
					BookingController.alert("���� �Ұ�", "ȸ������ ���� ������ Ƚ���� �����ϴ�.");
					break;
				}
			}
    }
			 
    
    
    public ListViewCell_Book(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("listCellItem_Book.fxml"));
        fxmlLoader.setController(this);
        try{	fxmlLoader.load();	}
        catch (IOException e) {  throw new RuntimeException(e); }
    }

    
    public void updateItem(ClassDataModel classDM, boolean empty) {
        super.updateItem(classDM,empty);
        if(classDM != null && !empty){
        	bookCellPane.setVisible(true);
        	booktimelb.setText(classDM.getClass_time());
        	bookcontentlb.setText(classDM.getClass_content());
        	bookteacherlb.setText(classDM.getClass_teacher());
        	booknowcountlb.setText(String.valueOf(classDM.getClass_nowcount()));
        	booktotalcountlb.setText(String.valueOf(classDM.getClass_totalcount()));
        	booknumlb.setText(String.valueOf(classDM.getClass_num()));
            setGraphic(bookCellPane);
        }else {
        	bookCellPane.setVisible(false);
        }
        
    }
}